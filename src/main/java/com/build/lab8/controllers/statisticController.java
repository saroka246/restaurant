package com.build.lab8.controllers;

import com.build.lab8.HelloApplication;
import com.build.lab8.Product;
import com.build.lab8.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class statisticController implements Initializable {
    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList productNames = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/mainMenu.fxml"));
        FXMLLoader catalogLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/catalog.fxml"));
        try {
            loader.load();
            catalogLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainMenuController controller = loader.getController();
        catalogController catalogController = catalogLoader.getController();
        for(Product product : catalogController.productData){
            productNames.add(product.getProductName());
        }
        xAxis.setCategories(productNames);

    }
    public void setProductData(ObservableList<Purchase> purchases){
        ArrayList<Integer> productCounter = new ArrayList<Integer>();
        for(int i = 0; i < 7; i++){
            productCounter.add(0);
        }
        for(Purchase purchase: purchases){
            for(Purchase.PurchaseProduct product : purchase.getPurchaseProducts()){
                int count = productCounter.get(product.getProductId() - 1) + product.getCount();
                productCounter.set(product.getProductId() - 1, count);
            }
        }

        XYChart.Series series = new XYChart.Series<>();

        for(int i = 0; i < productCounter.size();i++){
            series.getData().add(new XYChart.Data<>(productNames.get(i),productCounter.get(i)));
        }
        barChart.getData().add(series);
    }
}
