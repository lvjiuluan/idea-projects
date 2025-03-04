import tkinter as tk
from tkinter import scrolledtext, messagebox, ttk
import os
import re
import traceback

class TextProcessorApp:
    def __init__(self, root):
        self.root = root
        self.root.title("文本串行处理器")
        self.root.geometry("800x700")
        
        # 创建规则目录
        self.rules_dir = "rules"
        if not os.path.exists(self.rules_dir):
            os.makedirs(self.rules_dir)
        
        self.rules = {}  # 规则字典
        self.rule_order = []  # 规则执行顺序
        
        self.setup_ui()
        self.load_rules()
        self.update_rules_list()
    
    def load_rules(self):
        # 添加默认规则
        default_code = '''def process_text(text: str) -> str:
    """
    不处理
    将文本原样返回，不进行任何处理。

    参数：
        text (str): 原始文本。

    返回：
        str: 处理后的文本。
    """
    return text
'''
        self.rules["default"] = self.parse_rule_code(default_code)
        if "default" not in self.rule_order:
            self.rule_order.append("default")
        
        # 从目录中加载已保存的规则
        for filename in os.listdir(self.rules_dir):
            if filename.endswith(".py"):
                rule_id = filename[:-3]  # 移除.py扩展名
                file_path = os.path.join(self.rules_dir, filename)
                
                try:
                    with open(file_path, "r", encoding="utf-8") as f:
                        code = f.read()
                    
                    self.rules[rule_id] = self.parse_rule_code(code)
                    if rule_id not in self.rule_order:
                        self.rule_order.append(rule_id)
                except Exception as e:
                    print(f"加载规则 {filename} 出错: {str(e)}")
        
        # 保存规则顺序
        self.save_rule_order()
    
    def save_rule(self, rule_id, code):
        file_path = os.path.join(self.rules_dir, f"{rule_id}.py")
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(code)
    
    def save_rule_order(self):
        # 保存规则执行顺序
        order_path = os.path.join(self.rules_dir, "rule_order.txt")
        with open(order_path, "w", encoding="utf-8") as f:
            for rule_id in self.rule_order:
                f.write(f"{rule_id}\n")
    
    def load_rule_order(self):
        # 加载规则执行顺序
        order_path = os.path.join(self.rules_dir, "rule_order.txt")
        if os.path.exists(order_path):
            self.rule_order = []
            with open(order_path, "r", encoding="utf-8") as f:
                for line in f:
                    rule_id = line.strip()
                    if rule_id and rule_id in self.rules:
                        self.rule_order.append(rule_id)
        
        # 确保所有规则都在执行顺序中
        for rule_id in self.rules:
            if rule_id not in self.rule_order:
                self.rule_order.append(rule_id)
    
    def setup_ui(self):
        # 创建主容器
        main_container = ttk.Frame(self.root, padding="10")
        main_container.pack(fill=tk.BOTH, expand=True)
        
        # 创建规则管理框架
        rule_frame = ttk.LabelFrame(main_container, text="规则管理", padding="5")
        rule_frame.pack(fill=tk.X, pady=(0, 10))
        
        # 规则按钮
        button_frame = ttk.Frame(rule_frame)
        button_frame.pack(side=tk.TOP, fill=tk.X, pady=5)
        
        ttk.Button(button_frame, text="添加规则", command=self.show_add_rule_dialog).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="编辑规则", command=self.show_edit_rule_dialog).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="删除规则", command=self.delete_rule).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="上移", command=lambda: self.move_rule(-1)).pack(side=tk.LEFT, padx=2)
        ttk.Button(button_frame, text="下移", command=lambda: self.move_rule(1)).pack(side=tk.LEFT, padx=2)
        
        # 规则列表框架
        rules_list_frame = ttk.Frame(rule_frame)
        rules_list_frame.pack(fill=tk.BOTH, expand=True, pady=5)
        
        # 创建树形视图显示规则
        self.rules_tree = ttk.Treeview(rules_list_frame, columns=("序号", "名称"), show="headings", height=5)
        self.rules_tree.heading("序号", text="序号")
        self.rules_tree.heading("名称", text="规则名称")
        #self.rules_tree.heading("描述", text="描述")
        
        self.rules_tree.column("序号", width=50, anchor="center")
        self.rules_tree.column("名称", width=150, anchor="center")
        #self.rules_tree.column("描述", width=450, anchor="w")
        
        self.rules_tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        
        # 为树形视图添加滚动条
        tree_scroll = ttk.Scrollbar(rules_list_frame, orient="vertical", command=self.rules_tree.yview)
        tree_scroll.pack(side=tk.RIGHT, fill=tk.Y)
        self.rules_tree.configure(yscrollcommand=tree_scroll.set)
        
        # 创建文本处理面板
        panel_frame = ttk.PanedWindow(main_container, orient=tk.VERTICAL)
        panel_frame.pack(fill=tk.BOTH, expand=True)
        
        # 输入框架
        input_frame = ttk.LabelFrame(panel_frame, text="输入文本", padding="5")
        panel_frame.add(input_frame, weight=1)
        
        # 输入文本框
        self.input_text = scrolledtext.ScrolledText(input_frame, wrap=tk.WORD)
        self.input_text.pack(fill=tk.BOTH, expand=True, pady=(0, 5))
        
        # 处理按钮
        process_frame = ttk.Frame(input_frame)
        process_frame.pack(fill=tk.X, pady=(0, 5))
        
        ttk.Button(process_frame, text="处理文本", command=self.process_text).pack(side=tk.LEFT, padx=5)
        ttk.Button(process_frame, text="清空输入", command=lambda: self.input_text.delete("1.0", tk.END)).pack(side=tk.LEFT, padx=5)
        ttk.Button(process_frame, text="复制结果到输入", command=self.copy_output_to_input).pack(side=tk.LEFT, padx=5)
        
        # 输出框架
        output_frame = ttk.LabelFrame(panel_frame, text="输出文本", padding="5")
        panel_frame.add(output_frame, weight=1)
        
        # 输出文本框
        self.output_text = scrolledtext.ScrolledText(output_frame, wrap=tk.WORD)
        self.output_text.pack(fill=tk.BOTH, expand=True)
        
        # 添加状态栏
        self.status_var = tk.StringVar()
        self.status_var.set("就绪")
        status_bar = ttk.Label(self.root, textvariable=self.status_var, relief=tk.SUNKEN, anchor=tk.W)
        status_bar.pack(side=tk.BOTTOM, fill=tk.X)
    
    def update_rules_list(self):
        # 清除现有项
        for item in self.rules_tree.get_children():
            self.rules_tree.delete(item)
        
        # 按顺序添加规则
        for index, rule_id in enumerate(self.rule_order):
            if rule_id in self.rules:
                rule_info = self.rules[rule_id]
                self.rules_tree.insert("", "end", iid=rule_id, values=(index+1, rule_info["name"], rule_info["description"]))
    
    def move_rule(self, direction):
        # 获取选中的规则
        selected = self.rules_tree.selection()
        if not selected:
            return
        
        rule_id = selected[0]
        
        # 获取当前位置
        current_index = self.rule_order.index(rule_id)
        new_index = current_index + direction
        
        # 检查是否可移动
        if new_index < 0 or new_index >= len(self.rule_order):
            return
        
        # 移动规则
        self.rule_order.pop(current_index)
        self.rule_order.insert(new_index, rule_id)
        
        # 保存规则顺序
        self.save_rule_order()
        
        # 更新显示
        self.update_rules_list()
        self.rules_tree.selection_set(rule_id)
    
    def parse_rule_code(self, code):
        # 从文档字符串中提取名称和描述
        doc_match = re.search(r'def\s+process_text.*?"""(.*?)"""', code, re.DOTALL)
        if doc_match:
            docstring = doc_match.group(1).strip()
            doc_lines = docstring.split('\n')
            name = doc_lines[0].strip()
            description = '\n'.join(doc_lines[1:]).strip() if len(doc_lines) > 1 else ""
        else:
            name = "未命名规则"
            description = ""
        
        return {
            "name": name,
            "description": description,
            "code": code
        }
    
    def show_add_rule_dialog(self):
        self._show_rule_dialog(is_edit=False)
    
    def show_edit_rule_dialog(self):
        selected = self.rules_tree.selection()
        if not selected:
            messagebox.showinfo("提示", "请先选择一个规则")
            return
        
        rule_id = selected[0]
        self._show_rule_dialog(is_edit=True, rule_id=rule_id)
    
    def _show_rule_dialog(self, is_edit=False, rule_id=None):
        rule_code = self.rules[rule_id]["code"] if is_edit else '''def process_text(text: str) -> str:
    """
    处理规则名称
    这里是规则的详细描述，可以多行。
    
    参数：
        text (str): 原始文本。
        
    返回：
        str: 处理后的文本。
    """
    # 在这里编写处理逻辑
    processed_text = text.upper()  # 例如：将文本转为大写
    return processed_text
'''
        
        dialog = tk.Toplevel(self.root)
        dialog.title("编辑规则" if is_edit else "添加规则")
        dialog.geometry("700x500")
        dialog.transient(self.root)
        dialog.grab_set()
        
        # 规则ID输入框（仅添加模式）
        if not is_edit:
            id_frame = ttk.Frame(dialog, padding="5")
            id_frame.pack(fill=tk.X)
            
            ttk.Label(id_frame, text="规则ID:").pack(side=tk.LEFT, padx=(0, 5))
            rule_id_entry = ttk.Entry(id_frame, width=40)
            rule_id_entry.pack(side=tk.LEFT, fill=tk.X, expand=True)
        
        # 代码编辑器
        code_frame = ttk.LabelFrame(dialog, text="规则代码", padding="5")
        code_frame.pack(fill=tk.BOTH, expand=True, pady=5, padx=5)
        
        code_text = scrolledtext.ScrolledText(code_frame, height=20, font=("Courier New", 10))
        code_text.pack(fill=tk.BOTH, expand=True)
        code_text.insert(tk.END, rule_code)
        
        def save_rule():
            nonlocal rule_id
            
            # 获取规则ID（如果在添加模式下）
            if not is_edit:
                rule_id = rule_id_entry.get().strip()
                if not rule_id:
                    messagebox.showerror("错误", "规则ID不能为空", parent=dialog)
                    return
                    
                if rule_id == "default" and "default" in self.rules:
                    messagebox.showerror("错误", "不能使用'default'作为规则ID", parent=dialog)
                    return
                
                if rule_id in self.rules:
                    overwrite = messagebox.askyesno("确认", f"规则'{rule_id}'已存在，是否覆盖？", parent=dialog)
                    if not overwrite:
                        return
            
            code = code_text.get("1.0", tk.END)
            
            # 验证代码
            try:
                compile(code, "<string>", "exec")
            except Exception as e:
                messagebox.showerror("代码错误", f"规则代码有语法错误:\n{str(e)}", parent=dialog)
                return
            
            # 检查代码中是否包含process_text函数
            if "def process_text" not in code:
                messagebox.showerror("错误", "代码必须包含 process_text 函数", parent=dialog)
                return
            
            # 保存规则
            try:
                rule_info = self.parse_rule_code(code)
                self.rules[rule_id] = rule_info
                self.save_rule(rule_id, code)
                
                # 如果是新规则，添加到规则顺序列表末尾
                if not is_edit and rule_id not in self.rule_order:
                    self.rule_order.append(rule_id)
                    self.save_rule_order()
                
                self.update_rules_list()
                
                dialog.destroy()
                action = "更新" if is_edit else "添加"
                self.status_var.set(f"已{action}规则: {rule_id}")
            except Exception as e:
                messagebox.showerror("错误", f"保存规则时出错:\n{str(e)}", parent=dialog)
        
        # 按钮
        button_frame = ttk.Frame(dialog, padding="5")
        button_frame.pack(fill=tk.X)
        
        ttk.Button(button_frame, text="取消", command=dialog.destroy).pack(side=tk.RIGHT, padx=5)
        save_label = "保存" if is_edit else "添加"
        ttk.Button(button_frame, text=save_label, command=save_rule).pack(side=tk.RIGHT, padx=5)
    
    def delete_rule(self):
        selected = self.rules_tree.selection()
        if not selected:
            messagebox.showinfo("提示", "请先选择一个规则")
            return
        
        rule_id = selected[0]
        if rule_id == "default":
            messagebox.showinfo("提示", "默认规则不能删除")
            return
        
        confirm = messagebox.askyesno("确认删除", f"确定要删除规则 '{self.rules[rule_id]['name']}' 吗？")
        if not confirm:
            return
        
        try:
            # 删除规则文件
            rule_file = os.path.join(self.rules_dir, f"{rule_id}.py")
            if os.path.exists(rule_file):
                os.remove(rule_file)
            
            # 从规则字典和顺序列表中移除
            del self.rules[rule_id]
            self.rule_order.remove(rule_id)
            
            # 保存规则顺序
            self.save_rule_order()
            
            # 更新UI
            self.update_rules_list()
            self.status_var.set(f"已删除规则: {rule_id}")
        except Exception as e:
            messagebox.showerror("错误", f"删除规则时出错:\n{str(e)}")
    
    def copy_output_to_input(self):
        """将输出文本复制到输入文本框"""
        output_text = self.output_text.get("1.0", tk.END)
        self.input_text.delete("1.0", tk.END)
        self.input_text.insert(tk.END, output_text)
        self.status_var.set("已将输出复制到输入")
    
    def process_text(self):
        input_text = self.input_text.get("1.0", tk.END)
        
        # 清空输出
        self.output_text.delete("1.0", tk.END)
        
        # 依次应用所有规则
        current_text = input_text
        processed_rules = []
        
        try:
            self.status_var.set("处理中...")
            self.root.update_idletasks()
            
            # 创建处理日志
            log_text = "处理过程日志：\n"
            log_text += "=" * 40 + "\n"
            log_text += f"原始文本长度: {len(input_text)} 字符\n"
            log_text += "=" * 40 + "\n\n"
            
            for rule_id in self.rule_order:
                rule_info = self.rules[rule_id]
                rule_code = rule_info["code"]
                rule_name = rule_info["name"]
                
                log_text += f"应用规则 [{rule_name}]...\n"
                
                # 创建命名空间以执行代码
                namespace = {}
                exec(rule_code, namespace)
                
                # 获取处理函数
                if "process_text" not in namespace:
                    raise Exception(f"规则 '{rule_id}' 中处理函数不存在")
                
                process_func = namespace["process_text"]
                
                # 调用处理函数
                try:
                    before_len = len(current_text)
                    current_text = process_func(current_text)
                    after_len = len(current_text)
                    
                    # 确保结果是字符串
                    if not isinstance(current_text, str):
                        current_text = str(current_text)
                    
                    log_text += f"  处理前: {before_len} 字符\n"
                    log_text += f"  处理后: {after_len} 字符\n"
                    if before_len != after_len:
                        change = after_len - before_len
                        change_str = f"增加了 {change}" if change > 0 else f"减少了 {abs(change)}"
                        log_text += f"  变化: {change_str} 字符\n"
                    log_text += "\n"
                    
                    processed_rules.append(rule_name)
                except Exception as e:
                    log_text += f"  错误: {str(e)}\n\n"
                    raise Exception(f"规则 '{rule_name}' 处理失败: {str(e)}")
            
            # 显示结果
            self.output_text.insert(tk.END, current_text)
            
            # 打印日志到控制台
            print(log_text)
            
            if processed_rules:
                rules_str = " → ".join(processed_rules)
                self.status_var.set(f"处理完成，应用规则链: {rules_str}")
            else:
                self.status_var.set("未应用任何规则")
                
        except Exception as e:
            self.status_var.set("处理失败")
            error_message = "".join(traceback.format_exception(type(e), e, e.__traceback__))
            messagebox.showerror("处理错误", f"处理文本时出错:\n{str(e)}")
            print(error_message)  # 在控制台打印详细错误信息

def main():
    root = tk.Tk()
    app = TextProcessorApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()