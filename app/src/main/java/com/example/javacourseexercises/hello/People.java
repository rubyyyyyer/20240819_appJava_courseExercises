package com.example.javacourseexercises.hello;

public class People {
    //屬性
    String name;
    String id;
    float weight = 0;
    float height = 0;

    //空建構子
    public People() {
        super();
    }
    //有帶參數的建構子
    public People(String name, String id, float weight, float height) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.height = height;
    }
    //Getter & Setter，才譨讓參數跟建構子合再一起寫。Start---------
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    //方法Start---------------------------------------------------
    public void hello() {
        System.out.println("Hi, Java~");
    }

    public void helloname(String name) {
        System.out.println("Hello, " + name);
    }

    public float bmi() {
        float bmi = weight / (height * height);
        return bmi;
    }


    public void getBmi() {
        System.out.println(name + "\t" + id + "\t" + weight + "\t" + height + "\t" + bmi());
    }
}
