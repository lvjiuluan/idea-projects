package com.lagou.service;

import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;

import java.util.List;

/***
 * 课程内容管理 service层 接口
 */

public interface CourseContentService {
    // 根据课程ID查询章节和课时相关信息
    public List<Course_Section> findSectionAndLessonByCourseId(int course_id);

    // 根据章节id，查询章节相关的课时信息
    public List<Course_Lesson> findLessonBySectionId(int section_id);

    // 根据课程id， 回显课程信息
    public Course findCourseById(int course_id);

    // 保存章节信息的方法
    public String saveSection(Course_Section section);

    // 修改章节的方法
    public String updateSection(Course_Section section);

    // 修改章节状态
    public String updateSectionStatus(int section_id, int status);

}
