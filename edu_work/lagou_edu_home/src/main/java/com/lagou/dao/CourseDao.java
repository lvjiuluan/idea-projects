package com.lagou.dao;

import com.lagou.pojo.Course;

import java.util.List;

/***
 * 课程模块DAO
 */

public interface CourseDao {
    // 查询课程列表信息
    public List<Course> findCourseList();

    // 根据条件查询课程信息
    public List<Course> findByCourseNameAndStatus(String courseName, String status);

    // 保存课程营销信息
    public int saveCourseSalesInfo(Course course);

    // 根据ID查询课程信息
    public Course findCourseById(int id);

    // 修改课程营销信息的方法
    public int updateCourseSalesInfo(Course course);

    // 修改课程状态
    public int updateCourseStatus(Course course);

}
