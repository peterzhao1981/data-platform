package com.mode.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mode.dao.UserDao;
import com.mode.entity.User;

/**
 * Created by Ben Hu on 2016/7/20.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * Description: Validate email
     *
     * @param email user's Email
     * @return validate is true or false
     */
    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    /**
     * Description: Update user info
     *
     * @param user
     * @return Integer
     */
    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * Description: Reset user password
     *
     * @param email user's Email
     * @return Sent email status, true or false
     */
    @Override
    public boolean sentEmail(String email) {
        return false;
    }

    /**
     * Description: Reset user's password
     *
     * @param checkCode Generate random
     * @return Reset password operation status: true or false
     */
    @Override
    public boolean resetPwd(String checkCode) {
        return false;
    }
}
