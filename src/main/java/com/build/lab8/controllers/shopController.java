package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.Product;
import com.build.lab8.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class shopController {
    public final ObservableList<Product> productData = FXCollections.observableArrayList();
    public final ObservableList<Purchase.PurchaseProduct> cartData = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> catalogTable;
    @FXML
    private TableColumn<Product,String> productName;
    @FXML
    private TableColumn<Product,String> productPrice;
    @FXML
    private TableView<Purchase.PurchaseProduct> cartTable;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> cartName;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> cartPrice;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> cartCount;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Label checkLabel;
    @FXML
    private Label userLogin;
    @FXML
    private TextField count;

    @FXML
    public void initialize() throws IOException {
        try{
            fillCatalog(productData);
        } catch (IOException ex){
            ex.printStackTrace();
        }

        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productSum"));
        catalogTable.setItems(productData);
        showProductDetails(null);

        catalogTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    private void showProductDetails(Product product){
        if(product!=null){
            productNameLabel.setText(product.getProductName());
            productPriceLabel.setText(product.getProductSum().toString());
            count.setText("1");
        }
    }

    private void fillCatalog(ObservableList<Product> productData) throws IOException {
        FileReader f = new FileReader("src/main/java/com/build/lab8/data/catalog.csv");
        BufferedReader buffer = new BufferedReader(f);
        String str;
        while((str = buffer.readLine()) != null ){
            String[] words = str.split(";");
            productData.add(new Product(Integer.parseInt(words[0]),words[1],Integer.parseInt(words[2]), Double.parseDouble(words[3])));
        }
        f.close();
    }
    @FXML
    private void refillPurchases() throws IOException {
        if(cartData.size() > 0){
            BufferedReader input = new BufferedReader(new FileReader("src/main/java/com/build/lab8/data/purchases.csv"));
            String last = null, line;
            while (null != (line = input.readLine())) {
                last = line;
            }
            input.close();
            String[] idBuff = last.split(";");
            int lastId = Integer.parseInt(idBuff[0]) + 1;
            Date currentDate = new Date();
            FileWriter f = new FileWriter("src/main/java/com/build/lab8/data/purchases.csv", true);
            f.append("\n"+lastId+";"+userLogin.getText()+";"+currentDate.getTime()+";");
            for(Purchase.PurchaseProduct product : cartData){
                f.append(product.getProductId()+" "+product.getCount()+",");
            }
            f.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(null);
            alert.setTitle("Успех!");
            alert.setHeaderText("Поздравляем!");
            alert.setContentText("Заказ сделан успешно!");
            alert.showAndWait();
            cartData.clear();
            cartTable.setItems(cartData);
            productNameLabel.setText("");
            productPriceLabel.setText("");
            count.setText("1");
            fillCheck();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Пустая корзина");
            alert.setHeaderText("Не выбран товар");
            alert.setContentText("Добавьте товар в корзину");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleMinus(){
        int countVal = Integer.parseInt(count.getText());
        if(countVal > 1){
            countVal--;
            count.setText(Integer.toString(countVal));
        }
    }
    @FXML
    private void handleAdd(){
        Product product = catalogTable.getSelectionModel().getSelectedItem();
        if(product != null){
            cartData.add(new Purchase.PurchaseProduct(product,Integer.parseInt(count.getText())));
        }
        cartName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cartPrice.setCellValueFactory(new PropertyValueFactory<>("productSum"));
        cartCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        cartTable.setItems(cartData);
        fillCheck();
    }
    @FXML
    private void handlePlus(){
        int countVal = Integer.parseInt(count.getText());
        if(countVal < 10){
            countVal++;
            count.setText(Integer.toString(countVal));
        }
    }
    @FXML
    private void handleDeleteProduct() throws IOException {
        int selectedIndex = cartTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            cartTable.getItems().remove(selectedIndex);
            fillCheck();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выделено");
            alert.setHeaderText("Не выбран товар");
            alert.setContentText("Выберите товар из таблицы");
            alert.showAndWait();
        }
    }

    public void fillCheck(){
        Double sum = 0.0;
        for(Purchase.PurchaseProduct productObj : cartData){
            sum+=(productObj.getProductSum() * productObj.getCount());
        }
        checkLabel.setText(sum+"Р");
    }
    public void setLogin(String login){
        userLogin.setText(login);
    }
}
