package com.ecomapp.ecomapp.controller.usercontroller;

import com.ecomapp.ecomapp.service.address.DefaultAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
public class DefaultAddressController {

    @Autowired
    private DefaultAddressService defaultAddressService;

    @GetMapping("/user/default-address/set/{addressId}")
    public String setDefaultAddress(@PathVariable("addressId") UUID addressId, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();

        defaultAddressService.setDefaultAddressForUser(username, addressId);
        System.out.println(username+"usename"+addressId+"addressid========================");
        redirectAttributes.addFlashAttribute("message" , "address updated as default");
        return "redirect:/profile/details";
    }
}
