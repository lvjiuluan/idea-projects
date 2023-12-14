package com.lagou.web.servlet;

import com.lagou.base.Constant;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    // 保存课程营销信息
    // 同时也是修改课程营销信息
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.printf("1运行到我这里来了！！！！");
            // 1 收集表单的数据，封装到Course实体中
            // 1.1 创建Course对象
            Course course = new Course();
            // 1.2 创建Map集合，用来收集数据
            Map<String, Object> map = new HashMap<String, Object>();
            // 1.3
            // 2 将图片上传到tomcat服务器
            // 2.1 创建磁盘工程对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2.2 创建文件上传核心对象
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 2.3 解析request对象，获取表单项集合
            List<FileItem> list = upload.parseRequest(req);
            // 2.4 遍历集合
            for (FileItem fileItem : list) {
                boolean formField = fileItem.isFormField();
                // 判断是否为普通表单项
                if (formField) {
                    // 普通表单项
                    // 获取表单项中的数据，并且保存到map中
                    String fieldName = fileItem.getFieldName();
                    String fieldValue = fileItem.getString("utf-8");
                    map.put(fieldName, fieldValue);
                } else {
                    // 文件上传项
                    // 获取文件名
                    String fileName = fileItem.getName();
                    // 文件名拼接
                    fileName = UUIDUtils.getUUID() + "_" + fileName;
                    // 获取输入流
                    InputStream in = fileItem.getInputStream();
                    // 拼接路径
                    String realPath = this.getServletContext().getRealPath("/");
                    String webapps = realPath.substring(0, realPath.indexOf("lagou_edu_home"));
                    // 获取输出流
                    FileOutputStream out = new FileOutputStream(webapps + "/upload/" + fileName);
                    // 复制文件
                    IOUtils.copy(in, out);
                    // 关闭流
                    out.close();
                    in.close();
                    // 将图片路径进行保存
                    map.put("course_img_url", Constant.LOCAL_URL + "/upload/" + fileName);
                }
            }
            System.out.printf("2运行到我这里来了！！！！");
            // 使用BeanUtils将map中的数据封装到course对象中
            System.out.println(map.get("courseSectionList"));
            BeanUtils.populate(course, map);
            System.out.printf("3运行到我这里来了！！！！");
            String formart = DateUtils.getDateFormart();
            CourseService cs = new CourseServiceImpl();

            /***
             * 在这里进行一个判断
             */
            if (map.get("id") != null) {
                // 证明是一个修改操作
                // 补全信息
                course.setUpdate_time(formart);
                // 业务处理
                System.out.printf("运行到我这里来了！！！！");
                String result = cs.updateCourseSalesInfo(course);
                // 响应结果
                resp.getWriter().print(result);

            } else {
                // 新建操作
                // 将course信息补全
                course.setCreate_time(formart);
                course.setUpdate_time(formart);
                course.setStatus(1); // 1 上架
                // 业务处理
                String result = cs.saveCourseSalesInfo(course);
                // 响应结果
                resp.getWriter().print(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
