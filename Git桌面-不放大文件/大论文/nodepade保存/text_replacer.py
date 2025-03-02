import tkinter as tk
from tkinter import ttk, messagebox

def process_text():
    # 获取输入文本
    input_text = input_textbox.get("1.0", "end-1c")
    
    # 初始化输出文本
    output_text = input_text
    
    # 执行默认替换
    if default_replace_var.get():
        output_text = output_text.replace("\\(", "$").replace("\\)", "$").replace("\\[", "$$").replace("\\]", "$$")
    
    # 执行自定义替换
    if custom_replace_var.get():
        for i in range(3):  # 处理3组自定义替换
            from_text = custom_entries[i][0].get().strip()
            to_text = custom_entries[i][1].get().strip()
            if from_text:  # 只有当"替换前"不为空时才执行替换
                output_text = output_text.replace(from_text, to_text)
    
    # 清空输出文本框并插入处理后的文本
    output_textbox.delete("1.0", tk.END)
    output_textbox.insert("1.0", output_text)

def clear_input():
    input_textbox.delete("1.0", tk.END)

def copy_output():
    root.clipboard_clear()
    root.clipboard_append(output_textbox.get("1.0", "end-1c"))
    status_label.config(text="文本已复制到剪贴板!")
    root.after(2000, lambda: status_label.config(text=""))

# 创建主窗口
root = tk.Tk()
root.title("文本替换工具")
root.geometry("600x600")

# 创建顶层框架
main_frame = ttk.Frame(root, padding="10")
main_frame.pack(fill=tk.BOTH, expand=True)

# 创建选项框架
options_frame = ttk.LabelFrame(main_frame, text="替换选项", padding="10")
options_frame.pack(fill=tk.X, pady=(0, 10))

# 默认替换选项
default_replace_var = tk.BooleanVar(value=True)
default_cb = ttk.Checkbutton(options_frame, text="使用默认替换规则 (\\( → $, \\) → $, \\[ → $$, \\] → $$)", 
                             variable=default_replace_var)
default_cb.grid(row=0, column=0, sticky=tk.W, pady=5)

# 自定义替换选项
custom_replace_var = tk.BooleanVar(value=False)
custom_cb = ttk.Checkbutton(options_frame, text="使用自定义替换规则", variable=custom_replace_var)
custom_cb.grid(row=1, column=0, sticky=tk.W, pady=5)

# 自定义替换规则框架
custom_frame = ttk.Frame(options_frame)
custom_frame.grid(row=2, column=0, sticky=tk.W, padx=20)

custom_entries = []
for i in range(3):
    row_frame = ttk.Frame(custom_frame)
    row_frame.pack(fill=tk.X, pady=2)
    
    ttk.Label(row_frame, text=f"规则 {i+1}:").pack(side=tk.LEFT, padx=(0, 5))
    
    from_entry = ttk.Entry(row_frame, width=15)
    from_entry.pack(side=tk.LEFT, padx=(0, 5))
    
    ttk.Label(row_frame, text="→").pack(side=tk.LEFT, padx=5)
    
    to_entry = ttk.Entry(row_frame, width=15)
    to_entry.pack(side=tk.LEFT)
    
    custom_entries.append((from_entry, to_entry))

# 输入文本标签
input_label = ttk.Label(main_frame, text="输入文本:")
input_label.pack(anchor=tk.W, pady=(10, 5))

# 输入文本框
input_textbox = tk.Text(main_frame, height=8)
input_textbox.pack(fill=tk.BOTH, expand=True, pady=(0, 5))

# 按钮框架
button_frame = ttk.Frame(main_frame)
button_frame.pack(fill=tk.X, pady=5)

# 清空按钮
clear_button = ttk.Button(button_frame, text="清空输入", command=clear_input)
clear_button.pack(side=tk.LEFT, padx=(0, 5))

# 确认按钮
confirm_button = ttk.Button(button_frame, text="执行替换", command=process_text)
confirm_button.pack(side=tk.LEFT)

# 输出文本标签
output_label = ttk.Label(main_frame, text="替换后文本:")
output_label.pack(anchor=tk.W, pady=(10, 5))

# 输出文本框
output_textbox = tk.Text(main_frame, height=8)
output_textbox.pack(fill=tk.BOTH, expand=True, pady=(0, 5))

# 复制按钮
copy_button = ttk.Button(main_frame, text="复制结果", command=copy_output)
copy_button.pack(anchor=tk.E, pady=(0, 5))

# 状态标签
status_label = ttk.Label(main_frame, text="")
status_label.pack(anchor=tk.W)

# 启动应用
root.mainloop()