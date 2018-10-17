package com.newdream.api.service;

import com.newdream.api.dao.PersonDao;
import com.newdream.api.entitiy.Person;

/**
 * @author liuhp
 */
public class PersonServiceImpl {

    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean update(int id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null) {
            return false;
        }

        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }

}
