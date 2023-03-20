package com.build.lab8;

public class User {
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean compareLogin(String login){
        if(login.equals(this.login)){
            return true;
        } else {
            return false;
        }
    }
    public boolean comparePassword(String password){
        if(password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }


}

