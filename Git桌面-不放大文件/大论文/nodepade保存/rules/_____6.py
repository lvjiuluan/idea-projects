import re

def process_text(text: str) -> str:
    """
    替换公式
    将文本中的 $$...$$ 替换为 \begin{equation}... \end{equation}。
    
    参数：
        text (str): 原始文本。
        
    返回：
        str: 处理后的文本。
    """
    # 使用正则表达式匹配 $$ ... $$ 并替换为 \begin{equation} ... \end{equation}
    processed_text = re.sub(r'\$\$(.*?)\$\$', r'\\begin{equation}\1\\end{equation}', text, flags=re.DOTALL)
    
    return processed_text

