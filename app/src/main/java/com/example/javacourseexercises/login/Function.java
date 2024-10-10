package com.example.javacourseexercises.login;

public class Function {
    String gametext;
    int gameImg;

    public Function(String gametext, int gameImg) {
        this.gametext = gametext;
        this.gameImg = gameImg;
    }


    public String getGametext() {
        return gametext;
    }

    public void setGametext(String gametext) {
        this.gametext = gametext;
    }

    public int getGameImg() {
        return gameImg;
    }

    public void setGameImg(int gameImg) {
        this.gameImg = gameImg;
    }
}
