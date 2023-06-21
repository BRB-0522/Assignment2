package com.example.assignment2;

import java.util.ArrayList;

public class User {
    private String email;
    private String type;
    private ArrayList<String> played;

    User(String email, String type){
        this.email = email;
        this.type = type;
    }

    User(){

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getPlayed() {
        return played;
    }

    public void setPlayed(ArrayList<String> played) {
        this.played = played;
    }
}


