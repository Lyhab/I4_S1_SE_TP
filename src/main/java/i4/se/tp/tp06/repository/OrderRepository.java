package i4.se.tp.tp06.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import i4.se.tp.tp06.entity.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    
}
