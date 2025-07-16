import java.io.*;

class Student {
    private int studentId;
    private String name;
    private int[] grades;
    private int[] coursesAssigned;

    public Student(int studentId, String name, int maxCourses) {
        this.studentId = studentId;
        this.name = name;
        this.grades = new int[maxCourses];
        this.coursesAssigned = new int[maxCourses];
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void assignCourse(int courseIndex) {
        coursesAssigned[courseIndex] = 1;
    }

    public void assignGrade(int courseIndex, int grade) {
        grades[courseIndex] = grade;
    }

    public int getGrade(int courseIndex) {
        return grades[courseIndex];
    }

    public boolean isCourseAssigned(int courseIndex) {
        return coursesAssigned[courseIndex] == 1;
    }

    public void displayInfo() {
        System.out.printf(" %-10d | %-20s%n", studentId, name);
    }

    public void displayCourses(Course[] courses) {
        System.out.println("Courses assigned to " + name + ":");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null && isCourseAssigned(i)) {
                System.out.printf(" %-10s | %-30s%n", courses[i].getCourseCode(), courses[i].getCourseName());
            }
        }
    }

    public void displayGrades(Course[] courses) {
        System.out.println("Grades for " + name + ":");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null && isCourseAssigned(i)) {
                System.out.printf(" %-30s | Grade: %-3d%n", courses[i].getCourseName(), grades[i]);
            }
        }
    }
}


class Course {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void displayInfo() {
        System.out.printf(" %-10s | %-30s%n", courseCode, courseName);
    }
}


class Professor {
    private int professorId;
    private String name;

    public Professor(int professorId, String name) {
        this.professorId = professorId;
        this.name = name;
    }

    public int getProfessorId() {
        return professorId;
    }

    public String getName() {
        return name;
    }

    public void displayInfo() {
        System.out.printf(" %-10d | %-20s%n", professorId, name);
    }
}


