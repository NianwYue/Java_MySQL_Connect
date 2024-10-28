// StudentMapper.java
package cn.edu.sdcet.mapper;

import cn.edu.sdcet.entity.Student;

import java.util.List;

public interface StudentMapper {
    void insertStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Integer studentId);
    Student getStudentById(Integer studentId);
    List<Student> getAllStudents();
    int countById(Integer studentId);
}



