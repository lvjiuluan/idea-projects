#encoding=utf-8

# ��ȡsource.md�ļ���Ϊÿһ�����"## "��Ȼ��д��target.md�ļ�

def add_prefix_to_lines(source_file, target_file, prefix="## "):
    with open(source_file, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    with open(target_file, 'w', encoding='utf-8') as file:
        for line in lines:
            file.write(prefix + line)

# ���ú��������ļ�
add_prefix_to_lines('source.md', 'target.md')
