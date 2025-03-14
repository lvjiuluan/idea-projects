import os

def replace_delimiters(file_path):
    """
    读取指定文件，替换特定的分隔符字符串，并将结果写回原文件
    
    替换规则：
    - '\[' 和 '\]' 替换为 '$$'
    - '\(' 和 '\)' 替换为 '$'
    
    Args:
        file_path: 要处理的文件路径
    """
    try:
        # 检查文件是否存在
        if not os.path.exists(file_path):
            print(f"错误：文件 {file_path} 不存在")
            return False
        
        # 读取文件内容
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        
        # 替换字符串
        # 替换 '\[' 和 '\]' 为 '$$'
        content = content.replace('\\[', '$$').replace('\\]', '$$')
        
        # 替换 '\(' 和 '\)' 为 '$'
        content = content.replace('\\(', '$').replace('\\)', '$')
        
        # 将修改后的内容写回文件
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(content)
        
        print(f"成功替换文件 {file_path} 中的分隔符")
        return True
    
    except Exception as e:
        print(f"处理文件时出错：{e}")
        return False

if __name__ == "__main__":
    file_path = "replace.txt"
    replace_delimiters(file_path)
