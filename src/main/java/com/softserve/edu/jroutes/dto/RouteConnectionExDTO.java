package com.softserve.edu.jroutes.dto;

import com.softserve.edu.jroutes.entity.Transport;

public class RouteConnectionExDTO {
    private Long id;
    private int departCountry;
    private int departCity;
    private int transferCountry;
    private int transferCity;
    private int arriveCountry;
    private int arriveCity;
    private int price;
    private int time;
    private boolean includeLayout;
    private String transport0;
    private String transport1;
    private String transport2;
    private String transport3;
    private String name;
    
    public boolean isIncludeLayout() {
        return includeLayout;
    }
    public void setIncludeLayout(boolean includeLayout) {
        this.includeLayout = includeLayout;
    }
    public int getTransferCountry() {
        return transferCountry;
    }
    public void setTransferCountry(int transferCountry) {
        this.transferCountry = transferCountry;
    }
    public int getTransferCity() {
        return transferCity;
    }
    public void setTransferCity(int transferCity) {
        this.transferCity = transferCity;
    }
    public String getTransport0() {
        return transport0;
    }
    public void setTransport0(String transport0) {
        this.transport0 = transport0;
    }
    public String getTransport1() {
        return transport1;
    }
    public void setTransport1(String transport1) {
        this.transport1 = transport1;
    }
    public String getTransport2() {
        return transport2;
    }
    public void setTransport2(String transport2) {
        this.transport2 = transport2;
    }
    public String getTransport3() {
        return transport3;
    }
    public void setTransport3(String transport3) {
        this.transport3 = transport3;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getDepartCountry() {
        return departCountry;
    }
    public void setDepartCountry(int departCountry) {
        this.departCountry = departCountry;
    }
    public int getDepartCity() {
        return departCity;
    }
    public void setDepartCity(int departCity) {
        this.departCity = departCity;
    }
    public int getArriveCountry() {
        return arriveCountry;
    }
    public void setArriveCountry(int arriveCountry) {
        this.arriveCountry = arriveCountry;
    }
    public int getArriveCity() {
        return arriveCity;
    }
    public void setArriveCity(int arriveCity) {
        this.arriveCity = arriveCity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
