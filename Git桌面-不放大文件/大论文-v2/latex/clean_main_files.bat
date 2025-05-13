@echo off
setlocal enabledelayedexpansion

echo 准备删除当前目录下的main.*文件(保留main.tex)...
echo.

set "found=0"
set "deleted=0"

rem 遍历所有main.*文件
for %%f in (main.*) do (
    set /a found+=1
    
    rem 检查扩展名是否为.tex
    if /i not "%%~xf"==".tex" (
        echo 删除: %%f
        del "%%f"
        set /a deleted+=1
    ) else (
        echo 保留: %%f
    )
)

echo.
if %found%==0 (
    echo 未找到任何main.*文件。
) else (
    echo 操作完成: 共找到%found%个main.*文件，删除了%deleted%个文件。
)

pause