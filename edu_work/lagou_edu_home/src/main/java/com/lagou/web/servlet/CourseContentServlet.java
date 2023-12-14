package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.service.impl.CourseContentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/courseContent")
public class CourseContentServlet extends BaseServlet {

    //根据课程ID查询课程章节和课时信息
    public void findSectionAndLessonByCourseId(HttpServletRequest request, HttpServletResponse response) {
        try {
            String course_id = request.getParameter("course_id");
            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            List<Course_Section> sectionList = contentService.findSectionAndLessonByCourseId(Integer.parseInt(course_id));
            // 返回结果
            String result = JSON.toJSONString(sectionList);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程id查询棵
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 接收参数
            String course_id = request.getParameter("course_id");
            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            Course course = contentService.findCourseById(Integer.parseInt(course_id));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name");
            String result = JSON.toJSONString(course, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存&修改章节信息
    public void saveOrUpdateSection(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 接收参数
            Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
            // 创建章节对象
            Course_Section section = new Course_Section();
            // 设置参数， 使用BeanUtils工具类，将map中的数据转到section中
//            BeanUtils.populate(section, map);
            BeanUtils.copyProperties(section,map.get("section"));
            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();

            /**
             * 在这里做判断
             */
            String result = null;
            // 判断是否携带id
            if (section.getId() == 0) {
                // 新增操作
                result = contentService.saveSection(section);
            } else {
                // 修改操作
                result = contentService.updateSection(section);
            }
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改章节状态方法
    public void updateSectionStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 接收参数
            String id = request.getParameter("id");
            String status = request.getParameter("status");
            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            String result = contentService.updateSectionStatus(Integer.parseInt(id), Integer.parseInt(status));
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
