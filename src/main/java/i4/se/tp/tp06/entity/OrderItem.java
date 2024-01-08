package i4.se.tp.tp06.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// marks User class as a JPA entity, indicating that instances of this class will be persisted to a relational database.
@Entity
public class OrderItem {

    @Id// marks the id field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// indicates that the primary key relies on auto-increment
    private Long orderItemID;
    
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode")
    private Product product; 

    private String unitPrice;
    private String quantity;
    public OrderItem(Long orderItemID, Order order, Product product, String unitPrice, String quantity) {
        this.orderItemID = orderItemID;
        this.order = order;
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    public Long getOrderItemID() {
        return orderItemID;
    }
    public void setOrderItemID(Long orderItemID) {
        this.orderItemID = orderItemID;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public String getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}