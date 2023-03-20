package com.build.lab8;

import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;

public class Product {
    public String productName;
    public Integer productId, productCount;
    public Double productSum;

    public Product(Integer productId, String productName , Integer productCount, Double productSum) {
        this.productName = productName;
        this.productId = productId;
        this.productCount = productCount;
        this.productSum = productSum;
    }
    public Product(){

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Double getProductSum() {
        return productSum;
    }

    public void setProductSum(double productSum) {
        this.productSum = productSum;
    }

    public static void refillCatalog(ObservableList<Product> products) throws IOException {
        FileWriter f = new FileWriter("src/main/java/com/build/lab8/data/catalog.csv");
        for(Product product : products){
            f.append(product.getProductId()+";"+product.getProductName()+";"+ product.getProductCount()+";"+ product.getProductSum()+"\n");
        }
        f.close();
    }
}
