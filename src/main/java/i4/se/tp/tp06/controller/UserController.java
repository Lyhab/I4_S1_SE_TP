package i4.se.tp.tp06.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import i4.se.tp.tp06.entity.User;
import i4.se.tp.tp06.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/TP06")
public class UserController {

    // injects an instance of the UserService, allowing the services to be used
    @Autowired
    private UserService service;

    @GetMapping("/Log_In")
    public String Log_In() {
        return "LogIn";
    }
    @PostMapping("/Log_In")
    public String login(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        User authenticatedUser = service.authenticateUser(userName, password);

        if (authenticatedUser != null) {
            // Store the authenticated user in the session
            session.setAttribute("authenticatedUser", authenticatedUser);

            if ("admin".equals(authenticatedUser.getRole())) {
                return "redirect:/TP06/Admin_Home";
            } else if ("user".equals(authenticatedUser.getRole())) {
                return "redirect:/TP06/User_Home";
            }
        } else {
            // Add error message attribute to be displayed in the login page
            request.setAttribute("LogInError", "Invalid username or password!");
            return "LogIn";
        }
        return "redirect:/";
    }

    @GetMapping("/Register")
    public String Register() {
        return "Register";
    }

    @PostMapping("/Register")
    public String register(HttpServletRequest request, Model model) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");

        // Check if the username already exists in the database
        if (service.alreadyExists(userName)) {
            request.setAttribute("RegisterError", "Username already exists!");
            return "Register";
        }

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("RegisterError", "Passwords do not match!");
            return "Register";
        }

        // Save the user to the database or perform other registration logic
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setRole("user"); // You may set the role as needed

        service.save(newUser);

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "User has been successfully created!");

        // Redirect to the login page after successful registration
        return "Register";
    }

    //Admin

    @GetMapping("/Admin_Home")
    public String Admin_Home(HttpSession session) {
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

        return "AdminHome";
    }

    @GetMapping("/User_List")
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

        // Fetch all users from the database
        List<User> userList = service.getAllUsers();

        // Add the user list to the Thymeleaf model
        model.addAttribute("userList", userList);

        return "UserList";
    }

    @GetMapping("/User_Insert")
    public String User_Insert(HttpSession session, Model model) {
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

        // Prepare a new user object for the form
        model.addAttribute("user", new User());

        return "UserInsert";
    }

    @PostMapping("/User_Insert")
    public String User_Insert(@ModelAttribute("user") User newUser, HttpServletRequest request, Model model) {
        String userName = request.getParameter("username");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");

        // Check if the username already exists in the database
        if (service.alreadyExists(userName)) {
            model.addAttribute("RegisterError", "Username already exists!");
            return "UserInsert";
        }

        // Check if password and confirm password match
        if (!newUser.getPassword().equals(confirmPassword)) {
            model.addAttribute("RegisterError", "Passwords do not match!");
            return "UserInsert";
        }

        // Set the username, password, and role, and then save the user to the database
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setRole(role);

        // Save the user
        service.save(newUser);

        // Add the success message to the Thymeleaf model
        model.addAttribute("SuccessMessage", "User has been successfully created!");

        // Return the view name
        return "UserInsert";
    }

    @GetMapping("/User_Update")
    public String User_Update(HttpSession session, Model model) {
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
        List<User> userList = service.getAllUsers();

        // Add the user list to the Thymeleaf model
        model.addAttribute("userList", userList);

        return "UserUpdate";
    }

    @PutMapping("/User_Update/{id}")
    @ResponseBody
    public ResponseEntity<String> updateUser(@PathVariable(name = "id") Long id, @RequestBody User updatedUser) {
        // Check if the user with the given ID exists
        Optional<User> existingUserOptional = service.getUserById(id);
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Update the user's information
        User existingUser = existingUserOptional.get();
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        // Save the updated user
        service.save(existingUser);

        return ResponseEntity.ok("User updated successfully");
    }    


    @GetMapping("/User_Delete")
    public String User_Delete(HttpSession session, Model model) {
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
        List<User> userList = service.getAllUsers();

        // Add the user list to the Thymeleaf model
        model.addAttribute("userList", userList);

        return "UserDelete";
    }

    @DeleteMapping("/User_Delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        // Delete the user
        service.deleteUserById(id);

        return ResponseEntity.ok("User deleted successfully");
    }


    // User

    @GetMapping("/User_Home")
    public String User_Home(HttpSession session, Model model) {
        // Check if the user is authenticated
        User authenticatedUser = (User) session.getAttribute("authenticatedUser");
        if (authenticatedUser == null) {
            // Redirect to the login page if not authenticated
            return "redirect:/TP06/Log_In";
        }
    
        // Fetch the latest user data from the server
        User updatedUser = service.getUserById(authenticatedUser.getId()).orElse(null);

        // Update the session attribute with the latest user data
        session.setAttribute("authenticatedUser", updatedUser);

        // Pass the authenticated user to the Thymeleaf template
        model.addAttribute("user", updatedUser);
    
        return "UserHome";
    }    


    @PutMapping("/User_Edit/{id}")
    @ResponseBody
    public ResponseEntity<String> editUser(@PathVariable(name = "id") Long id, @RequestBody User updatedUser) {
        // Check if the user with the given ID exists
        Optional<User> existingUserOptional = service.getUserById(id);
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Update the user's information
        User existingUser = existingUserOptional.get();
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setPassword(updatedUser.getPassword());

        // Save the updated user
        service.save(existingUser);

        return ResponseEntity.ok("User updated successfully");
    }   
}
