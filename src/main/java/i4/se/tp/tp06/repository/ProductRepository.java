package i4.se.tp.tp06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import i4.se.tp.tp06.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
