#encoding = "utf-8"
"""
帮我写一个Python程序，从当前目录下的source.md文件中读取文本内容，文本内容经处理后，存入dest.md。
处理要求：
1、删除文本中出现的词语：[“然后”，“那么”]。
2、当前目录下面有一个替换表文件名为"replace.csv"，该文件有两列，第一列是要替换的词语，第二列是替换后的词语，以逗号分隔,将文本内容出现的词语进行替换,对于英文单词替换在查找的时候不区分大小写，例如：ctrl替换为controller，Ctrl，CTRL，ctRL都会被替换成controller。

"""
import csv
import re

def load_replacements(filename):
    # 读取替换表
    replacements = {}
    with open(filename, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if row:  # 确保行非空
                key, value = row
                # 对于英文单词，忽略大小写
                if key.isalpha():  # 如果关键字是字母，则添加正则，忽略大小写
                    replacements[re.compile(re.escape(key), re.IGNORECASE)] = value
                else:
                    replacements[re.compile(re.escape(key))] = value
    return replacements

def process_text(text, replacements):
    # 执行替换操作
    for pattern, replacement in replacements.items():
        text = pattern.sub(replacement, text)
    return text

def remove_words(text, words):
    # 删除指定的词语
    for word in words:
        text = text.replace(word, '')
    return text

def remove_space(content):
    # 正则表达式匹配汉字和英文字母之间的空格，以及汉字之间的空格
    # \u4e00-\u9fff 是汉字的Unicode范围
    pattern = re.compile(r'(?<=[\u4e00-\u9fff])\s+(?=[\u4e00-\u9fff])|(?<=[\u4e00-\u9fff])\s+(?=[a-zA-Z])|(?<=[a-zA-Z])\s+(?=[\u4e00-\u9fff])')
    # 使用空字符串替换找到的匹配项
    return pattern.sub('', content)

def main():
    # 要删除的词语列表
    words_to_remove = ['然后', '那么']
    
    # 加载替换表
    replacements = load_replacements('replace.csv')
    
    # 读取原始文本
    with open('source.md', 'r', encoding='utf-8') as file:
        content = file.read()
    
    # 删除空格
    # conten = remove_space(content);
    
    # 删除词语
    content = remove_words(content, words_to_remove)
    
    # 应用替换
    processed_text = process_text(content, replacements)
    
    # 存入新文件
    with open('dest.md', 'w', encoding='utf-8') as file:
        file.write(processed_text)

if __name__ == "__main__":
    main()
