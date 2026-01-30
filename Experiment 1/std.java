class Student {

    int rollNo;
    String name;
    int marks;
    Student() {
        rollNo = 0;
        name = "Not Assigned";
        marks = 0;
    }
    Student(int r, String n, int m) {
        rollNo = r;
        name = n;
        marks = m;
    }
    void display() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Marks   : " + marks);
    }
    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student(101, "Aditya", 85);
        s2.display();
    }
}
