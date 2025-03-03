import tkinter as tk
import re
from tkinter import scrolledtext, Button, Label, Frame

def replace_citations():
    """替换文本框中的\cite{...}为\textsuperscript{\cite{...}}"""
    # 获取输入文本
    input_text = input_text_box.get("1.0", tk.END)
    
    # 使用正则表达式查找并替换所有的\cite{...}
    # 正则模式：匹配\cite{后跟任意字符(非贪婪匹配)直到遇到}
    pattern = r'\\cite\{([^}]*)\}'
    replacement = r'\\textsuperscript{\\cite{\1}}'
    
    # 执行替换
    output_text = re.sub(pattern, replacement, input_text)
    
    # 清空输出文本框并插入替换后的文本
    output_text_box.delete("1.0", tk.END)
    output_text_box.insert("1.0", output_text)

def clear_text():
    """清空两个文本框"""
    input_text_box.delete("1.0", tk.END)
    output_text_box.delete("1.0", tk.END)

# 创建主窗口
root = tk.Tk()
root.title("LaTeX引用格式转换工具")
root.geometry("800x600")  # 设置窗口大小

# 创建标题标签
title_label = Label(root, text="LaTeX引用格式转换工具", font=("Arial", 16))
title_label.pack(pady=10)

# 创建说明标签
description = Label(root, text="将\\cite{...}替换为\\textsuperscript{\\cite{...}}", font=("Arial", 12))
description.pack(pady=5)

# 创建框架来容纳输入区域
input_frame = Frame(root)
input_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=10)

# 输入标签
input_label = Label(input_frame, text="输入文本:", font=("Arial", 11))
input_label.pack(anchor="w")

# 创建带滚动条的文本输入框
input_text_box = scrolledtext.ScrolledText(input_frame, wrap=tk.WORD, height=10)
input_text_box.pack(fill=tk.BOTH, expand=True)

# 创建框架来容纳输出区域
output_frame = Frame(root)
output_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=10)

# 输出标签
output_label = Label(output_frame, text="输出文本:", font=("Arial", 11))
output_label.pack(anchor="w")

# 创建带滚动条的文本输出框
output_text_box = scrolledtext.ScrolledText(output_frame, wrap=tk.WORD, height=10)
output_text_box.pack(fill=tk.BOTH, expand=True)

# 创建按钮框架
button_frame = Frame(root)
button_frame.pack(pady=15)

# 创建转换按钮
convert_button = Button(button_frame, text="转换", command=replace_citations, 
                        bg="#4CAF50", fg="white", font=("Arial", 12), width=10)
convert_button.pack(side=tk.LEFT, padx=10)

# 创建清空按钮
clear_button = Button(button_frame, text="清空", command=clear_text,
                     bg="#f44336", fg="white", font=("Arial", 12), width=10)
clear_button.pack(side=tk.LEFT, padx=10)

# 添加底部说明 - 修正了引号问题
footer = Label(root, text="提示：粘贴LaTeX文本到输入框，点击\"转换\"按钮即可", font=("Arial", 10), fg="gray")
footer.pack(pady=5)

# 启动主循环
root.mainloop()