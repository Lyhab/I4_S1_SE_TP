package i4.se.tp.tp06.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// marks User class as a JPA entity, indicating that instances of this class will be persisted to a relational database.
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productCode")  
    private Long productCode;

    @Column(name = "productName")
    private String productName;

    @Column(name = "country")  
    private String country;

    @Column(name = "price")  
    private Double price;

    @Column(name = "description")  
    private String description;

    public Product(Long productCode, String productName, String country, Double price, String description) {
        this.productCode = productCode;
        this.productName = productName;
        this.country = country;
        this.price = price;
        this.description = description;
    }
    public Long getProductCode() {
        return productCode;
    }
    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Product() {
    }    
}