package com.ecomapp.ecomapp.controller.usercontroller;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping
    public String showAdminPage() {

        return "user/main-page";
    }



    @GetMapping("/address/add")
    public String addAddress(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
        return "user/address-form";
    }

    @PostMapping("address/save")
    public String saveAddress(Model model, RedirectAttributes redirectAttributes,
                              @ModelAttribute Address address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));

        if (user.isPresent()) {
            User existingUser = user.get();
            address.setUser(existingUser);

            addressService.save(address);
            model.addAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Successfully added");
            return "redirect:/profile/details";
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/address/add";
        }
    }
}