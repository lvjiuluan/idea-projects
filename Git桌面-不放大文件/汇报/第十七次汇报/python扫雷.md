大家好，相信许多人很早就知道有扫雷这么一款经典的游戏，更是有不少人曾听说过中国雷圣，也是中国扫雷第一、世界综合排名第二的郭蔚嘉的顶顶大名。扫雷作为一款在Windows9x时代就已经诞生的经典游戏，从过去到现在依然都有着它独特的魅力：快节奏高精准的鼠标操作要求、快速的反应能力、刷新纪录的快感，这些都是扫雷给雷友们带来的、只属于扫雷的独一无二的兴奋点。

本文将介绍使用Python+OpenCV实现自动扫雷。对于自动扫雷而言，大致的开发过程是这样的：完成窗体内容截取部分，雷块分割，实现雷块类型识别，进而构建扫雷算法。

# 1.窗体截取

窗体截取是一个逻辑上简单，但实现起来却相对麻烦且不可少的部分。通过 **Spy++** 得到了以下两点信息：

- **ms_arbiter.exe** 的主窗体类别名为 `"TMain"`
- **ms_arbiter.exe** 的主窗体名称为 `"Minesweeper Arbiter "`

## 使用 `win32gui` 获取窗体位置

通过 `win32gui` 来获取窗体的位置信息，具体代码如下：

```python
class_name = "TMain"
title_name = "Minesweeper Arbiter "

hwnd = win32gui.FindWindow(class_name, title_name)
if hwnd:
    left, top, right, bottom = win32gui.GetWindowRect(hwnd)
```

通过以上代码，得到了窗体相对于整个屏幕的位置，之后通过 **PIL** 来进行扫雷界面的棋盘截取。

```python
from PIL import ImageGrab

left += 15
top += 101
right -= 15
bottom -= 43

rect = (left, top, right, bottom)
img = ImageGrab.grab().crop(rect)
```

# 2.雷块分割 

在进行雷块分割之前，事先需要了解雷块的尺寸以及它的边框大小，在ms_arbiter下，每一个雷块的尺寸为16px*16px。知道雷块尺寸，就可以进行每一个雷块的裁剪。

```python
block_width, block_height = 16, 16
  blocks_x = int((right - left) / block_width)
  blocks_y = int((bottom - top) / block_height)
```

建立一个二维数组用于存储每一个雷块的图像，并且进行图像分割，保存在之前建立的数组中。

```python
def crop_block(hole_img, x, y):
        x1, y1 = x * block_width, y * block_height
        x2, y2 = x1 + block_width, y1 + block_height
return hole_img.crop((x1, y1, x2, y2))
 
blocks_img = [[0 for i in range(blocks_y)] for i in range(blocks_x)]
 
for y in range(blocks_y):
for x in range(blocks_x):
        blocks_img[x][y] = crop_block(img, x, y)
```

 将整个图像获取、分割的部分封装成一个库，随时调用，将这一部分封装成imageProcess.py，其中函数get_frame()用于完成上述的图像获取、分割过程。

# 3.雷块识别

这一部分可能是整个项目里除了[扫雷算法](https://so.csdn.net/so/search?q=扫雷算法&spm=1001.2101.3001.7020)本身之外最重要的部分，在进行雷块检测的时候采用了比较简单的特征，高效并且可以满足要求。 

