package com.example.assignment2;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private String type;
    private ArrayList<String> played;
    private ArrayList<String> liked;

    User(String email, String type){
        this.email = email;
        this.type = type;
    }

    User(){
        this.played = new ArrayList<>();
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

    public ArrayList<String> getLiked() {
        return liked;
    }

    public void setLiked(ArrayList<String> liked) {
        this.liked = liked;
    }

    public void setPlayed(ArrayList<String> played) {
        this.played = played;
    }
}


