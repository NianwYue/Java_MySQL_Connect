// GradeMapper.java
package cn.edu.sdcet.mapper;

import cn.edu.sdcet.entity.Grade;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface GradeMapper {
    void insertGrade(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
    void updateGrade(Grade grade);
    void deleteGradeByStudentId(Integer studentId);
    void deleteGradeByCourseId(Integer courseId);
    Grade getGrade(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
    List<Grade> getAllGrades();
    void updateCourseSelection(@Param("studentId") Integer studentId, @Param("oldCourseId") Integer oldCourseId, @Param("newCourseId") Integer newCourseId);
}