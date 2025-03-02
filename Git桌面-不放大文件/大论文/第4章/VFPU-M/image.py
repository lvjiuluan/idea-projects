import tkinter as tk
from tkinter import messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import numpy as np

# 创建全局变量来追踪图形对象
canvas_widget = None
figure = None

def plot_graph():
    global canvas_widget, figure
    
    try:
        # 获取输入值
        x_input = x_entry.get().strip()
        y_input = y_entry.get().strip()
        
        # 解析X和Y值（假设是逗号或空格分隔的数字）
        try:
            if ',' in x_input:
                x_values = [float(x) for x in x_input.split(',')]
            else:
                x_values = [float(x) for x in x_input.split()]
                
            if ',' in y_input:
                y_values = [float(y) for y in y_input.split(',')]
            else:
                y_values = [float(y) for y in y_input.split()]
        except ValueError:
            messagebox.showerror("输入错误", "请确保X和Y值都是有效的数字！")
            return
            
        # 检查X和Y值的数量是否匹配
        if len(x_values) != len(y_values):
            messagebox.showerror("输入错误", "X和Y值的数量必须相同！")
            return
        
        # 清除旧图和canvas
        if canvas_widget:
            canvas_widget.destroy()
        
        # 创建新的图形
        figure = plt.Figure(figsize=(6, 4), dpi=100)
        ax = figure.add_subplot(111)
        
        # 绘制曲线图
        ax.plot(x_values, y_values, marker='o', linestyle='-')
        ax.grid(True)
        ax.set_xlabel('X 值')
        ax.set_ylabel('Y 值')
        ax.set_title('X-Y 曲线图')
        
        # 创建新的canvas
        canvas = FigureCanvasTkAgg(figure, master=frame_plot)
        canvas_widget = canvas.get_tk_widget()
        canvas_widget.pack(fill=tk.BOTH, expand=True)
        canvas.draw()
        
    except Exception as e:
        messagebox.showerror("错误", f"绘图时出错: {str(e)}")

# 创建主窗口
root = tk.Tk()
root.title("曲线图绘制工具")
root.geometry("800x600")

# 创建输入框
frame_input = tk.Frame(root, padx=10, pady=10)
frame_input.pack(fill=tk.X)

tk.Label(frame_input, text="X 值 (用逗号或空格分隔):").grid(row=0, column=0, sticky=tk.W, padx=5, pady=5)
x_entry = tk.Entry(frame_input, width=50)
x_entry.grid(row=0, column=1, padx=5, pady=5)

tk.Label(frame_input, text="Y 值 (用逗号或空格分隔):").grid(row=1, column=0, sticky=tk.W, padx=5, pady=5)
y_entry = tk.Entry(frame_input, width=50)
y_entry.grid(row=1, column=1, padx=5, pady=5)

# 创建按钮
plot_button = tk.Button(frame_input, text="绘制图表", command=plot_graph, bg="#4CAF50", fg="white", padx=10)
plot_button.grid(row=2, column=0, columnspan=2, pady=10)

# 创建绘图区域
frame_plot = tk.Frame(root, bg="white")
frame_plot.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

# 添加示例值
x_entry.insert(0, "1, 2, 3, 4, 5")
y_entry.insert(0, "2, 4, 6, 8, 10")

# 启动主循环
root.mainloop()