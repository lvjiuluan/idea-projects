package com.lagou.service.impl;

import com.lagou.base.StatusCode;
import com.lagou.dao.CourseContentDao;
import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.utils.DateUtils;
import com.lagou.utils.DruidUtils;

import java.util.List;

public class CourseContentServiceImpl implements CourseContentService {

    CourseContentDao contentDao = new CourseContentDaoImpl();

    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int course_id) {
        List<Course_Section> sectionList = contentDao.findSectionAndLessonByCourseId(course_id);
        return sectionList;
    }

    @Override
    public List<Course_Lesson> findLessonBySectionId(int section_id) {
        List<Course_Lesson> lessonList = contentDao.findLessonBySectionId(section_id);
        return lessonList;
    }

    @Override
    public Course findCourseById(int course_id) {
        Course course = contentDao.findCourseById(course_id);
        return course;
    }

    @Override
    public String saveSection(Course_Section section) {
        // 补全章节信息
        section.setStatus(2);
        String dateFormart = DateUtils.getDateFormart();
        section.setCreate_time(dateFormart);
        section.setUpdate_time(dateFormart);
        // 插入操作
        int i = contentDao.saveSection(section);
        if (i > 0) {
            return StatusCode.SUCCESS.toString();
        }
        return StatusCode.FAIL.toString();
    }

    @Override
    public String updateSection(Course_Section section) {
        // 补全信息
        section.setUpdate_time(DateUtils.getDateFormart());
        int i = contentDao.updateSection(section);
        if (i > 0) {
            return StatusCode.SUCCESS.toString();
        }
        return StatusCode.FAIL.toString();
    }

    @Override
    public String updateSectionStatus(int section_id, int status) {
        int i = contentDao.updateSectionStatus(section_id, status);
        if (i > 0) {
            return StatusCode.SUCCESS.toString();
        }
        return StatusCode.FAIL.toString();
    }
}
