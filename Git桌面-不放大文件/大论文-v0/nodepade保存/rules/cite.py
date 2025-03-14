import re

def process_text(text: str) -> str:
    """
    cite替换

    Args:
        text (str): 需要处理的原始 LaTeX 文本。

    Returns:
        str: 转换后的 LaTeX 文本，引用部分被转换为上标格式。

    Example:
        >>> process_text("See \\cite{author2020} for details.")
        'See \\textsuperscript{\\cite{author2020}} for details.'
    """
    # 使用正则表达式替换 \cite{...} 为 \textsuperscript{\cite{...}}
    processed_text = re.sub(r'\\cite\{([^}]*)\}', r'\\textsuperscript{\\cite{\1}}', text)
    
    return processed_text


