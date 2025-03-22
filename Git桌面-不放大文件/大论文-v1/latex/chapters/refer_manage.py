import os
import re
from typing import List, Dict, Any

def extract_citations_from_tex_files(directory=None):
    """
    Extracts citation IDs from multiple .tex files in a specified directory or current directory.
    
    Reads files 1.tex, 2.tex, 3.tex, and 4.tex sequentially.
    Extracts all \cite{...} occurrences and returns a list of bibliography IDs.
    Maintains the order of first appearance and removes duplicates.
    
    Args:
        directory (str, optional): Path to the directory containing .tex files. 
                                  If None, uses the current directory.
    
    Returns:
        list: A list of all unique bibliography IDs found across all files, 
              preserving the order of first appearance.
    """
    # Use current directory if none specified
    if directory is None:
        directory = os.getcwd()
    
    bib_id_list = []
    seen_ids = set()  # Track seen IDs for deduplication
    
    # Process files in sequence
    for i in range(1, 5):
        filename = f"{i}.tex"
        filepath = os.path.join(directory, filename)
        
        # Check if file exists
        if not os.path.exists(filepath):
            print(f"Warning: {filepath} does not exist. Skipping.")
            continue
        
        try:
            # Read file content
            with open(filepath, 'r', encoding='utf-8') as file:
                content = file.read()
            
            # Extract all \cite{...} patterns - using raw string to avoid escape issues
            cite_patterns = re.findall(r'\\cite\{([^}]+)\}', content)
            
            # Process each citation found
            for cite_content in cite_patterns:
                # Split by comma for multiple citations in one \cite{}
                ids = [bib_id.strip() for bib_id in cite_content.split(',')]
                
                # Add to result list only if not seen before (preserves order of first appearance)
                for bib_id in ids:
                    if bib_id not in seen_ids:
                        seen_ids.add(bib_id)
                        bib_id_list.append(bib_id)
                
        except Exception as e:
            print(f"Error processing {filepath}: {str(e)}")
    
    return bib_id_list
    

