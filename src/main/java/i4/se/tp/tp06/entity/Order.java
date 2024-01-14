package i4.se.tp.tp06.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// marks User class as a JPA entity, indicating that instances of this class will be persisted to a relational database.
@Entity
@Table(name = "`Order`")
public class Order {

    @Id// marks the id field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// indicates that the primary key relies on auto-increment
    private Long orderID;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "specialInstruction")
    private String specialInstruction;
    
    public Order(Long orderID, List<OrderItem> orderItems, double totalPrice, Date orderDate, String specialInstruction) {
        this.orderID = orderID;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.specialInstruction = specialInstruction;
    }
    
    public void calculateTotalPrice() {
        if (orderItems != null && !orderItems.isEmpty()) {
            double total = 0.0;
            for (OrderItem orderItem : orderItems) {
                total += orderItem.getQuantity() * orderItem.getUnitPrice();
            }
            this.totalPrice = total;
        } else {
            this.totalPrice = 0.0;
        }
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public Order() {

    }
}