package com.example.javacourseexercises.Student;

public class Score {
    public static void main(String[] args) {
        Student studentA = new Student("Ruby","0002",20,80);
        Student studentB = new Student("selin","0005",75,96);
        Student studentC = new Student("sean","0007",30,99);

        studentA.printScore();
        studentB.printScore();
        studentC.printScore();

    }
}