class CollegeManagementSystem {
    private static final int MAX_SIZE = 100;
    private static Student[] students = new Student[MAX_SIZE];
    private static Course[] courses = new Course[MAX_SIZE];
    private static Professor[] professors = new Professor[MAX_SIZE];
    private static int studentCount = 0, courseCount = 0, professorCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            displayHeader("*** College Management System ***");
            displayMenu();
            System.out.print("\033[1;36mEnter your choice: \033[0m"); 
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1 -> adminMode(reader);
                case 2 -> studentMode(reader);
                case 3 -> professorMode(reader);
                case 4 -> exitSystem();
                default -> System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m"); 
            }
        }
    }

    private static void displayHeader(String title) {
        System.out.println("\n=================================================");
        System.out.println("                 " + title + "                 ");
        System.out.println("=================================================\n");
    }

    private static void displayMenu() {
        System.out.println("1. Admin Mode");
        System.out.println("2. Student Mode");
        System.out.println("3. Professor Mode");
        System.out.println("4. Exit");
        System.out.println("-------------------------------------------------");
    }

    private static void adminMode(BufferedReader reader) throws IOException {
        displayHeader("Admin Mode");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Add Professor");
        System.out.println("4. Assign Student to Course");
        System.out.println("5. Display All Students");
        System.out.println("6. Display All Courses");
        System.out.println("7. Display All Professors");
        System.out.println("8. Back to Main Menu");
        System.out.print("\033[1;36mEnter your choice: \033[0m");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case 1 -> addStudent(reader);
            case 2 -> addCourse(reader);
            case 3 -> addProfessor(reader);
            case 4 -> assignStudentToCourse(reader);
            case 5 -> displayStudents();
            case 6 -> displayCourses();
            case 7 -> displayProfessors();
            case 8 -> {
                return;
            }
            default -> System.out.println("\033[1;31mInvalid choice! Try again.\033[0m");
        }
    }

    private static void studentMode(BufferedReader reader) throws IOException {
        displayHeader("Student Mode");
        System.out.print("Enter Student ID to Login: ");
        int studentId = Integer.parseInt(reader.readLine());
        Student student = findStudentById(studentId);

        if (student == null) {
            System.out.println("\033[1;31mStudent not found! Please try again.\033[0m");
            return;
        }

        System.out.println("Welcome, " + student.getName() + "!");
        System.out.println("1. View Details");
        System.out.println("2. View Courses");
        System.out.println("3. View Grades");
        System.out.println("4. Back to Main Menu");
        System.out.print("\033[1;36mEnter your choice: \033[0m");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case 1 -> student.displayInfo();
            case 2 -> student.displayCourses(courses);
            case 3 -> student.displayGrades(courses);
            case 4 -> {
                return;
            }
            default -> System.out.println("\033[1;31mInvalid choice! Try again.\033[0m");
        }
    }

    private static void professorMode(BufferedReader reader) throws IOException {
        displayHeader("Professor Mode");
        System.out.print("Enter Professor ID to Login: ");
        int professorId = Integer.parseInt(reader.readLine());
        Professor professor = findProfessorById(professorId);

        if (professor == null) {
            System.out.println("\033[1;31mProfessor not found! Please try again.\033[0m");
            return;
        }

        System.out.println("Welcome, Professor " + professor.getName() + "!");
        System.out.println("1. View Assigned Courses");
        System.out.println("2. Grade Students");
        System.out.println("3. Back to Main Menu");
        System.out.print("\033[1;36mEnter your choice: \033[0m");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case 1 -> displayCourses();
            case 2 -> gradeStudents(reader);
            case 3 -> {
                return;
            }
            default -> System.out.println("\033[1;31mInvalid choice! Try again.\033[0m");
        }
    }

    private static void addStudent(BufferedReader reader) throws IOException {
        System.out.println("\033[1;33mAdding a new student...\033[0m"); 
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(reader.readLine());
        System.out.print("Enter Student Name: ");
        String name = reader.readLine();
        students[studentCount++] = new Student(id, name, courseCount);
        System.out.println("\033[1;32mStudent added successfully!\033[0m"); 
    }

    private static void addCourse(BufferedReader reader) throws IOException {
        System.out.println("\033[1;33mAdding a new course...\033[0m"); 
        System.out.print("Enter Course Code: ");
        String code = reader.readLine();
        System.out.print("Enter Course Name: ");
        String name = reader.readLine();
        courses[courseCount++] = new Course(code, name);
        System.out.println("\033[1;32mCourse added successfully!\033[0m"); 
    }

    private static void addProfessor(BufferedReader reader) throws IOException {
        System.out.println("\033[1;33mAdding a new professor...\033[0m"); 
        System.out.print("Enter Professor ID: ");
        int id = Integer.parseInt(reader.readLine());
        System.out.print("Enter Professor Name: ");
        String name = reader.readLine();
        professors[professorCount++] = new Professor(id, name);
        System.out.println("\033[1;32mProfessor added successfully!\033[0m"); 
    }

    private static void assignStudentToCourse(BufferedReader reader) throws IOException {
        System.out.println("\033[1;33mAssigning a student to a course...\033[0m"); 
        System.out.print("Enter Student ID: ");
        int studentId = Integer.parseInt(reader.readLine());
        System.out.print("Enter Course Code: ");
        String courseCode = reader.readLine();

        Student student = findStudentById(studentId);
        int courseIndex = findCourseIndex(courseCode);

        if (student != null && courseIndex != -1) {
            student.assignCourse(courseIndex);
            System.out.println("\033[1;32mStudent assigned to course successfully!\033[0m"); 
        } else {
            System.out.println("\033[1;31mError: Student or Course not found.\033[0m"); 
        }
    }

    private static void gradeStudents(BufferedReader reader) throws IOException {
        System.out.println("\033[1;33mGrading students...\033[0m"); 
        System.out.print("Enter Student ID: ");
        int studentId = Integer.parseInt(reader.readLine());
        System.out.print("Enter Course Code: ");
        String courseCode = reader.readLine();
        System.out.print("Enter Grade: ");
        int grade = Integer.parseInt(reader.readLine());

        Student student = findStudentById(studentId);
        int courseIndex = findCourseIndex(courseCode);

        if (student != null && courseIndex != -1) {
            student.assignGrade(courseIndex, grade);
            System.out.println("\033[1;32mGrade assigned successfully!\033[0m"); 
        } else {
            System.out.println("\033[1;31mError: Student or Course not found.\033[0m"); 
        }
    }

    private static void displayStudents() {
        displayHeader("Student List");
        System.out.printf(" %-10s | %-20s%n", "Student ID", "Name");
        System.out.println("------------------------------------------");
        for (int i = 0; i < studentCount; i++) {
            students[i].displayInfo();
        }
    }

    private static void displayCourses() {
        displayHeader("Course List");
        System.out.printf(" %-10s | %-30s%n", "Course Code", "Course Name");
        System.out.println("----------------------------------------------");
        for (int i = 0; i < courseCount; i++) {
            courses[i].displayInfo();
        }
    }

    private static void displayProfessors() {
        displayHeader("Professor List");
        System.out.printf(" %-10s | %-20s%n", "Professor ID", "Name");
        System.out.println("------------------------------------------");
        for (int i = 0; i < professorCount; i++) {
            professors[i].displayInfo();
        }
    }

    private static Student findStudentById(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId() == id) {
                return students[i];
            }
        }
        return null;
    }

    private static int findCourseIndex(String code) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    private static Professor findProfessorById(int id) {
        for (int i = 0; i < professorCount; i++) {
            if (professors[i].getProfessorId() == id) {
                return professors[i];
            }
        }
        return null;
    }

    private static void exitSystem() {
        System.out.println("\033[1;35mThank you for using the College Management System. Goodbye!\033[0m");
        System.exit(0);
    }
}


