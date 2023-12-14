package com.lagou.dao.impl;

import com.lagou.dao.CourseDao;
import com.lagou.pojo.Course;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * CourseDao 接口的实现类
 */

public class CourseDaoImpl implements CourseDao {
    @Override
    public List<Course> findCourseList() {
        // 1 创建 QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写SQL, 判断是否删除，去除is_del等于0的数据
        String sql = "SELECT id,course_name,price,sort_num,STATUS FROM course WHERE is_del = ?";
        // 3 执行查询
        try {
            List<Course> courseList = qr.query(sql, new BeanListHandler<Course>(Course.class), 0);
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {
        // 1 创建 QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写SQL, 当前的条件为多条件不定查询
        // 2.1 创建StringBuffer对象，将SQL字符串添加进缓冲区
        StringBuffer sb = new StringBuffer(" SELECT id,course_name,price,sort_num,STATUS FROM course WHERE 1=1 AND is_del = ? ");
        // 2.2 创建list集合，保存参数
        List<Object> list = new ArrayList<>();
        list.add(0);
        // 2.3 判断传入的参数是否为空
        if (courseName != null && !courseName.isEmpty()) {
            sb.append(" AND course_name LIKE ?");
            // like模糊查询需要添加%
            list.add("%" + courseName + "%");
        }
        if (status != null && !status.isEmpty()) {
            sb.append(" AND STATUS = ?");
            // 需要将status转换为int
            list.add(Integer.parseInt(status));
        }
        // 3 执行查询
        try {
            List<Course> courseList = qr.query(sb.toString(), new BeanListHandler<Course>(Course.class), list.toArray());
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int saveCourseSalesInfo(Course course) {
        // 保存课程营销信息
        // 1 创建QueryRunner对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        // 2 编写SQL
        String sql = "INSERT INTO course (\n" +
                "course_name,\n" +
                "brief,\n" +
                "teacher_name,\n" +
                "teacher_info,\n" +
                "preview_first_field,\n" +
                "preview_second_field,\n" +
                "discounts,\n" +
                "price,\n" +
                "price_tag,\n" +
                "share_image_title,\n" +
                "share_title,\n" +
                "share_description,\n" +
                "course_description,\n" +
                "course_img_url,\n" +
                "STATUS,\n" +
                "create_time,\n" +
                "update_time\n" +
                ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // 3 准备参数
        Object[] params = {
                course.getCourse_name(),
                course.getBrief(),
                course.getTeacher_name(),
                course.getTeacher_info(),
                course.getPreview_first_field(),
                course.getPreview_second_field(),
                course.getDiscounts(),
                course.getPrice(),
                course.getPrice_tag(),
                course.getShare_image_title(),
                course.getShare_title(),
                course.getShare_description(),
                course.getCourse_description(),
                course.getCourse_img_url(),
                course.getStatus(),
                course.getCreate_time(),
                course.getUpdate_time()
        };
        // 4 执行插入操作
        try {
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Course findCourseById(int id) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT \n" +
                    "id,\n" +
                    "course_name,\n" +
                    "brief,\n" +
                    "teacher_name,\n" +
                    "teacher_info,\n" +
                    "preview_first_field,\n" +
                    "preview_second_field,\n" +
                    "discounts,\n" +
                    "price,\n" +
                    "price_tag,\n" +
                    "course_img_url,\n" +
                    "share_image_title,\n" +
                    "share_title,\n" +
                    "share_description,\n" +
                    "course_description,\n" +
                    "STATUS\n" +
                    "FROM course WHERE id = ?;";
            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), id);
            return course;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateCourseSalesInfo(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "UPDATE course SET \n" +
                    "course_name = ?,\n" +
                    "brief = ?,\n" +
                    "teacher_name = ?,\n" +
                    "teacher_info = ?,\n" +
                    "preview_first_field = ?,\n" +
                    "preview_second_field = ?,\n" +
                    "discounts = ?,\n" +
                    "price = ?,\n" +
                    "price_tag = ?,\n" +
                    "share_image_title = ?,\n" +
                    "share_title = ?,\n" +
                    "share_description = ?,\n" +
                    "course_description = ?,\n" +
                    "course_img_url = ?,\n" +
                    "update_time = ? \n" +
                    "WHERE id = ?";

            Object[] params = {course.getCourse_name(),course.getBrief(),course.getTeacher_name(),course.getTeacher_info(),
                    course.getPreview_first_field(),course.getPreview_second_field(),course.getDiscounts(),course.getPrice(),
                    course.getPrice_tag(),course.getShare_image_title(),course.getShare_title(),course.getShare_description(),course.getCourse_description(),
                    course.getCourse_img_url(),course.getUpdate_time(),course.getId()};
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int updateCourseStatus(Course course) {
        // 修改课程状态
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course set STATUS = ?, update_time = ? WHERE id = ?";
            int update = qr.update(sql, course.getStatus(), course.getUpdate_time(), course.getId());
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
