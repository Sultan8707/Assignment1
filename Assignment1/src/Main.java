import models.School;
import models.Student;
import models.Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Locale.setDefault(Locale.US);

        School school = new School("Hogwarts");
        initializeStudents(school, "src/students.txt");
        initializeTeachers(school, "src/teachers.txt");

        System.out.println(school);

        displayStudentGPAs(school);
        updateAndDisplayTeacherSalaries(school, 10, 100);
    }

    private static void initializeStudents(School school, String filePath) throws FileNotFoundException {
        File studentFile = new File(filePath);
        Scanner scanner = new Scanner(studentFile);

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(" ");
            String firstName = data[0];
            String lastName = data[1];
            int age = Integer.parseInt(data[2]);
            String gender = data[3];

            List<Integer> grades = new ArrayList<>();
            for (int i = 4; i < data.length; i++) {
                grades.add(Integer.parseInt(data[i]));
            }

            Student student = new Student(firstName, lastName, age, gender, grades);
            school.addStudent(student);
        }
    }

    private static void initializeTeachers(School school, String filePath) throws FileNotFoundException {
        File teacherFile = new File(filePath);
        Scanner scanner = new Scanner(teacherFile);

        while (scanner.hasNext()) {
            Teacher teacher = new Teacher(
                    scanner.next(),
                    scanner.next(),
                    scanner.nextInt(),
                    scanner.next(),
                    scanner.next(),
                    scanner.nextInt(),
                    scanner.nextInt()
            );
            school.addTeacher(teacher);
        }
    }

    private static void displayStudentGPAs(School school) {
        for (Student student : school.getStudents()) {
            double gpa = student.calcGpa();
            System.out.printf("%s %s has a GPA of %.2f%n", student.getName(), student.getSurname(), gpa);
        }
        System.out.println();
    }

    private static void updateAndDisplayTeacherSalaries(School school, int experienceThreshold, double raisePercent) {
        for (Teacher teacher : school.getTeachers()) {
            teacher.giveRaise(experienceThreshold, raisePercent);
            String salaryMessage = teacher.isRaised(experienceThreshold)
                    ? String.format("'s salary has increased to %d", teacher.getSalary())
                    : String.format("'s salary remains the same at %d", teacher.getSalary());

            System.out.printf("%s %s%s%n", teacher.getName(), teacher.getSurname(), salaryMessage);
        }
    }
}