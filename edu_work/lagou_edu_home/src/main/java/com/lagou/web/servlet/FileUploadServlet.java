package com.lagou.web.servlet;

import com.lagou.utils.UUIDUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 创建磁盘文件工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 2 创建文件上传核心类
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 2.1 设置上传文件编码
        upload.setHeaderEncoding("utf-8");
        // 2.2 判断表单是否是文件上传表单
        boolean multipartContent = upload.isMultipartContent(req);
        // 2.3 如果是文件上传表单
        if (multipartContent) {

            // 3 解析request 获取表单项的集合
            try {
                List<FileItem> list = upload.parseRequest(req);
                if (list != null) {
                    // 4 遍历表单项的集合，获取每一个表单项
                    for (FileItem fileItem : list) {
                        // 5 判断是普通的表单项还是文件上传项
                        boolean formField = fileItem.isFormField();
                        if (formField) {
                            // 普通表单项
                            String fieldName = fileItem.getFieldName();
                            String fieldValue = fileItem.getString("utf-8");
                            System.out.println(fieldName + "=" + fieldValue);

                        } else {
                            // 文件上传项
                            // 获取文件名
                            String fileName = fileItem.getName();
                            System.out.println(fileName);
                            // 拼接新的文件名，保证不重复
                            String newFileName = UUIDUtils.getUUID() + "_" + fileName;
                            // 获取输入流
                            InputStream in = fileItem.getInputStream();
                            // 创建输出流
                            System.out.println(newFileName);
                            // a 获取项目的运行目录
                            String path = this.getServletContext().getRealPath("/");
                            String webappsPath = path.substring(0, path.indexOf("lagou_edu_home"));
                            System.out.println(webappsPath);
                            FileOutputStream out = new FileOutputStream(webappsPath + "/upload/" + newFileName);
                            // 使用IOUtils完成文件的copy
                            IOUtils.copy(in, out);
                            // 关闭流
                            out.close();
                            in.close();
                        }
                    }

                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }
}
