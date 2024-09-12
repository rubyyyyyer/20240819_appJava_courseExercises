package com.example.javacourseexercises.Student;

public class Student {
    static int pass = 60;
    String name;
    String id;
    int math;
    int english;
    int average;


    public Student(String name, String id, int math, int english) {
        this.name = name;
        this.id = id;
        this.math = math;
        this.english = english;
    }

    public int getAverage() {
        return (math + english) / 2;
    }

//    // 只能判斷尾數平均的不及格成績
//    public void printScore() {
//        System.out.print(name + "\t" + id + "\t" + math + "\t" + "\t" + english + "\t" + "\t" + getAverage());
//        if (getAverage() >= pass) {
//            System.out.println();
//        } else {
//            System.out.println("*");
//        }
//    }
/*        // 三元運算式，判別各科結果。
        public void printScore () {
            System.out.println(name + "\t" + id + "\t" + math + "\t" + (math >= pass ? " " : "*") + "\t" + english + "\t" + (english >= pass ? " " : "*") + getAverage() + "\t" + (getAverage() >= pass ? " " : "*"));
        }*/

        //String.format，判別各科結果。
//        public void printScore () {
//            System.out.printf("%s\t%s\t%d%s\t%d%s\t%d%s%n", name, id, math, math <= pass ? "*" : " ", english, english <= pass ? "*" : " ",
//                    getAverage(), getAverage() < pass ? "*" : "");
//        }

/*        //String.format，判別各科結果。
        public void printScore () {
            System.out.printf("%s\t%s\t%d\t%s\t%d\t%s\t%d\t%s%n",
                    name, id, math, math >= pass ? "*" : "",
                    english, english >= pass ? "*" : "",
                    getAverage(), getAverage() >= pass ? "*" : "");
        }*/

        //自定義一個方法
        public void printScore () {
            System.out.println(name + "\t" + id + "\t" + math + getAsterisk(math) + "\t" + english + getAsterisk(english) + "\t" + getAverage() + getAsterisk(getAverage()));
        }
        private String getAsterisk ( int score){
            return score >= pass ? " " : "*";
        }
}
