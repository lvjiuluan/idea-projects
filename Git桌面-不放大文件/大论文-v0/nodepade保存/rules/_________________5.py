import re

def process_text(text: str) -> str:
    """
    去掉英文单词与中文之间多余的空格

    参数：
        text (str): 原始文本。

    返回：
        str: 处理后的文本。
    """
    # 匹配中文和英文之间的空格
    # 先处理中文后面跟空格再跟英文的情况
    text = re.sub(r'([\u4e00-\u9fa5])\s+([a-zA-Z])', r'\1\2', text)
    # 再处理英文后面跟空格再跟中文的情况
    text = re.sub(r'([a-zA-Z])\s+([\u4e00-\u9fa5])', r'\1\2', text)
    
    return text

