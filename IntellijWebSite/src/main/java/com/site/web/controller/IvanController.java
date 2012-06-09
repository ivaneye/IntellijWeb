package com.site.web.controller;

import com.dao.UserDAO;
import com.dao.UserDAOImpl;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: Administrator
 * Time: 2012-06-09 04:05:24
 */
@Controller
@RequestMapping(value = "ivan")
public class IvanController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String methodName(Model model,User user) {
        model.addAttribute("name",user.getName());
        user.setRecId(1111L);
        user.setPassword("1111");
        userDAO.insert(user);

        return "test";
    }
}