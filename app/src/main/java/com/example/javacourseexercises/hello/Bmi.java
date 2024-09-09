package com.example.javacourseexercises.hello;

public class Bmi {
    public static void main(String[] args) {
        //如果有撰寫到其他建構子，空建構子就要自己寫。
        People hello = new People();
        People helloname = new People() ;
        //不管方法相不相同，都使用同樣的類別（型別），建構子（參數）與方法可以根據需求交互使用。
        People peopleA = new People("Ruby","A002" ,50, 1.6f) ;
        People peopleB = new People("selin","A005" ,40, 1.7f) ;
        People peopleC = new People("sean","A007" ,60, 1.8f) ;


        //使用空建構子＋方法的參數就要寫在這裡。
        hello.hello();
        helloname.helloname("Rubyyyyyer");
        peopleA.getBmi();
        peopleB.getBmi();
        peopleC.getBmi();
    }
}
