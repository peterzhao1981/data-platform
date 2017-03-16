package com.mode.service.user;

import com.mode.entity.User;

/**
 * Created by Ben Hu on 2016/7/20.
 */
public interface UserService {

    /**
     * Description: Validate email
     *
     * @param email user's Email
     * @return validate is true or false
     */
    User getUserByEmail(String email);

    /**
     * Description: Update user info
     *
     * @param user
     * @return Integer
     */
    Integer updateUser(User user);

    /**
     * Description: Reset user password
     *
     * @param email user's Email
     * @return Sent email status, true or false
     */
    boolean sentEmail(String email);

    /**
     * Description:
     *
     * @param checkCode Generate random
     * @return Reset password operation status: true or false
     */
    public boolean resetPwd(String checkCode);
}
