package com.ecomapp.ecomapp.controller.ReferralController;

import com.ecomapp.ecomapp.config.OtpConfig.AppConfig;
import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.ReferralMethod;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.security.Principal;

@Controller
@RequestMapping("/referral")
public class ReferralController {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;


    @Autowired
    private UserService userService;


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/success")
    public String Success() {
        return "/user/success";
    }


    @GetMapping("/refer")
    public String showReferralPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/user/referral";
    }


    @PostMapping("/refer")
    public String referUser(
            @ModelAttribute("userDto") UserDto userDto,
            @RequestParam("referralMethod") ReferralMethod referralMethod,
            Model model
    ) {
        User referrer = userService.findByUsername(getCurrentUsername()); // Get the currently logged-in user
        User referredUser = userService.findByEmailOrPhone(userDto.getEmail(), userDto.getPhoneNumber());
        System.out.println(referredUser + "1234567891011 refered user");
        if (referredUser != null)
        {
            referrer.getReferredUsers().add(referredUser);
            referrer.setReferralMethod(referralMethod);
            userService.save(referrer); // Save the changes to the referrer user
            String referralCode = userService.getReferralCodeForUser(referrer.getId());
            sendReferralEmail(referredUser.getEmail(), String.valueOf(referralCode));
            System.out.println(referralCode+"12355555555768978787878 referralEmail ---------------testcase");
            return "redirect:/success"; // Redirect to a success page
        } else {
            model.addAttribute("error", "User not found.");
            return "/user/referral"; // Show an error message
        }
    }

    private void sendReferralEmail(String recipientEmail, String referralCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Your Referral Code");

            Context context = new Context();
            context.setVariable("referralCode", referralCode);
            String emailContent = templateEngine.process("referral-email", context);

            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            // Handle email sending errors
        }
    }
}