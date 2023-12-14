package com.lagou.service;

import com.lagou.pojo.Course;

import java.util.List;
import java.util.Map;

/***
 * 课程管理模块 Service 接口
 */

public interface CourseService {
    // 查询课程列表信息
    public List<Course> findCourseList();

    // 根据条件查询课程信息
    public List<Course> findByCourseNameAndStatus(String courseName, String status);

    // 保存课程营销信息
    public String saveCourseSalesInfo(Course course);

    // 根据ID查询课程信息
    public Course findCourseById(int id);

    // 修改课程营销信息的方法
    public String updateCourseSalesInfo(Course course);

    // 修改课程状态
    public Map<String,Integer> updateCourseStatus(Course course);
}
