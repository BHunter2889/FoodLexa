package com.labelinsight.smartlabel.domain;

public class SmartLabelProduct {
    private Integer id;
    private String upc;
    private String brand;
    private String title;
    private String productSize;

    public SmartLabelProduct(Integer id, String upc, String brand, String title, String productSize) {
        this.id = id;
        this.upc = upc;
        this.brand = brand;
        this.title = title;
        this.productSize = productSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    @Override
    public String toString() {
        return "SmartLabelProduct{" +
                "id=" + id +
                ", upc='" + upc + '\'' +
                ", brand='" + brand + '\'' +
                ", title='" + title + '\'' +
                ", productSize='" + productSize + '\'' +
                '}';
    }
}
