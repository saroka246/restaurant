package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class enterController {

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
    private Hyperlink registration;


    @FXML
    private void onCancelButtonClick(ActionEvent event) {
        login.clear();
        password.clear();
    }
    @FXML
    public void registrationForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("fxml/registrationForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onEnterButtonClick(ActionEvent event) throws IOException {
        if(HelloApplication.users.search(login.getText())){
            loginLabelError.setText("");
            for(User user : HelloApplication.users.getUsers()){
                if(user.compareLogin(login.getText())){
                    if(user.comparePassword(password.getText())){
                        if(login.getText().equals("admin")){
                            Parent root = FXMLLoader.load(HelloApplication.class.getResource("fxml/mainMenu.fxml"));
                            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setTitle("Главное меню");
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/shop.fxml"));
                            AnchorPane page = (AnchorPane) loader.load();
                            shopController controller = loader.getController();
                            controller.setLogin(login.getText());
                            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(page);
                            stage.setTitle("Ресторан");
                            stage.setScene(scene);
                            stage.show();

                        }

                    } else {
                        passwordLabelError.setText("Неверный пароль!");
                        break;
                    }
                }
            }
        } else {
            loginLabelError.setText("Пользователя с таким ником не существует!");
        }
    }

}