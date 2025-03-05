def process_text(text: str) -> str:
    """
    处理BibTeX格式的引用文本
    删除每个引用中的abstract字段，
    并确保每个条目的最后一个字段不带逗号。
    
    参数：
        text (str): 原始BibTeX文本。
    
    返回：
        str: 处理后的文本，不包含abstract字段且最后一个字段没有逗号。
    """
    # 分割文本为单独的引用条目
    entries = []
    current_entry = []
    in_entry = False
    
    for line in text.split('\n'):
        if line.strip().startswith('@'):
            # 新条目开始
            if in_entry:
                entries.append('\n'.join(current_entry))
            current_entry = [line]
            in_entry = True
        elif line.strip() == '}':
            # 条目结束
            current_entry.append(line)
            entries.append('\n'.join(current_entry))
            current_entry = []
            in_entry = False
        elif in_entry:
            current_entry.append(line)
    
    # 如果最后一个条目没有被添加
    if current_entry:
        entries.append('\n'.join(current_entry))
    
    # 处理每个条目，删除abstract字段并修复最后一个字段的逗号
    processed_entries = []
    for entry in entries:
        lines = entry.split('\n')
        filtered_lines = []
        skip_line = False
        
        for i, line in enumerate(lines):
            if 'abstract' in line and '=' in line:
                skip_line = True
                # 查找abstract字段的结束位置
                if line.strip().endswith('}'):
                    skip_line = False
            elif skip_line:
                if '}' in line and not any(field in line for field in ['title', 'author', 'journal', 'year']):
                    skip_line = False
                    continue
            else:
                filtered_lines.append(line)
        
        # 找到最后一个属性行并移除逗号
        for i in range(len(filtered_lines) - 2, -1, -1):
            line = filtered_lines[i]
            if '=' in line and line.strip().endswith(','):
                filtered_lines[i] = line.rstrip(',')
                break
        
        processed_entries.append('\n'.join(filtered_lines))
    
    return '\n'.join(processed_entries)

