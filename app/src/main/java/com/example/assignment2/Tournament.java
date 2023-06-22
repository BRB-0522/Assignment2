package com.example.assignment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Tournament implements Serializable {
    private String Tname;
    private String category;
    private String difficulty;
    private ArrayList<Quiz> Quizs;
    private String startDate;
    private String endDate;
    private int like = 0;

    public Tournament(){

    }

    public Tournament(String tname, String category, String difficulty, ArrayList<Quiz> quizs, String startDate, String endDate, int like) {
        this.Tname = tname;
        this.category = category;
        this.difficulty = difficulty;
        this.Quizs = quizs;
        this.startDate = startDate;
        this.endDate = endDate;
        this.like = like;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Quiz> getQuizs() {
        return Quizs;
    }

    public void setQuizs(ArrayList<Quiz> quizs) {
        Quizs = quizs;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSLike() {
        return String.valueOf(like);
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void moreLike(){
        this.like=like+1;
    }

}
