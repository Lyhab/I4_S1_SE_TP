package i4.se.tp.tp06.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// marks User class as a JPA entity, indicating that instances of this class will be persisted to a relational database.
@Entity
public class Order {

    @Id// marks the id field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// indicates that the primary key relies on auto-increment
    private Long orderID;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private Long orderNumber;
    private String totalPrice;
    private String orderDate;
    private String specialInstruction;
    public Order(Long orderID, List<OrderItem> orderItems, Long orderNumber, String totalPrice, String orderDate,
            String specialInstruction) {
        this.orderID = orderID;
        this.orderItems = orderItems;
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.specialInstruction = specialInstruction;
    }
    public Long getOrderID() {
        return orderID;
    }
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public Long getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getSpecialInstruction() {
        return specialInstruction;
    }
    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }
}