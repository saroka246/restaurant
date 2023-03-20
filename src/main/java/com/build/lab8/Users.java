package com.build.lab8;

import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

    public Users() throws IOException {
        FileReader f = new FileReader("src/main/java/com/build/lab8/data/db.txt");
        BufferedReader buffer = new BufferedReader(f);
        String str;
        while((str = buffer.readLine()) != null ){
            String[] words = str.split(" ");
            User user = new User(words[0],words[1]);
            users.add(user);
        }
        f.close();
    }

    public boolean search(String login){
        boolean flag = false;
        for(User user : users){
            if(login.equals(user.getLogin())){
                flag = true;
                break;
            }
        }
        if(flag){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser(String login, String password){
        User user = new User(login,password);
        users.add(user);
    }
}
