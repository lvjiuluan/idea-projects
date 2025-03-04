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
        
        # 创建处理按钮框架 - 移到最上面
        process_frame = ttk.Frame(main_container)
        process_frame.pack(fill=tk.X, pady=(0, 5))
        
        # 处理按钮
        process_button = ttk.Button(process_frame, text="处理文本", command=self.process_text)
        process_button.pack(side=tk.LEFT, padx=5)
        
        # 复制按钮
        copy_button = ttk.Button(process_frame, text="输出→输入", command=self.copy_output_to_input)
        copy_button.pack(side=tk.LEFT, padx=5)
        
        # 状态栏
        self.status_var = tk.StringVar()
        self.status_var.set("就绪")
        status_label = ttk.Label(process_frame, textvariable=self.status_var)
        status_label.pack(side=tk.LEFT, padx=10)
        
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
        
        # 规则列表
        self.rules_listbox = tk.Listbox(rules_list_frame, height=5)
        self.rules_listbox.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        
        # 规则列表滚动条
        rules_scrollbar = ttk.Scrollbar(rules_list_frame, orient=tk.VERTICAL, command=self.rules_listbox.yview)
        rules_scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self.rules_listbox.config(yscrollcommand=rules_scrollbar.set)
        
        # 创建文本处理区域
        text_paned = ttk.PanedWindow(main_container, orient=tk.VERTICAL)
        text_paned.pack(fill=tk.BOTH, expand=True)
        
        # 输入文本框
        input_frame = ttk.LabelFrame(text_paned, text="输入文本", padding="5")
        text_paned.add(input_frame, weight=1)
        
        self.input_text = scrolledtext.ScrolledText(input_frame, wrap=tk.WORD)
        self.input_text.pack(fill=tk.BOTH, expand=True)
        
        # 输出文本框
        output_frame = ttk.LabelFrame(text_paned, text="输出文本", padding="5")
        text_paned.add(output_frame, weight=1)
        
        self.output_text = scrolledtext.ScrolledText(output_frame, wrap=tk.WORD)
        self.output_text.pack(fill=tk.BOTH, expand=True)
    
    def parse_rule_code(self, code):
        """从代码中解析规则信息"""
        # 提取函数文档字符串
        doc_match = re.search(r'def\s+process_text.*?"""(.*?)"""', code, re.DOTALL)
        if doc_match:
            doc_string = doc_match.group(1).strip()
            lines = doc_string.split('\n')
            name = lines[0].strip()
            description = '\n'.join(lines[1:]).strip() if len(lines) > 1 else ""
        else:
            name = "未命名规则"
            description = "无描述"
        
        return {
            "name": name,
            "description": description,
            "code": code
        }
    
    def update_rules_list(self):
        """更新规则列表显示"""
        self.rules_listbox.delete(0, tk.END)
        
        for rule_id in self.rule_order:
            if rule_id in self.rules:
                rule_info = self.rules[rule_id]
                self.rules_listbox.insert(tk.END, f"{rule_info['name']} ({rule_id})")
    
    def show_add_rule_dialog(self):
        """显示添加规则对话框"""
        dialog = tk.Toplevel(self.root)
        dialog.title("添加规则")
        dialog.geometry("600x500")
        dialog.transient(self.root)
        dialog.grab_set()
        
        # 规则ID
        id_frame = ttk.Frame(dialog, padding="5")
        id_frame.pack(fill=tk.X)
        
        ttk.Label(id_frame, text="规则ID:").pack(side=tk.LEFT)
        id_entry = ttk.Entry(id_frame, width=30)
        id_entry.pack(side=tk.LEFT, padx=5)
        
        # 代码编辑器
        code_frame = ttk.LabelFrame(dialog, text="规则代码", padding="5")
        code_frame.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        code_editor = scrolledtext.ScrolledText(code_frame, wrap=tk.WORD, font=("Courier New", 10))
        code_editor.pack(fill=tk.BOTH, expand=True)
        
        # 设置默认代码模板
        default_code = '''def process_text(text: str) -> str:
    """
    规则名称
    规则描述，说明这个规则的作用。
    
    参数：
        text (str): 原始文本。
        
    返回：
        str: 处理后的文本。
    """
    # 在这里编写处理逻辑
    return text
'''
        code_editor.insert(tk.END, default_code)
        
        # 按钮框架
        button_frame = ttk.Frame(dialog, padding="5")
        button_frame.pack(fill=tk.X)
        
        def save_new_rule():
            rule_id = id_entry.get().strip()
            code = code_editor.get("1.0", tk.END)
            
            if not rule_id:
                messagebox.showerror("错误", "规则ID不能为空")
                return
            
            if rule_id in self.rules and rule_id != "default":
                messagebox.showerror("错误", f"规则ID '{rule_id}' 已存在")
                return
            
            try:
                # 尝试解析和执行代码
                rule_info = self.parse_rule_code(code)
                
                # 创建命名空间以执行代码
                namespace = {}
                exec(code, namespace)
                
                if "process_text" not in namespace:
                    raise Exception("代码中必须包含 process_text 函数")
                
                # 保存规则
                self.rules[rule_id] = rule_info
                self.save_rule(rule_id, code)
                
                # 添加到规则顺序
                if rule_id not in self.rule_order:
                    self.rule_order.append(rule_id)
                    self.save_rule_order()
                
                # 更新UI
                self.update_rules_list()
                self.status_var.set(f"已添加规则: {rule_info['name']}")
                
                dialog.destroy()
            except Exception as e:
                messagebox.showerror("错误", f"保存规则时出错:\n{str(e)}")
        
        ttk.Button(button_frame, text="保存", command=save_new_rule).pack(side=tk.RIGHT, padx=5)
        ttk.Button(button_frame, text="取消", command=dialog.destroy).pack(side=tk.RIGHT, padx=5)
    
    def show_edit_rule_dialog(self):
        """显示编辑规则对话框"""
        # 获取选中的规则
        selection = self.rules_listbox.curselection()
        if not selection:
            messagebox.showinfo("提示", "请先选择一个规则")
            return
        
        index = selection[0]
        if index >= len(self.rule_order):
            return
        
        rule_id = self.rule_order[index]
        rule_info = self.rules[rule_id]
        
        dialog = tk.Toplevel(self.root)
        dialog.title(f"编辑规则 - {rule_info['name']}")
        dialog.geometry("600x500")
        dialog.transient(self.root)
        dialog.grab_set()
        
        # 规则ID (只读)
        id_frame = ttk.Frame(dialog, padding="5")
        id_frame.pack(fill=tk.X)
        
        ttk.Label(id_frame, text="规则ID:").pack(side=tk.LEFT)
        id_var = tk.StringVar(value=rule_id)
        ttk.Entry(id_frame, textvariable=id_var, state="readonly", width=30).pack(side=tk.LEFT, padx=5)
        
        # 代码编辑器
        code_frame = ttk.LabelFrame(dialog, text="规则代码", padding="5")
        code_frame.pack(fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        code_editor = scrolledtext.ScrolledText(code_frame, wrap=tk.WORD, font=("Courier New", 10))
        code_editor.pack(fill=tk.BOTH, expand=True)
        
        # 加载当前代码
        code_editor.insert(tk.END, rule_info["code"])
        
        # 按钮框架
        button_frame = ttk.Frame(dialog, padding="5")
        button_frame.pack(fill=tk.X)
        
        def save_edited_rule():
            code = code_editor.get("1.0", tk.END)
            
            try:
                # 尝试解析和执行代码
                rule_info = self.parse_rule_code(code)
                
                # 创建命名空间以执行代码
                namespace = {}
                exec(code, namespace)
                
                if "process_text" not in namespace:
                    raise Exception("代码中必须包含 process_text 函数")
                
                # 保存规则
                self.rules[rule_id] = rule_info
                self.save_rule(rule_id, code)
                
                # 更新UI
                self.update_rules_list()
                self.status_var.set(f"已更新规则: {rule_info['name']}")
                
                dialog.destroy()
            except Exception as e:
                messagebox.showerror("错误", f"保存规则时出错:\n{str(e)}")
        
        ttk.Button(button_frame, text="保存", command=save_edited_rule).pack(side=tk.RIGHT, padx=5)
        ttk.Button(button_frame, text="取消", command=dialog.destroy).pack(side=tk.RIGHT, padx=5)
    
    def move_rule(self, direction):
        """移动规则位置"""
        selection = self.rules_listbox.curselection()
        if not selection:
            messagebox.showinfo("提示", "请先选择一个规则")
            return
        
        index = selection[0]
        if index >= len(self.rule_order):
            return
        
        new_index = index + direction
        if new_index < 0 or new_index >= len(self.rule_order):
            return  # 超出范围
        
        # 交换位置
        self.rule_order[index], self.rule_order[new_index] = self.rule_order[new_index], self.rule_order[index]
        
        # 保存规则顺序
        self.save_rule_order()
        
        # 更新UI
        self.update_rules_list()
        self.rules_listbox.selection_clear(0, tk.END)
        self.rules_listbox.selection_set(new_index)
        self.rules_listbox.see(new_index)
    
    def delete_rule(self):
        """删除规则"""
        selection = self.rules_listbox.curselection()
        if not selection:
            messagebox.showinfo("提示", "请先选择一个规则")
            return
        
        index = selection[0]
        if index >= len(self.rule_order):
            return
        
        rule_id = self.rule_order[index]
        
        # 不允许删除默认规则
        if rule_id == "default":
            messagebox.showinfo("提示", "默认规则不能删除")
            return
        
        # 确认删除
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
