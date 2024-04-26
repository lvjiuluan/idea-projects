#encoding=utf-8

# 读取source.md文件，为每一行添加"## "，然后写入target.md文件

def add_prefix_to_lines(source_file, target_file, prefix="## "):
    with open(source_file, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    with open(target_file, 'w', encoding='utf-8') as file:
        for line in lines:
            file.write(prefix + line)

# 调用函数处理文件
add_prefix_to_lines('source.md', 'target.md')
