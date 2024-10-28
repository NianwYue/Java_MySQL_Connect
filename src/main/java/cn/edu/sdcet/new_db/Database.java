package cn.edu.sdcet.new_db;

import cn.edu.sdcet.entity.Course;
import cn.edu.sdcet.entity.Grade;
import cn.edu.sdcet.entity.Student;
import cn.edu.sdcet.mapper.StudentMapper;
import cn.edu.sdcet.mapper.CourseMapper;
import cn.edu.sdcet.mapper.GradeMapper;
import cn.edu.sdcet.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Database {

    public static void addStudent(Scanner scan) {
        try {
            System.out.println("请输入学号: ");
            int studentId = scan.nextInt();
            scan.nextLine();
            System.out.println("请输入学生姓名: ");
            String name = scan.nextLine();
            System.out.println("请输入学生的性别(男/女): ");
            String gender = scan.nextLine();
            System.out.println("请输入学生的出生日期(年-月-日): ");
            String birthDate = scan.nextLine();
            System.out.println("请输入学生的政治面貌: ");
            String politicalStatus = scan.nextLine();

            Student student = new Student();
            student.setStudentId(studentId);
            student.setName(name);
            student.setSex(gender);
            student.setBirthDate(Date.valueOf(birthDate));
            student.setPolitical(politicalStatus);

            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
                StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
                mapper.insertStudent(student);
                System.out.println("学生添加成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加学生失败: " + e.getMessage());
        }
    }

    public static void addCourse(Scanner scan) {
        try {
            System.out.println("请输入课程号: ");
            int courseId = scan.nextInt();
            scan.nextLine();
            System.out.println("请输入课程名: ");
            String courseName = scan.nextLine();
            System.out.println("请输入课程的学分: ");
            int credits = scan.nextInt();

            Course course = new Course();
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setCredits(credits);

            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
                CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
                mapper.insertCourse(course);
                System.out.println("课程添加成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加课程失败: " + e.getMessage());
        }
    }

    public static void enrollStudentInCourse(Scanner scan) {
        try {
            System.out.println("请输入学生ID: ");
            int studentId = scan.nextInt();
            System.out.println("请输入课程ID: ");
            int courseId = scan.nextInt();

            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
                StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
                CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
                GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);

                if (studentMapper.countById(studentId) == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                if (courseMapper.countById(courseId) == 0) {
                    System.out.println("课程ID不存在.");
                    return;
                }

                gradeMapper.insertGrade(studentId, courseId);
                System.out.println("学生选课成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("选课失败: " + e.getMessage());
        }
    }
    public static void updateStudent(Scanner scan) {
        try {
            System.out.println("请输入要更新的学生的学号: ");
            int studentId = scan.nextInt();
            scan.nextLine();

            System.out.println("请输入新的名字: ");
            String name = scan.nextLine();
            System.out.println("请输入新的性别(男/女): ");
            String gender = scan.nextLine();
            System.out.println("请输入新的出生日期(年-月-日): ");
            String birthDate = scan.nextLine();
            System.out.println("请输入新的政治面貌: ");
            String politicalStatus = scan.nextLine();

            Student student = new Student();
            student.setStudentId(studentId);
            student.setName(name);
            student.setSex(gender);
            student.setBirthDate(Date.valueOf(birthDate));
            student.setPolitical(politicalStatus);

            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
                StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
                mapper.updateStudent(student);
                sqlSession.commit();
                System.out.println("学生信息更新成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("更新学生信息失败: " + e.getMessage());
        }
    }

    public static void updateCourse(Scanner scan) {
        try {
            System.out.println("请输入要更新的课程的课程号: ");
            int courseId = scan.nextInt();
            scan.nextLine();

            System.out.println("请输入新的课程名: ");
            String courseName = scan.nextLine();
            System.out.println("请输入新的课程的学分: ");
            int credits = scan.nextInt();

            Course course = new Course();
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setCredits(credits);

            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
                CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
                mapper.updateCourse(course);
                sqlSession.commit();
                System.out.println("课程信息更新成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("更新课程信息失败: " + e.getMessage());
        }
    }

//    public static void updateCourseSelection(Scanner scan) {
//        try {
//            System.out.println("请输入学号: ");
//            int studentId = scan.nextInt();
//            System.out.println("请输入原课程号: ");
//            int oldCourseId = scan.nextInt();
//            System.out.println("请输入新课程号: ");
//            int newCourseId = scan.nextInt();
//
//            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//                GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
//                int rowsAffected = gradeMapper.updateGrade(studentId, oldCourseId, newCourseId);
//                sqlSession.commit();
//                if (rowsAffected > 0) {
//                    System.out.println("选课信息更新成功.");
//                } else {
//                    System.out.println("未找到匹配的选课记录，请检查输入的学号和课程号是否正确.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("更新选课信息失败: " + e.getMessage());
//        }
//    }
//
//    public static void addScore(Scanner scan) {
//        try {
//            System.out.println("请输入学生ID: ");
//            int studentId = scan.nextInt();
//            System.out.println("请输入课程ID: ");
//            int courseId = scan.nextInt();
//            System.out.println("请输入成绩: ");
//            double score = scan.nextDouble();
//
//            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//                GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
//                int rowsAffected = gradeMapper.addOrUpdateScore(studentId, courseId, score);
//                sqlSession.commit();
//                if (rowsAffected > 0) {
//                    System.out.println("学生成绩更新成功.");
//                } else {
//                    System.out.println("未找到匹配的选课记录，请检查输入的学号和课程号是否正确.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("添加成绩失败: " + e.getMessage());
//        }
//    }
//
//    public static void deleteStudentById(Scanner scan) {
//        try {
//            System.out.println("请输入要删除的学生ID: ");
//            int studentId = scan.nextInt();
//
//            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//                StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
//                CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
//                GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
//
//                int rowsAffected = gradeMapper.deleteGradesByStudentId(studentId);
//                rowsAffected += studentMapper.deleteStudentById(studentId);
//                sqlSession.commit();
//                if (rowsAffected > 0) {
//                    System.out.println("学生信息及相关成绩记录删除成功。");
//                } else {
//                    System.out.println("学生ID不存在.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("删除学生信息失败: " + e.getMessage());
//        }
//    }
//
//    public static void deleteCourseById(Scanner scan) {
//        try {
//            System.out.println("请输入要删除的课程ID: ");
//            int courseId = scan.nextInt();
//
//            try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//                GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
//                CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
//
//                int rowsAffected = gradeMapper.deleteGradesByCourseId(courseId);
//                rowsAffected += courseMapper.deleteCourseById(courseId);
//                sqlSession.commit();
//                if (rowsAffected > 0) {
//                    System.out.println("课程信息及相关成绩记录删除成功。");
//                } else {
//                    System.out.println("课程ID不存在.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("删除课程信息失败: " + e.getMessage());
//        }
//    }
//    public static void viewStudents() {
//        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
//            List<Student> students = mapper.selectAllStudents();
//            for (Student student : students) {
//                System.out.println("学号: " + student.getStudentId() +
//                        ", 姓名: " + student.getName() +
//                        ", 性别: " + student.getSex() +
//                        ", 出生日期: " + student.getBirthDate() +
//                        ", 政治面貌: " + student.getPolitical());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("查询学生信息失败: " + e.getMessage());
//        }
//    }
//
//    public static void viewCourses() {
//        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
//            List<Course> courses = mapper.selectAllCourses();
//            for (Course course : courses) {
//                System.out.println("课程ID: " + course.getCourseId() +
//                        ", 课程名称: " + course.getCourseName() +
//                        ", 学分: " + course.getCredits());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("查询课程信息失败: " + e.getMessage());
//        }
//    }
//
//    public static void viewGrades() {
//        try (SqlSession sqlSession = MyBatisUtils.getSqlSession()) {
//            GradeMapper mapper = sqlSession.getMapper(GradeMapper.class);
//            List<Grade> grades = mapper.selectAllGrades();
//            for (Grade grade : grades) {
//                System.out.println("学生ID: " + grade.getStudentId() +
//                        ", 课程ID: " + grade.getCourseId() +
//                        ", 成绩: " + grade.getScore());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("查询成绩信息失败: " + e.getMessage());
//        }
//    }
    // ... 其他方法的实现类似，都遵循相同的模式：
    // 1. 获取用户输入
    // 2. 创建实体对象
    // 3. 使用SqlSession获取对应的Mapper
    // 4. 调用Mapper方法执行操作
    // 5. 异常处理
}