package com.example.humanaid;

public class ServiceInfo {
    private String physioName;
    private String password;
    private String serviceName;
    private String description;
    private String price;

    public ServiceInfo(String physioName, String password, String serviceName, String description, String price) {
        this.physioName = physioName;
        this.password = password;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
    }


    public String getPhysioName() {
        return physioName;
    }

    public void setPhysioName(String physioName) {
        this.physioName = physioName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
