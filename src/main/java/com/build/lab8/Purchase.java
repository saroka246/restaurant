package com.build.lab8;

import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Purchase {
    public Integer purchaseId;
    public String purchaseLogin;
    public Date purchaseDate;
    public ArrayList<PurchaseProduct> purchaseProducts;



    public Purchase(Integer purchaseId, String purchaseLogin, Date purchaseDate, ArrayList<PurchaseProduct> purchaseProducts) {
        this.purchaseId = purchaseId;
        this.purchaseLogin = purchaseLogin;
        this.purchaseDate = purchaseDate;
        this.purchaseProducts = purchaseProducts;
    }
    public Purchase(){

    }

    public static void output(ObservableList<Purchase> purchases){
        for(Purchase purchase : purchases){
            System.out.println(purchase.getPurchaseId()+" "+purchase.getPurchaseDate()+" "+purchase.getPurchaseLogin());
        }
    }
    public static Purchase search(ObservableList<Purchase> purchases, Integer id){
        boolean flag = false;
        Purchase result = new Purchase();
        for(Purchase purchase : purchases){
            if(purchase.getPurchaseId() == id){
                result = purchase;
                flag = true;

                break;
            }
        }
        if(!flag){
            return null;
        } else {
            return result;
        }

    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseLogin() {
        return purchaseLogin;
    }

    public void setPurchaseLogin(String purchaseLogin) {
        this.purchaseLogin = purchaseLogin;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public ArrayList<PurchaseProduct> getPurchaseProducts() {
        return this.purchaseProducts;
    }

    public void setPurchaseProducts(ArrayList<PurchaseProduct> purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }
    public static void refillPurchases(ObservableList<Purchase> purchases) throws IOException {
        FileWriter f = new FileWriter("src/main/java/com/build/lab8/data/purchases.csv");
        for(Purchase purchase : purchases){
            f.append(purchase.getPurchaseId()+";"+purchase.getPurchaseLogin()+";"+purchase.getPurchaseDate().getTime()+";");
            ArrayList<PurchaseProduct> products = purchase.getPurchaseProducts();
            for(PurchaseProduct product : products){
                f.append(product.getProductId()+" "+product.getCount()+",");
            }
            f.append("\n");
        }
        f.close();
    }

    public static class PurchaseProduct {
        public Product product;
        public Integer count;

        public PurchaseProduct(Product product, Integer count) {
            this.product = product;
            this.count = count;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
        public String getProductName() {
            return product.productName;
        }

        public Integer getProductId() {
            return product.productId;
        }


        public Double getProductSum() {
            return product.productSum;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
