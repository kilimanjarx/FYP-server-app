package com.example.escapadeserver3.Model;

public class Order {

    private long OrderId;
    private int OrderStatus;
    private String OrderDetail,Userphone;

    public Order() {
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }


    public String getUserphone() {
        return Userphone;
    }

    public void setUserphone(String userphone) {
        Userphone = userphone;
    }
}
