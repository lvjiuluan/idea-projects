import re

def process_text(text: str) -> str:
    """
    citep 引用格式转换
    将 \\textsuperscript{\\cite{...}} 格式替换为 \\citep{...} 格式

    参数：
        text (str): 原始文本。

    返回：
        str: 处理后的文本。
    """
    # 使用正则表达式查找并替换引用格式
    pattern = r'\\textsuperscript{\\cite{([^}]*)}}' 
    replacement = r'\\citep{\1}'
    
    # 执行替换
    processed_text = re.sub(pattern, replacement, text)
    
    return processed_text
