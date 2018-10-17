package com.newdream.api.dao;

import com.newdream.api.entitiy.Person;

public interface PersonDao {

        Person getPerson(int id);

        boolean update(Person person);

        Person findUserByLoginName(String loginName);

        void save(Person person);

}
