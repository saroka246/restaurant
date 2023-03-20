package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class catalogController {
    public final ObservableList<Product> productData = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> catalogTable;
    @FXML
    private TableColumn<Product,String> productId;
    @FXML
    private TableColumn<Product,String> productName;
    @FXML
    private TableColumn<Product,String> productSum;
    @FXML
    private TableColumn<Product,String> productCount;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productSumLabel;
    @FXML
    private Label productCountLabel;

    @FXML
    public void initialize(){
        try{
            fillCatalog(productData);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productSum.setCellValueFactory(new PropertyValueFactory<>("productSum"));
        productCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        catalogTable.setItems(productData);

        showProductDetails(null);

        catalogTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    private void showProductDetails(Product product){
        if(product!=null){
            productIdLabel.setText(product.getProductId().toString());
            productNameLabel.setText(product.getProductName());
            productSumLabel.setText(product.getProductSum().toString());
            productCountLabel.setText(product.getProductCount().toString());
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
    private void handleDeleteProduct() throws IOException {
        int selectedIndex = catalogTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            catalogTable.getItems().remove(selectedIndex);
            Product.refillCatalog(productData);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выделено");
            alert.setHeaderText("Не выбран товар");
            alert.setContentText("Выберите товар из таблицы");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleAddProduct() throws IOException {
        Product tempProduct = new Product();

        boolean okClicked = this.showProductEditDialog(tempProduct,"Добавление товара");
        if(okClicked){
            productData.add(tempProduct);
            Product.refillCatalog(productData);
        }
    }
    @FXML
    private void handleEditProduct() throws IOException {
        Product selectedProduct = catalogTable.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            boolean okClicked = showProductEditDialog(selectedProduct,"Изменение товара");
            if(okClicked){
                showProductDetails(selectedProduct);
                int selectedIndex = catalogTable.getSelectionModel().getSelectedIndex();
                productData.set(selectedIndex,selectedProduct);
                Product.refillCatalog(productData);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выделено");
            alert.setHeaderText("Не выбран товар");
            alert.setContentText("Выберите товар из таблицы");
            alert.showAndWait();
        }
    }
    public boolean showProductEditDialog(Product product, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/editProduct.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(null);
        Scene scene = new Scene(page);
        stage.setScene(scene);

        changeProductController controller = loader.getController();
        controller.setStage(stage);
        if(title.equals("Добавление товара")){
            controller.setProduct(product, true);
        } else {
            controller.setProduct(product, false);
        }


        stage.showAndWait();
        return controller.isOkClicked();
    }
}
