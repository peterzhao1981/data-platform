package com.mode.api;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mode.base.response.Message;
import com.mode.entity.User;
import com.mode.exception.ModeException;
import com.mode.service.user.UserService;

/**
 * Created by Ben Hu on 16/7/19.
 */
@Controller
@RequestMapping(value = "/users")
public class UserApi {

    private static final String EMAIL_FORMAT = "[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com|cn|org|edu|hk))";
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/passwords", method = RequestMethod.GET)
    public String inputAccount(Map<String, Object> model) {
        return "retrievepwd";
    }

    @RequestMapping(value = "/passwords", method = RequestMethod.POST)
    public String validateAccount(Map<String, Object> model,
                                  @RequestParam("email") String email) {
        if (!email.matches(EMAIL_FORMAT)){
            model.put("emailMsg", "Email address is not valid!");
            return "password";
        } else {
            // Validate email
            User user = userService.getUserByEmail(email);
            if (user == null){
                throw new ModeException(Message.EMAIL_NOT_FOUND);
            }
            // Sent email
            int resetPasswordKey = ThreadLocalRandom.current().nextInt(100000, 1000000);
            user.setResetPasswordKey(resetPasswordKey);
            userService.updateUser(user);
            if (!userService.sentEmail(email)){
                throw new ModeException(Message.SEND_EMAIL_FAILED);
            }
            return "feedback";
        }
    }
}
