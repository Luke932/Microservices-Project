package luke932.order_service.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luke932.order_service.config.UserEventListener;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserEventListener userEventListener;

    @GetMapping
    public List<Map<String, Object>> getAllUsers() {
        return userEventListener.getUserList();
    }
}

