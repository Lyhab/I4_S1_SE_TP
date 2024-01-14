package i4.se.tp.tp06.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import i4.se.tp.tp06.entity.Order;
import i4.se.tp.tp06.entity.OrderItem;
import i4.se.tp.tp06.entity.Product;
import i4.se.tp.tp06.entity.User;
import i4.se.tp.tp06.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/TP06")
public class OrderController {
    // injects an instance of the UserService, allowing the services to be used
    @Autowired
    private OrderService orderService;

    @GetMapping("/Order_Home")
    public String Order_Management(HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/TP06/Log_In";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/TP06/Log_In";
            }
        }

        return "OrderHome";
    }

    @GetMapping("/Order_List")
    public String Order_List(Model model, HttpSession session) {
        // Check if the user is authenticated
        if (session.getAttribute("authenticatedUser") == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/TP06/Log_In";
        }

        // Check the role of the authenticated user
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (!"admin".equals(authenticatedUser.getRole())) {
            // Redirect to the appropriate home page based on the user's role
            if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/TP06/Log_In";
            }
        }

        // Fetch all products from the database
        List<Order> orderList = orderService.getAllOrders();

        // Add the product list to the Thymeleaf model
        model.addAttribute("orderList", orderList);


        return "OrderList";
    }
}
