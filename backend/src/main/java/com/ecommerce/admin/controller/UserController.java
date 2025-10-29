package com.ecommerce.admin.controller;

import com.ecommerce.admin.dto.response.ApiResponse;
import com.ecommerce.admin.dto.response.UserResponse;
import com.ecommerce.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        List<UserResponse> users = userService.getAllUsers(search, status);
        return ApiResponse.success(users);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ApiResponse.success(user);
    }
}
