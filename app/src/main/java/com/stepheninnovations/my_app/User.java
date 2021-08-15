package com.stepheninnovations.my_app;

public class User {


    private String username;
    private String email;
    private String id;
    private String url;

    public User() {
    }

    public User(String username, String email,String url) {
        this.username = username;
        this.email = email;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
