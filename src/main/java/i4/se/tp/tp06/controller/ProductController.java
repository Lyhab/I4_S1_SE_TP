package i4.se.tp.tp06.controller;

import java.util.List;
import java.util.Optional;

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

import i4.se.tp.tp06.entity.Product;
import i4.se.tp.tp06.entity.User;
import i4.se.tp.tp06.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/TP06")
public class ProductController {

    // injects an instance of the UserService, allowing the services to be used
    @Autowired
    private ProductService productService;

    @GetMapping("/Product_Home")
    public String Product_Management(HttpSession session) {
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

        return "ProductHome";
    }


    @GetMapping("/Product_List")
    public String User_List(Model model, HttpSession session) {
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
        List<Product> productList = productService.getAllProducts();

        // Add the product list to the Thymeleaf model
        model.addAttribute("productList", productList);


        return "ProductList";
    }

    @GetMapping("/Product_Insert")
    public String Product_Insert(HttpSession session, Model model) {
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
                return "redirect:/TP06/User_Home";
            }
        }

        // Prepare a new product object for the form
        model.addAttribute("product", new Product());

        return "ProductInsert";
    }

    @PostMapping("/Product_Insert")
    public String User_Insert(@ModelAttribute("product") Product newProduct, HttpServletRequest request, Model model) {
        String productName = request.getParameter("productname");
        String country = request.getParameter("country");
        Double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");


        // Set the username, password, and role, and then save the user to the database
        newProduct.setProductName(productName);
        newProduct.setCountry(country);
        newProduct.setPrice(price);
        newProduct.setDescription(description);

        // Save the user
        productService.save(newProduct);

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "Product has been successfully inserted!");

        // Return the view name
        return "ProductInsert";
    }

    @GetMapping("/Product_Update")
    public String Product_Update(HttpSession session, Model model) {
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

        // Fetch all users from the database
        List<Product> productList = productService.getAllProducts();

        // Add the product list to the Thymeleaf model
        model.addAttribute("productList", productList);

        return "ProductUpdate";
    }

    @PutMapping("/Product_Update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateProduct(@PathVariable(name = "id") Long productCode, @RequestBody Product updatedProduct) {
        // Check if the product with the given ID exists
        Optional<Product> existingProductOptional = productService.getProductById(productCode);
        if (!existingProductOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Update the product's information
        Product existingProduct = existingProductOptional.get();
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setCountry(updatedProduct.getCountry());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());

        // Save the updated product
        productService.save(existingProduct);

        return ResponseEntity.ok("Product updated successfully");
    }

    @GetMapping("/Product_Delete")
    public String Product_Delete(HttpSession session, Model model) {
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
        List<Product> productList = productService.getAllProducts();

        // Add the product list to the Thymeleaf model
        model.addAttribute("productList", productList);

        return "ProductDelete";
    }

    @DeleteMapping("/Product_Delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long productcode) {
        // Delete the product
        productService.deleteProductById(productcode);

        return ResponseEntity.ok("Product deleted successfully!");
    }
}
