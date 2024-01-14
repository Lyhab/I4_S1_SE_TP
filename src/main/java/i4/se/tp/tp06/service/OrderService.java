package i4.se.tp.tp06.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import i4.se.tp.tp06.entity.Order;
import i4.se.tp.tp06.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;

    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    public void save(Order order) {
       orderRepo.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    } 

    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }
}
