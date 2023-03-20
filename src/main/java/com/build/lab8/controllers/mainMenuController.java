package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.Product;
import com.build.lab8.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class mainMenuController {
    public final ObservableList<Purchase> purchases = FXCollections.observableArrayList();
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab catalogTab;
    @FXML
    private Tab purchasesTab;
    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableColumn<Purchase,String> purchaseId;
    @FXML
    private TableColumn<Purchase,String> purchaseLogin;
    @FXML
    private TableView<Purchase.PurchaseProduct> productTable;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> productName;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> productSum;
    @FXML
    private TableColumn<Purchase.PurchaseProduct,String> productCount;

    @FXML
    private Label purchaseIdLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField searchField;



    @FXML
    private void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/catalog.fxml"));
        catalogTab.setContent(loader.load());
        catalogController controller = loader.getController();
        fillPurchases(purchases, controller);
        purchaseId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        purchaseLogin.setCellValueFactory(new PropertyValueFactory<>("purchaseLogin"));
        purchaseTable.setItems(purchases);

        showPurchaseDetails(null);
        purchaseTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPurchaseDetails(newValue));

    }
    private void fillPurchases(ObservableList<Purchase> purchases, catalogController controller) throws IOException {
        FileReader f = new FileReader("src/main/java/com/build/lab8/data/purchases.csv");
        BufferedReader buffer = new BufferedReader(f);
        String str;
        while((str = buffer.readLine()) != null ){
            String[] words = str.split(";");
            int id = Integer.parseInt(words[0]);
            String login = words[1];
            Date date = new Date(Long.parseLong(words[2]));
            ArrayList<Purchase.PurchaseProduct> products = new ArrayList<>();
            String[] prodBuff = words[3].split(",");
            for(int i = 0; i < prodBuff.length; i++){
                String[] prodObj = prodBuff[i].split(" ");
                products.add(i, new Purchase.PurchaseProduct(controller.productData.get(Integer.parseInt(prodObj[0]) - 1), Integer.parseInt(prodObj[1])));
            }
            purchases.add(new Purchase(id,login,date,products));

        }
        f.close();
    }
    private void showPurchaseDetails(Purchase purchase){
        if(purchase!=null){
            ObservableList<Purchase.PurchaseProduct> products = FXCollections.observableArrayList(purchase.getPurchaseProducts());
            purchaseIdLabel.setText(purchase.getPurchaseId().toString());
            productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            productSum.setCellValueFactory(new PropertyValueFactory<>("productSum"));
            productCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            productTable.setItems(products);

            Double sum = 0.0;
            for(Purchase.PurchaseProduct product : products){
                sum+=(product.getProductSum() * product.getCount());
            }
            priceLabel.setText(sum.toString());

        }
    }
    @FXML
    private void handleCatalogTab(){
        tabPane.getSelectionModel().select(catalogTab);
    }
    @FXML
    private void handlePurchasesTab(){
        tabPane.getSelectionModel().select(purchasesTab);
    }
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Программа Учебная.  версия 1.0.");
        alert.showAndWait();
    }
    @FXML
    private void handleWindowCatalog() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/catalog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage stage = new Stage();
        stage.setTitle("Каталог");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(null);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleStatistic() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/showDiagram.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage stage = new Stage();
        stage.setTitle("Статистика");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(null);
        Scene scene = new Scene(page);
        stage.setScene(scene);

        statisticController controller = loader.getController();
        controller.setProductData(purchases);

        stage.show();
    }
    @FXML
    private void handleSearch(){
        Purchase purchase = Purchase.search(purchases,Integer.parseInt(searchField.getText()));
        ObservableList<Purchase> query = FXCollections.observableArrayList();
        query.add(purchase);
        purchaseTable.setItems(query);
    }
    @FXML
    private void handleRefresh(){
        searchField.setText("");
        purchaseTable.setItems(purchases);
    }
}
