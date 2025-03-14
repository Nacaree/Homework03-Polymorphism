
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {

    private final String name;
    private final int age;
    private final double grade;

    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", Grade: " + grade + ")";
    }

    public double getGrade() {
        return grade;
    }

}

// * To Sort Students by Grade in Descending Order (Based on grade)
class GradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getGrade(), s1.getGrade());
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        System.out.print("Enter Student Details, Type 'exit' as name to finish\n");
        System.out.println();

        while (true) {
            System.out.print("Enter Student Name (or 'exit' to stop): ");
            String name = input.nextLine();
            if (name.equals("exit")) {
                break;
            }
            System.out.print("Enter Age: ");
            int age = input.nextInt();

            System.out.print("Enter Grade: ");
            double grade = input.nextDouble();

            System.out.println();
            input.nextLine();
            students.add(new Student(name, age, grade));
            input.close();
        }

        Collections.sort(students, new GradeComparator());

        System.out.println("Students sorted by grade (Highest to Lowest):");
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
