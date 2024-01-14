package i4.se.tp.tp06.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemID;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode")
    private Product product;

    @Column(name = "productName")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order order;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unitPrice")
    private Double unitPrice;    

    public OrderItem(Long orderItemID, Product product, Order order, int quantity, String productName, Double unitPrice) {
        this.orderItemID = orderItemID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.productName = product.getProductName();
        this.unitPrice = product.getPrice();
    }
 
    public Long getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(Long orderItemID) {
        this.orderItemID = orderItemID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderItem() {
    }
}
