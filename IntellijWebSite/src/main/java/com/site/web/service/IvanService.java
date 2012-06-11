package com.site.web.service;

import com.dao.UserDAO;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-10
 * Time: 下午11:17
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class IvanService {

    @Autowired
    private UserDAO userDAO;

    public User find(Long recId){
        return userDAO.selectByPrimaryKey(recId);
    }

    public void insert(User user){
        userDAO.insert(user);
    }

    public void update(User user){
        userDAO.updateByPrimaryKey(user);
    }

    public void delete(Long recId){
        userDAO.deleteByPrimaryKey(recId);
    }
}
