package i4.se.tp.tp06.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import i4.se.tp.tp06.entity.Product;
import i4.se.tp.tp06.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public void save(Product product) {
        productRepo.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    } 

    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }
}
