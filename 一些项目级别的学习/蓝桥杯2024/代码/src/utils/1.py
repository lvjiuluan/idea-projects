from openai import OpenAI
import paramiko

def sendFile():
    # 设置SSH连接参数
    ssh = paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(hostname='43.142.181.137', username='root', password='13110197267ljlLJL!')
    
    # 设置源文件路径和目标路径
    source_file = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\utils\answer.txt"
    target_folder = '/usr/local/nginx/html/answer.txt'
    
    # 使用SFTP传输文件
    sftp = ssh.open_sftp()
    sftp.put(source_file, target_folder)  # 目标文件名可以按需更改
    sftp.close()
    
    # 关闭SSH连接
    ssh.close()


def writeQuestion(filePath = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\main\Solution.java"):
    destFilePath = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\utils\question.txt"
    with open(filePath, 'r',encoding="utf-8") as file, open(destFilePath,'w',encoding="utf-8") as destFile:
        for line in file:
            line = line.strip()  # 去掉左右两端的空白字符
            if line.startswith("//"):  # 检查是否以"//"开始
                line = line[2:]  # 去掉"//"
                destFile.write(line + '\n')  # 将修改后的行写入destFilePath

def getUserMessage(filePath = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\utils\question.txt"):
    with open(filePath,encoding="utf-8") as f:
        user_message = f.read();
        return user_message
def writeUserMessage(user_message,filePath = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\utils\answer.txt"):
    with open(filePath,'a',encoding="utf-8") as f:
        f.write("*"*20 + "\n\n")
        f.write(user_message)
        f.write("\n\n")
def writeContent(content,filePath = r"D:\IDEA Projects\一些项目级别的学习\蓝桥杯2024\代码\src\utils\answer.txt"):
    with open(filePath,'a',encoding="utf-8") as f:
        f.write(content)
        f.write("\n\n")



def get_response(user_message):
    api_key = ''
    client = OpenAI(api_key="sk-dV1vmy9KJ1GDr3OMNOqAlSUnIMR3S6wbsGRSKKTGYpXhAWMn", base_url="https://api.chatanywhere.tech")

    response = client.chat.completions.create(
        # model="gpt-3.5-turbo",
        model="gpt-4",
        messages=[
            {"role": "system", "content": "你是一个数学博士，同时也精通Java编程语言，熟悉各种算法"},
            {"role": "user", "content": user_message}
        ]
    )
    writeUserMessage(user_message)
    writeContent(response.choices[0].message.content)
    return response.choices[0].message.content
    
writeQuestion()
get_response(getUserMessage())
#sendFile()