package com.example.assignment2;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    /*"response_code": 0,
            "results": [
    {
        "category": "Science & Nature",
            "type": "multiple",
            "difficulty": "medium",
            "question": "What is the chemical formula for ammonia?",
            "correct_answer": "NH3",
            "incorrect_answers": [
        "CO2",
                "NO3",
                "CH4"
      ]
    }*/

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private ArrayList<String> incorrect_answers;
    private ArrayList<String> answers;

    public Quiz(){

    }

    //Constructor
    public Quiz(String category, String type, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answers, ArrayList<String> answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
        this.answers = answers;
    }

    //Getter and Setter
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getAnswers(){return answers;}
}
