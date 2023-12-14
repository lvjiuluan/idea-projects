package com.lagou.dao;

// 查询对应课程下的章节与课时

import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.List;

public class TestCourseContentDao {
    CourseContentDao contentDao = new CourseContentDaoImpl();

    @Test
    public void testfindSectionAndLessonByCourseId() {
        List<Course_Section> sectionList = contentDao.findSectionAndLessonByCourseId(59);
        for (Course_Section course_section : sectionList) {
            System.out.println(course_section.getId() + "," + course_section.getSection_name());
            for (Course_Lesson course_lesson : course_section.getCourseLessonList()) {
                System.out.println(course_lesson.getId() + "," + course_lesson.getTheme() + "," + course_lesson.getSection_id());
            }
            System.out.println("**************************************************");
        }

    }

    // 测试，根据课程id回显课程名称
    @Test
    public void testfindCourseById() {
        Course course = contentDao.findCourseById(59);
        System.out.println(course);
    }

    // 测试保存章节信息
    @Test
    public void testsvaeSection() {
        Course_Section section = new Course_Section();
        section.setCourse_id(59);
        section.setSection_name("Vue高级2");
        section.setDescription("Vue相关的高级技术");
        section.setOrder_num(8);

        String dateFormart = DateUtils.getDateFormart();
        section.setCreate_time(dateFormart);
        section.setUpdate_time(dateFormart);
        section.setStatus(2);
        // 0 隐藏，1 待更新， 2 已发布
        int i = contentDao.saveSection(section);
        System.out.println(i);
    }

    // 测试修改章节功能
    @Test
    public void testupdateSection() {
        Course_Section section = new Course_Section();
        section.setId(41);
        section.setSection_name("物资 yyds");
        section.setDescription("一起来看流星雨");
        section.setOrder_num(3);
        section.setUpdate_time(DateUtils.getDateFormart());
        int i = contentDao.updateSection(section);
        System.out.println(i);
    }

    // 测试修改章节状态
    @Test
    public void testUpdateSectionStatus() {
        int i = contentDao.updateSectionStatus(1, 2);
        System.out.println(i);
    }
}
