package com.lagou.dao.impl;

import com.lagou.dao.CourseContentDao;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CourseContentDaoImpl implements CourseContentDao {
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int course_id) {
        try {
            // 创建queryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            // 编写SQL
            // 先把课程对应的章节信息查出来
            String sql = "SELECT\n" +
                    "id,\n" +
                    "course_id,\n" +
                    "section_name,\n" +
                    "description,\n" +
                    "order_num,\n" +
                    "STATUS\n" +
                    "FROM course_section\n" +
                    "WHERE course_id=?";
            List<Course_Section> sectionList = qr.query(sql, new BeanListHandler<Course_Section>(Course_Section.class), course_id);
            // 将课时信息查出来
            for (Course_Section course_section : sectionList) {
                // 根据章节id查询课时信息
                List<Course_Lesson> lessonList = findLessonBySectionId(course_section.getId());
                course_section.setCourseLessonList(lessonList);
            }
            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course_Lesson> findLessonBySectionId(int section_id) {
        try {
            // 根据章节id，查询章节相关的课时信息
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT\n" +
                    "id,\n" +
                    "course_id,\n" +
                    "section_id,\n" +
                    "theme,\n" +
                    "duration,\n" +
                    "is_free,\n" +
                    "order_num,\n" +
                    "STATUS\n" +
                    "FROM course_lesson\n" +
                    "WHERE section_id=?";
            List<Course_Lesson> lessonList = qr.query(sql, new BeanListHandler<Course_Lesson>(Course_Lesson.class), section_id);
            return lessonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course findCourseById(int course_id) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT \n" +
                    "id,\n" +
                    "course_name\n" +
                    "FROM course WHERE id = ?;";
            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), course_id);
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int saveSection(Course_Section section) {
        try {
            // 保存章节信息
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "INSERT INTO course_section (\n" +
                    "course_id, \n" +
                    "section_name,\n" +
                    "description,\n" +
                    "order_num,\n" +
                    "STATUS,\n" +
                    "create_time,\n" +
                    "update_time)\n" +
                    "VALUES(?,?,?,?,?,?,?)\n";
            Object[] params = {section.getCourse_id(), section.getSection_name()
                    , section.getDescription(), section.getOrder_num(),
                    section.getStatus(), section.getCreate_time(), section.getUpdate_time()};
            int row = qr.update(sql, params);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateSection(Course_Section section) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_section set " +
                    "section_name=?, " +
                    "description=?, " +
                    "order_num=?, " +
                    "update_time=?" +
                    " where id = ?";
            Object[] params = {section.getSection_name(),
                    section.getDescription(), section.getOrder_num(),
                    section.getUpdate_time(), section.getId()};
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateSectionStatus(int section_id, int status) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_section set status = ?, " +
                    "update_time = ? " +
                    "where id = ?";
            int update = qr.update(sql, status, DateUtils.getDateFormart(),section_id);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
