package cn.edu.sdcet.new_db;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("=======菜单（MyBatis版本）=======");
                System.out.println("1. 添加学生");
                System.out.println("2. 添加课程");
                System.out.println("3. 学生选课");
                System.out.println("4. 修改学生信息");
                System.out.println("5. 修改课程信息");
                System.out.println("6. 修改选课信息");
                System.out.println("7. 添加学生成绩");
                System.out.println("8. 删除学生信息");
                System.out.println("9. 删除课程信息");
                System.out.println("10. 查看数据库学生表");
                System.out.println("11. 查看数据库课程表");
                System.out.println("12. 查看数据库成绩表");
                System.out.println("0. 离开");
                System.out.println("===============================");
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
                            Database.addStudent(scan);
                            break;
                        case 2:
                            Database.addCourse(scan);
                            break;
                        case 3:
                            Database.enrollStudentInCourse(scan);
                            break;
                        case 4:
                            Database.updateStudent(scan);
                            break;
                        case 5:
                            Database.updateCourse(scan);
                            break;
//                        case 6:
//                            Database.updateCourseSelection(scan);
//                            break;
//                        case 7:
//                            Database.addScore(scan);
//                            break;
//                        case 8:
//                            Database.deleteStudentById(scan);
//                            break;
//                        case 9:
//                            Database.deleteCourseById(scan);
//                            break;
//                        case 10:
//                            Database.viewStudents();
//                            break;
//                        case 11:
//                            Database.viewCourses();
//                            break;
//                        case 12:
//                            Database.viewGrades();
//                            break;
                        case 0:
                            System.out.println("退出程序...");
                            return;
                        default:
                            System.out.println("非法输入，请输入正确的选项.");
                    }
                } catch (InputMismatchException e){
                    System.out.println("输入有误，请重新输入.");
                    scan.nextLine(); // 清除非法输入
                }
            }
        } finally {
            scan.close();
        }
    }
}
