package com.newdream.api.service;

import com.newdream.api.dao.PersonDao;
import com.newdream.api.entitiy.Person;

public interface UserService {

    Person getUserByLoginName(String loginName);

    int save(Person user) throws UserException;

    void setUserDao(PersonDao dao);
}
