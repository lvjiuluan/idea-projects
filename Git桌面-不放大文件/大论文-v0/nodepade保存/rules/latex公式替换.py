def process_text(text: str) -> str:
    """ 
    一般替换

    参数：
        text (str): 原始文本。

    返回：
        str: 处理后的文本。
    """
    # 在这里编写处理逻辑
    text = text.replace('#', '').replace('*', '').replace(r'%',r'\%')
    processed_text = (
        text.replace("\\(", "$")
        .replace("\\)", "$")
        .replace("\\[", "$$")
        .replace("\\]", "$$")
        .replace("*", "")  # 替换 * 为 空字符串
    )
 
    return processed_text



