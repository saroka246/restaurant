package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class registrationController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label loginLabelError;
    @FXML
    private Label passwordLabelError;
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    @FXML
    private void onRegClick(ActionEvent event) throws IOException {
        if(HelloApplication.users.search(login.getText())){
            loginLabelError.setText("Пользователь с таким ником уже существует. Попробуйте другой.");

        } else {
            loginLabelError.setText("");
            if(password.getText().equals("")){
                passwordLabelError.setText("Введите пароль!");
            } else {
                HelloApplication.users.addUser(login.getText(),password.getText());
                FileWriter f = new FileWriter("src/main/java/com/build/lab8/data/db.txt");
                for(User user : HelloApplication.users.getUsers()){
                    f.append(user.getLogin()+" "+user.getPassword()+"\n");
                }
                f.close();
                login.clear();
                password.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Регистрация прошла успешно!");
                alert.setContentText("Можете перейти к форме входа!");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("fxml/start.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Hello!");
                    stage.setScene(scene);
                    stage.show();
                }
            }

        }

    }

    @FXML
    private void onCancelButtonClick(ActionEvent event) throws IOException {
        login.clear();
        password.clear();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("fxml/start.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}

