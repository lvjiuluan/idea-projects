import re

def process_text(text: str) -> str:
    """
    删除以 [数字] 开头的行

    参数：
        text (str): 原始文本。

    返回：
        str: 处理后的文本。
    """
    # 使用正则表达式匹配以 [数字] 开头的行，并删除它们
    processed_text = re.sub(r'^\[\d+\].*\n?', '', text, flags=re.MULTILINE)
    return processed_text

