package com.newdream.api.service;

import com.newdream.api.dao.PersonDao;
import com.newdream.api.entitiy.Person;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private PersonDao userDao;

    @Override
    public Person getUserByLoginName(String loginName) {
        try {
            return userDao.findUserByLoginName(loginName);
        } catch (Exception e) {
            throw new RuntimeException("查找用户失败");
        }
    }

    @Override
    public int save(Person user) throws UserException {
        if (user == null){
            throw  new UserException("user is empty");
        }
        Person oldUser = userDao.findUserByLoginName(user.getName());
        if (oldUser != null) {
            throw new RuntimeException("已存在相同登录名");
        }
        try {
            userDao.save(user);
            return user.getId();
        } catch (Exception e) {
            throw new RuntimeException("新增用户失败", e);
        }
    }

    @Override
    public void setUserDao(PersonDao dao) {
        userDao = dao;
    }


}
