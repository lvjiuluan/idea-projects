package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/course")
public class CourseServlet extends BaseServlet {
    // 查询课程信息列表功能
    public void findCourseList(HttpServletRequest request, HttpServletResponse response) {
        // 1 接受参数
        // 2 业务处理
        CourseService cs = new CourseServiceImpl();
        List<Course> courseList = cs.findCourseList();
        // 3 结果响应
        // 3.1 指定要转换的JSON字段
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,
                "id", "course_name", "price", "sort_num", "status");
        String result = JSON.toJSONString(courseList, filter);
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
        }
    }

    // 根据条件查询课程信息
    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response) {
        // 1 接收参数
        String course_name = request.getParameter("course_name");
        String status = request.getParameter("status");
        // 2 调用业务处理
        CourseService cs = new CourseServiceImpl();
        List<Course> courseList = cs.findByCourseNameAndStatus(course_name, status);
        // 3 返回结果
        // 3.1 结果过滤
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name", "status");
        String result = JSON.toJSONString(courseList, filter);
        // 响应
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程id查询课程信息
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1 接收参数
            String id = request.getParameter("id");
            // 2 业务处理
            CourseService cs = new CourseServiceImpl();
            Course course = cs.findCourseById(Integer.parseInt(id));
            // 3 返回结果
            // 3.1 指定需要转换的对象
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,
                    "id",
                    "course_name",
                    "brief",
                    "teacher_name",
                    "teacher_info",
                    "price",
                    "price_tag",
                    "discounts",
                    "preview_first_field",
                    "preview_second_field",
                    "course_img_url",
                    "share_title",
                    "share_description",
                    "course_description",
                    "share_image_title");
            String result = JSON.toJSONString(course,filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 修改课程状态
    public void updateCourseStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1 接收参数
            String id = request.getParameter("id");
            // 2 业务处理
            CourseService cs = new CourseServiceImpl();
            Course course = cs.findCourseById(Integer.parseInt(id));
            // 2.1 判断课程信息状态，进行一个取反操作
            int status = course.getStatus();
            if (status == 0) {
                course.setStatus(1);
            } else {
                course.setStatus(0);
            }
            // 2.2 设置更新时间
            course.setUpdate_time(DateUtils.getDateFormart());
            // 3 调用修改状态的方法
            Map<String, Integer> map = cs.updateCourseStatus(course);
            // 4 返回响应结果
            String result = JSON.toJSONString(map);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
