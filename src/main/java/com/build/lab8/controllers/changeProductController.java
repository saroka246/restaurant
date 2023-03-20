package com.build.lab8.controllers;

import com.build.lab8.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class changeProductController {

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productSumField;
    @FXML
    private TextField productCountField;

    private Stage stage;
    private Product product;
    private boolean okClicked = false;


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setProduct(Product product, boolean flag) {
        this.product = product;
        if(!flag){
            productIdField.setText(product.getProductId().toString());
            productNameField.setText(product.getProductName());
            productSumField.setText(product.getProductSum().toString());
            productCountField.setText(product.getProductCount().toString());
        }
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if(productIdField.getText() == null || productIdField.getText().length() == 0){
            errorMessage += "Нет доступного артикула\n";
        }
        if(productNameField.getText() == null || productNameField.getText().length() == 0){
            errorMessage += "Нет доступного наименования товара\n";
        }
        if(productSumField.getText() == null || productSumField.getText().length() == 0){
            errorMessage += "Нет доступной суммы\n";
        }
        if(productCountField.getText() == null || productCountField.getText().length() == 0){
            errorMessage += "Нет доступного кол-ва\n";
        }
        if(errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Внесите корректную информацию");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    @FXML
    private void handleOk(){
        if(isInputValid()){
            product.setProductId(Integer.parseInt(productIdField.getText()));
            product.setProductName(productNameField.getText());
            product.setProductSum(Double.parseDouble(productSumField.getText()));
            product.setProductCount(Integer.parseInt(productCountField.getText()));

            okClicked = true;
            stage.close();
        }
    }
    @FXML
    private void handleCancel(){
        stage.close();
    }
}
