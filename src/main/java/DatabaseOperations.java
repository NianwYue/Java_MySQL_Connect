import java.sql.*;
import java.util.Scanner;

public class DatabaseOperations {
	
	private static final String URL = "jdbc:mysql://localhost:3306/j2300720_yangjiashu?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addStudent(Scanner scan) throws SQLException {
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

            String sql = "INSERT INTO Students (student_id, name, sex, birth_date, political) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setString(2, name);
                pstmt.setString(3, gender);
                pstmt.setDate(4, Date.valueOf(birthDate));
                pstmt.setString(5, politicalStatus);
                pstmt.executeUpdate();
                System.out.println("学生添加成功.");
            }
        } finally {
            // No need to close the Scanner here
        }
    }

    public static void addCourse(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入课程号: ");
            int courseId = scan.nextInt();
            scan.nextLine();
            System.out.println("请输入课程名: ");
            String courseName = scan.nextLine();
            System.out.println("请输入课程的学分: ");
            int credits = scan.nextInt();

            String sql = "INSERT INTO Courses (course_id, course_name, credits) VALUES (?, ?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, courseId);
                pstmt.setString(2, courseName);
                pstmt.setInt(3, credits);
                pstmt.executeUpdate();
                System.out.println("课程添加成功.");
            }
        } finally {
            // No need to close the Scanner here
        }
    }

    public static void enrollStudentInCourse(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入学生ID: ");
            int studentId = scan.nextInt();
            System.out.println("请输入课程ID: ");
            int courseId = scan.nextInt();

            // 检查学生是否存在
            String checkStudentSql = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
            // 检查课程是否存在
            String checkCourseSql = "SELECT COUNT(*) FROM Courses WHERE course_id = ?";

            try (
            	Connection conn = connect();
                PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentSql);
                PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseSql)
                ) {

                checkStudentStmt.setInt(1, studentId);
                checkCourseStmt.setInt(1, courseId);

                ResultSet studentResult = checkStudentStmt.executeQuery();
                studentResult.next();
                int studentCount = studentResult.getInt(1);

                ResultSet courseResult = checkCourseStmt.executeQuery();
                courseResult.next();
                int courseCount = courseResult.getInt(1);

                if (studentCount == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                if (courseCount == 0) {
                    System.out.println("课程ID不存在.");
                    return;
                }

                // 如果学生和课程都存在，则进行插入操作
                String enrollSql = "INSERT INTO Grades (student_id, course_id) VALUES (?, ?)";
                try (PreparedStatement enrollStmt = conn.prepareStatement(enrollSql)) {
                    enrollStmt.setInt(1, studentId);
                    enrollStmt.setInt(2, courseId);
                    enrollStmt.executeUpdate();
                    System.out.println("学生选课成功.");
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }
    

    public static void updateStudent(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入要更新的学生的学号: ");
            int studentId = scan.nextInt();
            scan.nextLine();

            // 检查学生是否存在
            String checkStudentSql = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
            try (Connection conn = connect();
                 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentSql)) {
                checkStudentStmt.setInt(1, studentId);
                ResultSet studentResult = checkStudentStmt.executeQuery();
                studentResult.next();
                int studentCount = studentResult.getInt(1);

                if (studentCount == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                System.out.println("请输入新的名字: ");
                String name = scan.nextLine();
                System.out.println("请输入新的性别: (男/女): ");
                String gender = scan.nextLine();
                System.out.println("请输入新的出生日期: (年-月-日): ");
                String birthDate = scan.nextLine();
                System.out.println("请输入新的政治面貌: ");
                String politicalStatus = scan.nextLine();

                String sql = "UPDATE Students SET name = ?, sex = ?, birth_date = ?, political = ? WHERE student_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, gender);
                    pstmt.setDate(3, Date.valueOf(birthDate));
                    pstmt.setString(4, politicalStatus);
                    pstmt.setInt(5, studentId);
                    pstmt.executeUpdate();
                    System.out.println("学生信息更新成功.");
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }


    public static void updateCourse(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入要更新的课程的课程号: ");
            int courseId = scan.nextInt();
            scan.nextLine();

            // 检查课程是否存在
            String checkCourseSql = "SELECT COUNT(*) FROM Courses WHERE course_id = ?";
            try (Connection conn = connect();
                 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseSql)) {
                checkCourseStmt.setInt(1, courseId);
                ResultSet courseResult = checkCourseStmt.executeQuery();
                courseResult.next();
                int courseCount = courseResult.getInt(1);

                if (courseCount == 0) {
                    System.out.println("课程ID不存在.");
                    return;
                }

                System.out.println("请输入新的课程名: ");
                String courseName = scan.nextLine();
                System.out.println("请输入新的课程的学分: ");
                int credits = scan.nextInt();

                String sql = "UPDATE Courses SET course_name = ?, credits = ? WHERE course_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, courseName);
                    pstmt.setInt(2, credits);
                    pstmt.setInt(3, courseId);
                    pstmt.executeUpdate();
                    System.out.println("课程信息更新成功.");
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }


    public static void updateCourseSelection(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入学号: ");
            int studentId = scan.nextInt();
            System.out.println("请输入原课程号: ");
            int oldCourseId = scan.nextInt();
            System.out.println("请输入新课程号: ");
            int newCourseId = scan.nextInt();

            // 检查学生和课程是否存在
            String checkStudentSql = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
            String checkCourseSql = "SELECT COUNT(*) FROM Courses WHERE course_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentSql);
                 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseSql)) {

                checkStudentStmt.setInt(1, studentId);
                ResultSet studentResult = checkStudentStmt.executeQuery();
                studentResult.next();
                int studentCount = studentResult.getInt(1);

                checkCourseStmt.setInt(1, oldCourseId);
                ResultSet courseResult1 = checkCourseStmt.executeQuery();
                courseResult1.next();
                int oldCourseCount = courseResult1.getInt(1);

                checkCourseStmt.setInt(1, newCourseId);
                ResultSet courseResult2 = checkCourseStmt.executeQuery();
                courseResult2.next();
                int newCourseCount = courseResult2.getInt(1);

                if (studentCount == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                if (oldCourseCount == 0) {
                    System.out.println("原课程ID不存在.");
                    return;
                }

                if (newCourseCount == 0) {
                    System.out.println("新课程ID不存在.");
                    return;
                }

                String sql = "UPDATE Grades SET course_id = ? WHERE student_id = ? AND course_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, newCourseId);
                    pstmt.setInt(2, studentId);
                    pstmt.setInt(3, oldCourseId);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("选课信息更新成功.");
                    } else {
                        System.out.println("未找到匹配的选课记录，请检查输入的学号和课程号是否正确.");
                    }
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }

    
    public static void addScore(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入学生ID: ");
            int studentId = scan.nextInt();
            System.out.println("请输入课程ID: ");
            int courseId = scan.nextInt();
            System.out.println("请输入成绩: ");
            double score = scan.nextDouble();

            // 检查学生和课程是否存在
            String checkStudentSql = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
            String checkCourseSql = "SELECT COUNT(*) FROM Courses WHERE course_id = ?";
            
            try (Connection conn = connect();
                 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentSql);
                 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseSql)) {

                checkStudentStmt.setInt(1, studentId);
                ResultSet studentResult = checkStudentStmt.executeQuery();
                studentResult.next();
                int studentCount = studentResult.getInt(1);

                checkCourseStmt.setInt(1, courseId);
                ResultSet courseResult = checkCourseStmt.executeQuery();
                courseResult.next();
                int courseCount = courseResult.getInt(1);

                if (studentCount == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                if (courseCount == 0) {
                    System.out.println("课程ID不存在.");
                    return;
                }

                String sql = "UPDATE Grades SET grade = ? WHERE student_id = ? AND course_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setDouble(1, score);  // 将成绩更新为给定的 score
                    pstmt.setInt(2, studentId);
                    pstmt.setInt(3, courseId);
                    int rowsAffected = pstmt.executeUpdate();  // 执行更新操作，并返回受影响的行数
                    
                    if (rowsAffected > 0) {
                        System.out.println("学生成绩更新成功.");
                    } else {
                        System.out.println("未找到匹配的选课记录，请检查输入的学号和课程号是否正确.");
                    }
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }


   
    public static void deleteStudentById(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入要删除的学生ID: ");
            int studentId = scan.nextInt();

            // 检查学生是否存在
            String checkStudentSql = "SELECT COUNT(*) FROM Students WHERE student_id = ?";
            try (Connection conn = connect();
                 PreparedStatement checkStudentStmt = conn.prepareStatement(checkStudentSql)) {
                checkStudentStmt.setInt(1, studentId);
                ResultSet studentResult = checkStudentStmt.executeQuery();
                studentResult.next();
                int studentCount = studentResult.getInt(1);

                if (studentCount == 0) {
                    System.out.println("学生ID不存在.");
                    return;
                }

                String deleteStudentSql = "DELETE FROM Students WHERE student_id = ?";
                String deleteGradesSql = "DELETE FROM Grades WHERE student_id = ?";
                
                try (PreparedStatement pstmt1 = conn.prepareStatement(deleteStudentSql);
                     PreparedStatement pstmt2 = conn.prepareStatement(deleteGradesSql)) {
                    
                    pstmt1.setInt(1, studentId);
                    pstmt1.executeUpdate();
                    
                    pstmt2.setInt(1, studentId);
                    pstmt2.executeUpdate();
                    
                    System.out.println("学生信息及相关成绩记录删除成功。");
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }


    public static void deleteCourseById(Scanner scan) throws SQLException {
        try {
            System.out.println("请输入要删除的课程ID: ");
            int courseId = scan.nextInt();

            // 检查课程是否存在
            String checkCourseSql = "SELECT COUNT(*) FROM Courses WHERE course_id = ?";
            try (Connection conn = connect();
                 PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseSql)) {
                checkCourseStmt.setInt(1, courseId);
                ResultSet courseResult = checkCourseStmt.executeQuery();
                courseResult.next();
                int courseCount = courseResult.getInt(1);

                if (courseCount == 0) {
                    System.out.println("课程ID不存在.");
                    return;
                }

                String deleteCourseSql = "DELETE FROM Courses WHERE course_id = ?";
                String deleteGradesSql = "DELETE FROM Grades WHERE course_id = ?";
                
                try (PreparedStatement pstmt1 = conn.prepareStatement(deleteCourseSql);
                     PreparedStatement pstmt2 = conn.prepareStatement(deleteGradesSql)) {
                    
                    pstmt1.setInt(1, courseId);
                    pstmt1.executeUpdate();
                    
                    pstmt2.setInt(1, courseId);
                    pstmt2.executeUpdate();
                    
                    System.out.println("课程信息及相关成绩记录删除成功。");
                }
            }
        } finally {
            // No need to close the Scanner here
        }
    }

    public static void viewStudents() throws SQLException {
        String sql = "SELECT * FROM Students";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("学生信息:");
            while (rs.next()) {
                System.out.println("学号: " + rs.getInt("student_id") + 
                                   ", 姓名: " + rs.getString("name") + 
                                   ", 性别: " + rs.getString("sex") + 
                                   ", 出生日期: " + rs.getDate("birth_date") + 
                                   ", 政治面貌: " + rs.getString("political"));
            }
        }
    }

    public static void viewCourses() throws SQLException {
        String sql = "SELECT * FROM Courses";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("课程信息:");
            while (rs.next()) {
                System.out.println("课程ID: " + rs.getInt("course_id") + 
                                   ", 课程名称: " + rs.getString("course_name") + 
                                   ", 学分: " + rs.getInt("credits"));
            }
        }
    }

    public static void viewGrades() throws SQLException {
        String sql = "SELECT * FROM Grades";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("成绩信息:");
            while (rs.next()) {
                System.out.println("学生ID: " + rs.getInt("student_id") + 
                                   ", 课程ID: " + rs.getInt("course_id") + 
                                   ", 成绩: " + rs.getInt("score"));
            }
        }
    }
}
