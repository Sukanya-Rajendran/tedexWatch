package com.ecomapp.ecomapp.controller;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.OtpService;
import com.ecomapp.ecomapp.service.OtpServiceImpl;
import com.ecomapp.ecomapp.service.ProductService.ProductService;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private OtpService otpService;
    private UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    ProductService productService;

    @Autowired
    OtpServiceImpl otpServiceimpl;
    @Autowired
    private UserRepository userRepo;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public MainController(UserService userService) {
        super();
        this.userService = userService;
    }


    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("login")
    public String login() {
        System.out.println("reached login");
        return "login";
    }
//    private String generateRandomReferralCode() {
//        // Generate a random alphanumeric referral code (customize as needed)
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuilder referralCode = new StringBuilder();
//        Random random = new Random();
//
//        for (int i = 0; i < 6; i++) {
//            referralCode.append(characters.charAt(random.nextInt(characters.length())));
//        }
//
//        return referralCode.toString();
//    }


//
//    @PostMapping("signup")
//    public String registerUserAccount(@ModelAttribute("user") UserDto userDto, HttpSession session) throws MessagingException, UnsupportedEncodingException {
//        int otp = otpService.generateRandomNumber();
//        System.out.println(otp);
//        System.out.println(userDto.getEmail());
//        session.setAttribute("email", userDto.getEmail());
//        session.setAttribute("user", userDto);
//        otpServiceimpl.saveOtp(userDto, otp);
//        otpService.sentOtpToEmail(userDto.getEmail(), otp);
//
//        return "redirect:/getOtpPage";
//    }
    //new
@PostMapping("signup")
public String registerUserAccount(@ModelAttribute("user") UserDto userDto, @RequestParam(value = "referralCode",required = false) String referralCode, HttpSession session) throws MessagingException, UnsupportedEncodingException {
    int otp = otpService.generateRandomNumber();
    session.setAttribute("email", userDto.getEmail());
    session.setAttribute("user", userDto);

    // Save the user's data into a User entity
    User user = new User();
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());

    // Check if a referral code is provided
    if (!referralCode.isEmpty()) {
       userService.addAmountToReferedUser(referralCode);

    }


    // Save the OTP and user's data in the service
    otpServiceimpl.saveOtp(userDto, otp);


    // Redirect to the referral code registration method
    return "redirect:/getOtpPage";
}


    @GetMapping("getOtpPage")
      public String getOtpPage() {
        return "/Otp/OtpPage";
      }


    @GetMapping
    public String redirectToLandingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        boolean isUser = authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

        if (isAdmin) return "redirect:/adminMainPage";

        return "index";


    }

    @GetMapping("/shopView")
    public String shopview(Model model) {
        List<Product> avaliableproducts = productService.findAll();
        model.addAttribute("avaliableproduct", avaliableproducts);
        System.out.println("is it  right ");
        return "user/shop";
    }

    @GetMapping("/shopView/{productId}")
    public String shopview(Model model, @PathVariable UUID productId) {
        Product product = productService.findById(productId).orElse(null);

        if (product != null) {
            model.addAttribute("products", product);

            return "user/product-detail"; // You should have a view for displaying a single product.
        } else {
            System.out.println("the product is not avaliable");
            // Handle the case where the product with the specified ID is not found.
            // You can add an error message or perform any  necessary action.
            return "/user/error"; // You should create an error view for this.
        }
    }

}
//
//@GetMapping("/cart")
//    public String cartView(){
//        return "user/cart";
//}
//
//
//}
