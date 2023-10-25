package com.ecomapp.ecomapp.controller;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Coupon;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository;
import com.ecomapp.ecomapp.repository.ProductRepo.ProductRepository;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.CartService.CartService;
import com.ecomapp.ecomapp.service.CouponService.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private com.ecomapp.ecomapp.repository.CouponRepository.CouponRepository CouponRepository;

//    @PostMapping("/addProductInCart")
//    public String addCart(@RequestParam("productId") UUID productId,
//                          @RequestParam("couponCode") String couponCode,
//                          Principal principal) {
//        String email = principal.getName();
//
//        // Check if the coupon is valid and can be applied
//        boolean isValidCoupon = couponService.validateCoupon(couponCode, email);
//
//        if (isValidCoupon) {
//            cartService.addToCartItem(email, productId, couponCode);
//        } else {
//            System.out.println("Coupon is not valid or available.");
//        }
//
//        return "redirect:/shopView";
//    }
////}
//
//    @PostMapping("/addProductInCart")
//    public String addCart(
//            @RequestParam("productId") UUID productId,
//            @RequestParam("couponCode") String couponCode,
//            Principal principal) {
//        String email = principal.getName();
//
//        // Check if the coupon is valid and can be applied
//        boolean isValidCoupon = couponService.validateCoupon(couponCode, email);
//
//        if (isValidCoupon) {
//            // Apply the discount percentage to the cart
//            double discountPercentage = couponService.getDiscountPercentage(couponCode);
//            Cart cart = cartService.addToCartItem(email, productId);
//            cart.setDiscountPercentage(discountPercentage);
//        } else {
//            System.out.println("Coupon is not valid or available.");
//        }
//
//        return "redirect:/shopView";
//    }



    @PostMapping("/addProductInCart")
    public String addCart(@RequestParam("productId") UUID productId, Principal principal){
        System.out.println(principal.getName()+"----------------------------------");
        System.out.println(productId+"====================");
        String email=principal.getName();
       Optional<Product> ExistingProduct =productRepository.findById(productId);
       if (ExistingProduct.isPresent()){
           cartService.addToCartItem(email,productId);
       }else{
           System.out.println("product is not avaliable ");
       }
        System.out.println(productId);
        return "redirect:/shopView";
    }



     @GetMapping("/showProductInCart")
     public String showcart(Model model,Principal prinicipal){
        String email=prinicipal.getName();
        List<Cart>avaliablecartitem=cartRepository.findAll();
         System.out.println(avaliablecartitem.get(0).getProduct().getImages().get(0)+"---------------");
        model.addAttribute("avaliablecartitem", avaliablecartitem);
        model.addAttribute("total",cartService.getTotalPrice(prinicipal.getName()));
         List<Coupon> availableCoupons = CouponRepository.findAll();


         model.addAttribute("availableCoupons", availableCoupons);
         System.out.println(availableCoupons);

         System.out.println(prinicipal.getName());

         System.out.println("is it  right ");
         return "/user/shopCartOne";
     }


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
                                     @RequestParam(name="quantity") int quantity,
                                     @AuthenticationPrincipal(expression = "username") String username){
     cartService.addQuantity(username,cartId,quantity);

        return "redirect:/showProductInCart";

    }

    @GetMapping("/checkout")
    public String checkOut( @AuthenticationPrincipal(expression = "username") String username){
        List<Cart> cartList = cartService.getCartItems(username);

        List<Product> products = cartList.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());
        return "redirect/cartShow";
    }




    @GetMapping("/removeCart/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        cartRepository.deleteById(id);
        return "redirect:/showProductInCart";
    }



}



