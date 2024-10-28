// CourseMapper.java
package cn.edu.sdcet.mapper;

import cn.edu.sdcet.entity.Course;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CourseMapper {
    void insertCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(Integer courseId);
    Course getCourseById(Integer courseId);
    List<Course> getAllCourses();
    int countById(Integer courseId);
}