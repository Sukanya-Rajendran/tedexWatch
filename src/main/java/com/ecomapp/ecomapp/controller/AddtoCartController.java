package com.ecomapp.ecomapp.controller;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository;
import com.ecomapp.ecomapp.repository.ProductRepo.ProductRepository;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.CartService.CartService;
import com.ecomapp.ecomapp.service.CouponService.CouponService;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class AddtoCartController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;
    @Autowired
    private com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository CouponRepository;


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    @PostMapping("/addProductInCart")
    public String addCart(@RequestParam("productId") UUID productId, Principal principal) {
        System.out.println(principal.getName() + "----------------------------------");
        System.out.println(productId + "====================");
        String email = principal.getName();
        Optional<Product> ExistingProduct = productRepository.findById(productId);
        if (ExistingProduct.isPresent()) {
            cartService.addToCartItem(email, productId);
        } else {
            System.out.println("product is not avaliable ");
        }
        System.out.println(productId);
        return "redirect:/shopView";
    }


    @GetMapping("/showProductInCart")
    public String showcart(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByUsername(email);

            List<Cart> cart = cartRepository.findByUser_id(user.getId());
            System.out.println(cart + "================cart item");
//            User user = userService.findByUsername(email); // Replace with your user retrieval method
            System.out.println(user + "=======this is user");

            if (user != null) {
//                Cart userCart = user.getCart(); // Retrieve the user's cart
                System.out.println(cart + "===============this is userCart");

                if (cart != null) {
                    model.addAttribute("avaliablecartitem", cart);
                    model.addAttribute("total", cartService.getTotalPrice(email));

                    List<Coupon> availableCoupons = CouponRepository.findAll();
                    model.addAttribute("availableCoupons", availableCoupons);

                    System.out.println(email);
                    System.out.println("User's cart is displayed.");
                } else {
                    System.out.println("User has no cart.");
                    // Handle the case where the user has no cart
                }
            } else {
                System.out.println("User not found.");
                // Handle the case where the user is not found
            }
        } else {
            System.out.println("User not logged in.");
            // Handle the case where the user is not logged in
        }

        return "/user/shopCartOne";
    }


// this is the method

//    @GetMapping("/showProductInCart")
//     public String showcart(Model model,Principal prinicipal){
//        String email=prinicipal.getName();
//        List<Cart>avaliablecartitem=cartRepository.findAll();
//         System.out.println(avaliablecartitem.get(0).getProduct().getImages().get(0)+"---------------");
//        model.addAttribute("avaliablecartitem", avaliablecartitem);
//        model.addAttribute("total",cartService.getTotalPrice(prinicipal.getName()));
//         List<Coupon> availableCoupons = CouponRepository.findAll();
//
//
//         model.addAttribute("availableCoupons", availableCoupons);
//         System.out.println(availableCoupons);
//
//         System.out.println(prinicipal.getName());
//
//         System.out.println("is it  right ");
//         return "/user/shopCartOne";
//     }


    //---------------
//     @GetMapping("/showProductInCart")
//     public String showcart(Model model, Principal principal) {
//         String email = principal.getName();
//         List<Cart> availableCartItems = cartRepository.findAll();
//
//         if (!availableCartItems.isEmpty()) {
//             // Check if the first cart item has a product and images
//             Cart firstCartItem = availableCartItems.get(0);
//             if (firstCartItem.getProduct() != null && !firstCartItem.getProduct().getImages().isEmpty()) {
//                 System.out.println(firstCartItem.getProduct().getImages().get(0) + "---------------");
//             }
//         }
//
//         model.addAttribute("availablecartitem", availableCartItems);
//         model.addAttribute("total", cartService.getTotalPrice(email));
//         System.out.println(email);
//         System.out.println("is it right");
//
//         return "user/shopCartOne"; // Make sure this is the correct view name
//     }

    @GetMapping("/addQuantity")
    public String addProductQuantity(@RequestParam(name = "cartId") UUID cartId,
                                     @RequestParam(name = "quantity") int quantity,
                                     @AuthenticationPrincipal(expression = "username") String username) {
        cartService.addQuantity(username, cartId, quantity);

        return "redirect:/showProductInCart";

    }

    @GetMapping("/checkout")
    public String checkOut(@AuthenticationPrincipal(expression = "username") String username) {
        List<Cart> cartList = cartService.getCartItems(username);

        List<Product> products = cartList.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());
        return "redirect/cartShow";
    }


    @GetMapping("/removeCart/{id}")
    public String removeProductFromCart(@PathVariable("id") UUID cartId) {
        // Find the cart you want to remove
        Optional<Cart> cart = cartRepository.findById(cartId);

        // Check if the cart exists
        if (cart.isPresent()) {
            // Disassociate the user from the cart
            Cart cartEntity = cart.get();
            System.out.println(cartEntity + "3467364732647326");
            User user = cartEntity.getUser();
            user.setCart(null);
            userRepository.save(user); // Save the updated user entity

            // Delete the cart
            cartRepository.delete(cartEntity);
        }

        return "redirect:/showProductInCart";
    }


    @PostMapping("/applycoupon")
    public String applycoupon(@RequestParam("ajith") String Couponcode, RedirectAttributes redirectAttributes, Principal principal,Model model) {
        User user = userService.findByUsername(principal.getName());


//        Coupon  coupon =couponService.getdiscountfromtotal(Couponcode);
        boolean isExistingCode = couponService.isCouponcodeExsitingOrNot(Couponcode);
        System.out.println(isExistingCode+"284781947891 it is valid or not");
        if (Couponcode == null || Couponcode.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Coupon code is required.");
            return "redirect:/showProductInCart";
        }
         if (!isExistingCode) {
            redirectAttributes.addFlashAttribute("message", "Enter a valid coupon ");
            return "redirect:/showProductInCart";
        }
        boolean isExpired = couponService.validateCouponCode(Couponcode);
        System.out.println(isExpired+"135325321563673562135");
         if (isExpired) {
            redirectAttributes.addFlashAttribute("message", "Coupon is Expired");
            return "redirect:/showProductInCart";
        }
        Coupon coupon = couponService.getCouponByCouponCode(Couponcode);
             float discount = (float) (coupon.getDiscount()- cartService.getTotalPrice(getCurrentUsername()));
        float updatedTotalAmount = (float) (cartService.getTotalPrice(getCurrentUsername()) - discount);
        System.out.println(discount+"1236778900909898989898998998");

        model.addAttribute("discount", discount);

        float discountone = (float) coupon.getDiscount();
        redirectAttributes.addFlashAttribute("message", "Coupon applied. Total amount"+discount);
        return "redirect:/showProductInCart";
    }
}




