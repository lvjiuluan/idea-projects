import os
import json
import tkinter as tk
from tkinter import ttk, filedialog, messagebox, scrolledtext
import datetime

class PromptManager:
    def __init__(self, root):
        self.root = root
        self.root.title("Prompt 管理器")
        self.root.geometry("900x600")
        self.root.minsize(800, 500)
        
        # 数据存储
        self.data_path = ""
        self.prompts = []
        self.current_prompt_index = None
        
        # 创建主框架
        self.create_widgets()
        
        # 加载配置
        self.load_config()
        
    def create_widgets(self):
        # 创建菜单栏
        menubar = tk.Menu(self.root)
        self.root.config(menu=menubar)
        
        # 文件菜单
        file_menu = tk.Menu(menubar, tearoff=0)
        menubar.add_cascade(label="文件", menu=file_menu)
        file_menu.add_command(label="设置存储路径", command=self.set_data_path)
        file_menu.add_separator()
        file_menu.add_command(label="导出所有Prompt", command=self.export_prompts)
        file_menu.add_command(label="导入Prompt", command=self.import_prompts)
        file_menu.add_separator()
        file_menu.add_command(label="退出", command=self.root.quit)
        
        # 帮助菜单
        help_menu = tk.Menu(menubar, tearoff=0)
        menubar.add_cascade(label="帮助", menu=help_menu)
        help_menu.add_command(label="关于", command=self.show_about)
        
        # 主分割窗口
        self.paned_window = ttk.PanedWindow(self.root, orient=tk.HORIZONTAL)
        self.paned_window.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        # 左侧面板 - Prompt列表
        left_frame = ttk.Frame(self.paned_window)
        self.paned_window.add(left_frame, weight=1)
        
        # 搜索框
        search_frame = ttk.Frame(left_frame)
        search_frame.pack(fill=tk.X, padx=5, pady=5)
        
        ttk.Label(search_frame, text="搜索:").pack(side=tk.LEFT)
        self.search_var = tk.StringVar()
        self.search_var.trace("w", self.filter_prompts)
        search_entry = ttk.Entry(search_frame, textvariable=self.search_var)
        search_entry.pack(side=tk.LEFT, fill=tk.X, expand=True, padx=5)
        
        # Prompt列表
        list_frame = ttk.Frame(left_frame)
        list_frame.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        columns = ("name", "category", "date")
        self.prompt_tree = ttk.Treeview(list_frame, columns=columns, show="headings")
        self.prompt_tree.heading("name", text="名称")
        self.prompt_tree.heading("category", text="分类")
        self.prompt_tree.heading("date", text="修改日期")
        
        self.prompt_tree.column("name", width=120)
        self.prompt_tree.column("category", width=80)
        self.prompt_tree.column("date", width=100)
        
        self.prompt_tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        self.prompt_tree.bind("<<TreeviewSelect>>", self.on_prompt_select)
        
        # 添加滚动条
        scrollbar = ttk.Scrollbar(list_frame, orient=tk.VERTICAL, command=self.prompt_tree.yview)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self.prompt_tree.configure(yscrollcommand=scrollbar.set)
        
        # 按钮区域
        button_frame = ttk.Frame(left_frame)
        button_frame.pack(fill=tk.X, padx=5, pady=5)
        
        ttk.Button(button_frame, text="新建", command=self.new_prompt).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="删除", command=self.delete_prompt).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="复制", command=self.duplicate_prompt).pack(side=tk.LEFT, padx=2)
        
        # 右侧面板 - 编辑区域
        right_frame = ttk.Frame(self.paned_window)
        self.paned_window.add(right_frame, weight=3)
        
        # 编辑区域
        edit_frame = ttk.LabelFrame(right_frame, text="编辑 Prompt")
        edit_frame.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        # 名称和分类
        info_frame = ttk.Frame(edit_frame)
        info_frame.pack(fill=tk.X, padx=5, pady=5)
        
        ttk.Label(info_frame, text="名称:").grid(row=0, column=0, sticky=tk.W, padx=5, pady=5)
        self.name_var = tk.StringVar()
        ttk.Entry(info_frame, textvariable=self.name_var).grid(row=0, column=1, sticky=tk.EW, padx=5, pady=5)
        
        ttk.Label(info_frame, text="分类:").grid(row=0, column=2, sticky=tk.W, padx=5, pady=5)
        self.category_var = tk.StringVar()
        self.category_combo = ttk.Combobox(info_frame, textvariable=self.category_var)
        self.category_combo.grid(row=0, column=3, sticky=tk.EW, padx=5, pady=5)
        
        info_frame.columnconfigure(1, weight=2)
        info_frame.columnconfigure(3, weight=1)
        
        # Prompt内容
        content_frame = ttk.Frame(edit_frame)
        content_frame.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        ttk.Label(content_frame, text="Prompt 内容:").pack(anchor=tk.W)
        self.content_text = scrolledtext.ScrolledText(content_frame, wrap=tk.WORD)
        self.content_text.pack(fill=tk.BOTH, expand=True, pady=5)
        
        # 保存按钮
        ttk.Button(edit_frame, text="保存", command=self.save_prompt).pack(pady=10)
        
        # 状态栏
        self.status_var = tk.StringVar()
        self.status_var.set("准备就绪")
        status_bar = ttk.Label(self.root, textvariable=self.status_var, relief=tk.SUNKEN, anchor=tk.W)
        status_bar.pack(side=tk.BOTTOM, fill=tk.X)
        
        # 禁用编辑区域，直到选择或创建新的Prompt
        self.toggle_edit_state(False)
    
    def toggle_edit_state(self, enabled=True):
        state = "normal" if enabled else "disabled"
        self.name_var.set("")
        self.category_var.set("")
        self.content_text.delete(1.0, tk.END)
        
        self.category_combo.config(state=state)
        self.content_text.config(state=state)
    
    def load_config(self):
        config_dir = os.path.join(os.path.expanduser("~"), ".prompt_manager")
        os.makedirs(config_dir, exist_ok=True)
        
        config_path = os.path.join(config_dir, "config.json")
        if os.path.exists(config_path):
            try:
                with open(config_path, 'r', encoding='utf-8') as f:
                    config = json.load(f)
                    self.data_path = config.get("data_path", "")
                    if self.data_path and os.path.exists(self.data_path):
                        self.load_prompts()
                    else:
                        self.set_data_path()
            except Exception as e:
                messagebox.showerror("错误", f"加载配置失败: {str(e)}")
                self.set_data_path()
        else:
            self.set_data_path()
    
    def save_config(self):
        config_dir = os.path.join(os.path.expanduser("~"), ".prompt_manager")
        os.makedirs(config_dir, exist_ok=True)
        
        config_path = os.path.join(config_dir, "config.json")
        try:
            with open(config_path, 'w', encoding='utf-8') as f:
                json.dump({"data_path": self.data_path}, f, ensure_ascii=False, indent=2)
        except Exception as e:
            messagebox.showerror("错误", f"保存配置失败: {str(e)}")
    
    def set_data_path(self):
        path = filedialog.askdirectory(title="选择数据存储路径")
        if path:
            self.data_path = path
            self.save_config()
            self.load_prompts()
            self.status_var.set(f"数据存储路径: {self.data_path}")
    
    def load_prompts(self):
        if not self.data_path:
            return
        
        data_file = os.path.join(self.data_path, "prompts.json")
        if os.path.exists(data_file):
            try:
                with open(data_file, 'r', encoding='utf-8') as f:
                    self.prompts = json.load(f)
                self.update_prompt_list()
                self.update_categories()
            except Exception as e:
                messagebox.showerror("错误", f"加载Prompt数据失败: {str(e)}")
        else:
            self.prompts = []
            self.save_prompts()
    
    def save_prompts(self):
        if not self.data_path:
            messagebox.showwarning("警告", "请先设置数据存储路径")
            self.set_data_path()
            if not self.data_path:
                return False
        
        try:
            os.makedirs(self.data_path, exist_ok=True)
            data_file = os.path.join(self.data_path, "prompts.json")
            with open(data_file, 'w', encoding='utf-8') as f:
                json.dump(self.prompts, f, ensure_ascii=False, indent=2)
            return True
        except Exception as e:
            messagebox.showerror("错误", f"保存Prompt数据失败: {str(e)}")
            return False
    
    def update_prompt_list(self):
        # 清空列表
        for item in self.prompt_tree.get_children():
            self.prompt_tree.delete(item)
        
        # 获取搜索关键词
        search_term = self.search_var.get().lower()
        
        # 添加符合搜索条件的Prompt
        for prompt in self.prompts:
            if (search_term in prompt["name"].lower() or 
                search_term in prompt["category"].lower() or 
                search_term in prompt["content"].lower()):
                self.prompt_tree.insert("", tk.END, values=(
                    prompt["name"], 
                    prompt["category"], 
                    prompt["modified_date"]
                ))
    
    def update_categories(self):
        categories = sorted(list(set(prompt["category"] for prompt in self.prompts if prompt["category"])))
        self.category_combo["values"] = categories
    
    def filter_prompts(self, *args):
        self.update_prompt_list()
    
    def on_prompt_select(self, event):
        selected_items = self.prompt_tree.selection()
        if not selected_items:
            return
        
        # 获取选中项的索引
        item = selected_items[0]
        item_index = self.prompt_tree.index(item)
        
        # 查找对应的prompt
        search_term = self.search_var.get().lower()
        matching_prompts = [p for p in self.prompts if 
                           search_term in p["name"].lower() or 
                           search_term in p["category"].lower() or 
                           search_term in p["content"].lower()]
        
        if item_index < len(matching_prompts):
            prompt = matching_prompts[item_index]
            self.current_prompt_index = self.prompts.index(prompt)
            
            # 更新编辑区域
            self.toggle_edit_state(True)
            self.name_var.set(prompt["name"])
            self.category_var.set(prompt["category"])
            self.content_text.delete(1.0, tk.END)
            self.content_text.insert(tk.END, prompt["content"])
    
    def new_prompt(self):
        self.current_prompt_index = None
        self.toggle_edit_state(True)
        self.name_var.set("新建Prompt")
        self.category_var.set("")
        self.content_text.delete(1.0, tk.END)
    
    def save_prompt(self):
        name = self.name_var.get().strip()
        category = self.category_var.get().strip()
        content = self.content_text.get(1.0, tk.END).strip()
        
        if not name:
            messagebox.showwarning("警告", "请输入Prompt名称")
            return
        
        if not content:
            messagebox.showwarning("警告", "请输入Prompt内容")
            return
        
        current_date = datetime.datetime.now().strftime("%Y-%m-%d")
        
        prompt = {
            "name": name,
            "category": category,
            "content": content,
            "created_date": current_date,
            "modified_date": current_date
        }
        
        if self.current_prompt_index is not None:
            # 更新现有Prompt
            old_prompt = self.prompts[self.current_prompt_index]
            prompt["created_date"] = old_prompt["created_date"]
            self.prompts[self.current_prompt_index] = prompt
        else:
            # 添加新Prompt
            self.prompts.append(prompt)
            self.current_prompt_index = len(self.prompts) - 1
        
        if self.save_prompts():
            self.update_prompt_list()
            self.update_categories()
            self.status_var.set(f"已保存: {name}")
    
    def delete_prompt(self):
        if self.current_prompt_index is None:
            messagebox.showwarning("警告", "请先选择要删除的Prompt")
            return
        
        prompt = self.prompts[self.current_prompt_index]
        if messagebox.askyesno("确认删除", f"确定要删除 '{prompt['name']}' 吗?"):
            del self.prompts[self.current_prompt_index]
            self.save_prompts()
            self.update_prompt_list()
            self.update_categories()
            self.toggle_edit_state(False)
            self.current_prompt_index = None
            self.status_var.set(f"已删除: {prompt['name']}")
    
    def duplicate_prompt(self):
        if self.current_prompt_index is None:
            messagebox.showwarning("警告", "请先选择要复制的Prompt")
            return
        
        prompt = self.prompts[self.current_prompt_index].copy()
        prompt["name"] = f"{prompt['name']} (复制)"
        prompt["modified_date"] = datetime.datetime.now().strftime("%Y-%m-%d")
        
        self.prompts.append(prompt)
        self.current_prompt_index = len(self.prompts) - 1
        
        if self.save_prompts():
            self.update_prompt_list()
            
            # 更新编辑区域
            self.toggle_edit_state(True)
            self.name_var.set(prompt["name"])
            self.category_var.set(prompt["category"])
            self.content_text.delete(1.0, tk.END)
            self.content_text.insert(tk.END, prompt["content"])
            
            self.status_var.set(f"已复制: {prompt['name']}")
    
    def export_prompts(self):
        if not self.prompts:
            messagebox.showinfo("信息", "没有可导出的Prompt")
            return
        
        file_path = filedialog.asksaveasfilename(
            defaultextension=".json",
            filetypes=[("JSON文件", "*.json"), ("所有文件", "*.*")],
            title="导出Prompts"
        )
        
        if file_path:
            try:
                with open(file_path, 'w', encoding='utf-8') as f:
                    json.dump(self.prompts, f, ensure_ascii=False, indent=2)
                messagebox.showinfo("成功", f"已成功导出 {len(self.prompts)} 个Prompt")
            except Exception as e:
                messagebox.showerror("错误", f"导出失败: {str(e)}")
    
    def import_prompts(self):
        file_path = filedialog.askopenfilename(
            filetypes=[("JSON文件", "*.json"), ("所有文件", "*.*")],
            title="导入Prompts"
        )
        
        if file_path:
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    imported_prompts = json.load(f)
                
                if not isinstance(imported_prompts, list):
                    messagebox.showerror("错误", "导入的文件格式不正确")
                    return
                
                # 检查导入的每个prompt是否有必要的字段
                required_fields = ["name", "content"]
                for prompt in imported_prompts:
                    for field in required_fields:
                        if field not in prompt:
                            messagebox.showerror("错误", f"导入的Prompt缺少必要字段: {field}")
                            return
                    
                    # 确保有分类字段
                    if "category" not in prompt:
                        prompt["category"] = ""
                    
                    # 确保有日期字段
                    current_date = datetime.datetime.now().strftime("%Y-%m-%d")
                    if "created_date" not in prompt:
                        prompt["created_date"] = current_date
                    if "modified_date" not in prompt:
                        prompt["modified_date"] = current_date
                
                # 询问用户是追加还是替换
                if self.prompts:
                    answer = messagebox.askyesnocancel(
                        "导入选项", 
                        "是否将导入的Prompt追加到现有列表?\n"
                        "选择'是'追加，'否'替换现有列表，'取消'取消操作"
                    )
                    
                    if answer is None:  # 取消
                        return
                    elif answer:  # 追加
                        self.prompts.extend(imported_prompts)
                    else:  # 替换
                        self.prompts = imported_prompts
                else:
                    self.prompts = imported_prompts
                
                if self.save_prompts():
                    self.update_prompt_list()
                    self.update_categories()
                    messagebox.showinfo("成功", f"已成功导入 {len(imported_prompts)} 个Prompt")
            
            except Exception as e:
                messagebox.showerror("错误", f"导入失败: {str(e)}")
    
    def show_about(self):
        about_text = """
        Prompt 管理器 v1.0
        
        一个简单的工具，用于管理和组织您的AI提示词(Prompts)。
        
        功能:
        - 添加、编辑、删除和复制Prompts
        - 按分类组织Prompts
        - 搜索Prompts
        - 导入/导出功能
        
        作者: AI助手
        """
        messagebox.showinfo("关于", about_text)

def main():
    root = tk.Tk()
    app = PromptManager(root)
    root.mainloop()

if __name__ == "__main__":
    main()
