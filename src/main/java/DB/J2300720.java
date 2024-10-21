package DB;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class J2300720 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); 
        try {
            while (true) {
                System.out.println("=======菜单=======");
                System.out.println("1. 添加学生");
                System.out.println("2. 添加课程");
                System.out.println("3. 学生选课");
                System.out.println("4. 修改学生信息");
                System.out.println("5. 修改课程信息");
                System.out.println("6. 修改选课信息");
                System.out.println("7. 添加学生成绩");
                System.out.println("8. 删除学生信息");
                System.out.println("9. 删除课程信息");
                System.out.println("11. 查看数据库学生表");
                System.out.println("22. 查看数据库课程表");
                System.out.println("33. 查看数据库成绩表");
                System.out.println("0. 离开");
                System.out.println("================");
                System.out.print("请选择: ");
                
                int choice;
                while (true) {
                    if (scan.hasNextInt()) {
                        choice = scan.nextInt();
                        scan.nextLine(); 
                        break;
                    } else {
                        System.out.println("非法输入，请输入数字.");
                        scan.next(); // 清除非法输入
                    }
                }

                try {
                    switch (choice) {
                        case 1:
                            DatabaseOperations.addStudent(scan);
                            break;
                        case 2:
                            DatabaseOperations.addCourse(scan);
                            break;
                        case 3:
                            DatabaseOperations.enrollStudentInCourse(scan);
                            break;
                        case 4:
                            DatabaseOperations.updateStudent(scan);
                            break;
                        case 5:
                            DatabaseOperations.updateCourse(scan);
                            break;
                        case 6:
                            DatabaseOperations.updateCourseSelection(scan);
                            break;
                        case 7:
                            DatabaseOperations.addScore(scan);
                            break;
                        case 8:
                            DatabaseOperations.deleteStudentById(scan);
                            break;
                        case 9:
                            DatabaseOperations.deleteCourseById(scan);
                            break;                     
                        case 11:
                            DatabaseOperations.viewStudents();
                            break;
                        case 22:
                            DatabaseOperations.viewCourses();
                            break;
                        case 33:
                            DatabaseOperations.viewGrades();
                            break;
                        case 0:
                            System.out.println("退出程序...");
                            return;
                        default:
                            System.out.println("非法输入，请输入正确的选项.");
                    }
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                } catch (InputMismatchException e){
                	System.out.println("输入有误，请重新输入.");
                	scan.nextLine(); // 清除非法输入
                }
            }
        } finally {
            if (scan != null) {
                scan.close(); 
            }
        }
    }
}