class BibliographyManager:
    def __init__(self, file_path: str = 'reference.tex'):
        """初始化参考文献管理器

        Args:
            file_path: reference.tex文件的路径，默认为'reference.tex'
        """
        self.file_path = file_path
        print(f"初始化参考文献管理器，文件路径: {self.file_path}")
    
    def process_bibliography(self, bib_id_list: List[str]) -> List[str]:
        """处理参考文献列表

        实现四个功能：
        1. 对于bib_id_list中有的id但bibitem没有的，如果是注释的则取消注释，完全没有则记录
        2. 对于bib_id_list中没有的id但bibitem有的，进行注释
        3. 按照bib_id_list的顺序对bibitem排序
        4. 将修改的内容写回reference.tex文件

        Args:
            bib_id_list: 需要包含的参考文献ID列表

        Returns:
            缺失的ID列表(如果有)
        """
        print(f"开始处理参考文献列表，共有 {len(bib_id_list)} 个 ID")
        
        # 读取文件
        try:
            with open(self.file_path, 'r', encoding='utf-8') as file:
                content = file.readlines()
            print(f"成功读取文件 {self.file_path}，共 {len(content)} 行")
        except FileNotFoundError:
            print(f"错误：未找到文件 {self.file_path}")
            return []
        
        # 查找参考文献环境的开始和结束位置
        begin_line = -1
        end_line = -1
        
        for i, line in enumerate(content):
            if '\\begin{thebibliography}' in line:
                begin_line = i
            elif '\\end{thebibliography}' in line:
                end_line = i
                break
        
        if begin_line == -1 or end_line == -1:
            print("错误：未找到参考文献环境")
            return []
        
        print(f"找到参考文献环境，开始于第 {begin_line+1} 行，结束于第 {end_line+1} 行")
        
        # 提取文件各部分
        # 找到第一个bibitem的位置
        first_bibitem_index = -1
        for i in range(begin_line + 1, end_line):
            if re.search(r'^\s*(?:%\s*)?\\bibitem{', content[i]):
                first_bibitem_index = i
                break
                
        if first_bibitem_index == -1:
            print("错误：未找到任何bibitem条目")
            return []
            
        print(f"第一个bibitem出现在第 {first_bibitem_index+1} 行")
        
        # 区分格式设置部分和实际条目部分
        header = content[:first_bibitem_index]  # 包含\begin{thebibliography}行和格式设置
        footer = content[end_line:]  # 包含\end{thebibliography}行
        bib_content = content[first_bibitem_index:end_line]
        
        # 解析参考文献条目
        entries = []
        i = 0
        
        while i < len(bib_content):
            # 跳过空行
            if not bib_content[i].strip():
                i += 1
                continue
            
            # 检查是否为bibitem行
            bibitem_match = re.search(r'^\s*(?:%\s*)?\\bibitem{([^}]*)}', bib_content[i])
            
            if bibitem_match:
                bib_id = bibitem_match.group(1)
                is_commented = bib_content[i].lstrip().startswith('%')
                
                # 这行包含bibitem
                entry_lines = [bib_content[i]]
                i += 1
                
                # 收集当前条目的所有行，直到下一个bibitem或空行
                while i < len(bib_content):
                    line = bib_content[i]
                    
                    # 遇到下一个bibitem则停止
                    if re.search(r'^\s*(?:%\s*)?\\bibitem{', line):
                        break
                    
                    # 将此行添加到当前条目
                    if line.strip():
                        entry_lines.append(line)
                        i += 1
                    else:
                        # 空行 - 检查下一行是否是新bibitem
                        if i + 1 < len(bib_content) and re.search(r'^\s*(?:%\s*)?\\bibitem{', bib_content[i + 1]):
                            break
                        i += 1
                
                entries.append({
                    'id': bib_id,
                    'lines': entry_lines,
                    'is_commented': is_commented
                })
                print(f"解析到参考文献条目: {bib_id}，{'已注释' if is_commented else '未注释'}")
            else:
                # 跳过非bibitem行
                i += 1
        
        print(f"共解析到 {len(entries)} 个参考文献条目")
        
        # 处理条目
        missing_ids = []  # 缺失的ID列表
        processed_entries = []  # 处理后的条目
        entry_map = {entry['id']: entry for entry in entries}  # ID到条目的映射
        
        # 功能1：处理bib_id_list中的ID
        for bib_id in bib_id_list:
            if bib_id in entry_map:
                entry = entry_map[bib_id]
                
                # 如果需要取消注释
                if entry['is_commented']:
                    print(f"取消注释参考文献: {bib_id}")
                    new_lines = []
                    for line in entry['lines']:
                        line_stripped = line.strip()
                        if line_stripped and line.lstrip().startswith('%'):
                            # 只对非空行且已注释的行移除注释标记
                            indent = ' ' * (len(line) - len(line.lstrip()))
                            new_line = indent + line.lstrip()[1:].lstrip()
                            new_lines.append(new_line)
                        else:
                            # 保持空行和未注释行不变
                            new_lines.append(line)
                    
                    processed_entries.append({
                        'id': bib_id,
                        'lines': new_lines,
                        'is_commented': False
                    })
                else:
                    print(f"保持参考文献不变: {bib_id}")
                    processed_entries.append(entry)
            else:
                # 记录缺失的ID
                missing_ids.append(bib_id)
                print(f"缺失的参考文献条目: {bib_id}")
        
        # 功能2：处理不在bib_id_list中的ID
        for entry_id, entry in entry_map.items():
            if entry_id not in bib_id_list:
                # 如果需要注释
                if not entry['is_commented']:
                    print(f"注释参考文献: {entry_id}")
                    new_lines = []
                    for line in entry['lines']:
                        line_stripped = line.strip()
                        if line_stripped and not line.lstrip().startswith('%'):
                            # 只对非空行且未注释的行添加注释标记
                            indent = ' ' * (len(line) - len(line.lstrip()))
                            new_line = indent + '%' + line[len(indent):]
                            new_lines.append(new_line)
                        else:
                            # 保持空行和已注释行不变
                            new_lines.append(line)
                    
                    processed_entries.append({
                        'id': entry_id,
                        'lines': new_lines,
                        'is_commented': True
                    })
                else:
                    print(f"保持参考文献已注释: {entry_id}")
                    processed_entries.append(entry)
        
        # 功能3：按照bib_id_list的顺序排序
        sorted_entries = []
        
        # 首先添加bib_id_list中的条目（按顺序）
        for bib_id in bib_id_list:
            for entry in processed_entries:
                if entry['id'] == bib_id:
                    sorted_entries.append(entry)
                    break
        
        # 然后添加不在bib_id_list中的条目
        for entry in processed_entries:
            if entry['id'] not in bib_id_list:
                sorted_entries.append(entry)
        
        print(f"排序后的参考文献条目顺序: {[entry['id'] for entry in sorted_entries]}")
        
        # 功能4：写回文件
        # 重建文件内容
        new_content = header.copy()  # 保持开头和格式设置部分不变
        
        # 添加排序后的条目
        for i, entry in enumerate(sorted_entries):
            new_content.extend(entry['lines'])
            # 只在条目之间添加空行，最后一个条目后不添加
            if i < len(sorted_entries) - 1:
                new_content.append('\n')
        
        new_content.extend(footer)
        
        # 写入文件
        try:
            with open(self.file_path, 'w', encoding='utf-8') as file:
                file.writelines(new_content)
            print(f"成功写入文件 {self.file_path}，共 {len(new_content)} 行")
        except Exception as e:
            print(f"写入文件时出错: {e}")
        
        # 如果有缺失的ID，写入error.txt
        if missing_ids:
            try:
                with open('error.txt', 'w', encoding='utf-8') as error_file:
                    for bib_id in missing_ids:
                        error_file.write(f"缺失的参考文献条目: {bib_id}\n")
                print(f"已将 {len(missing_ids)} 个缺失的参考文献ID写入 error.txt")
            except Exception as e:
                print(f"写入error.txt时出错: {e}")
        else:
            print("没有缺失的参考文献ID")
        
        return missing_ids


if __name__ == "__main__": 
    print("开始处理参考文献...")
    bib_id_list = extract_citations_from_tex_files()
    print(f"提取的引用ID: {bib_id_list}")
    manager = BibliographyManager("./reference.tex")
    missing_ids = manager.process_bibliography(bib_id_list)
    if missing_ids:
        print(f"处理完成，但有以下ID缺失: {missing_ids}")
    else:
        print("处理完成，所有引用都已找到对应条目")
    