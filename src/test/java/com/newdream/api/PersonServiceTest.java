package com.newdream.api;

import com.newdream.api.dao.PersonDao;
import com.newdream.api.entitiy.Person;
import com.newdream.api.service.PersonServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    @Mock
    private PersonDao mockDao;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @BeforeTest
    public void setUp() throws Exception {
        //模拟PersonDao对象
        mockDao = mock(PersonDao.class);
        when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);

        personServiceImpl = new PersonServiceImpl(mockDao);
    }

    @Test
    public void testUpdate() throws Exception {
        boolean result = personServiceImpl.update(1, "new name");
        assertTrue("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, times(1)).update(isA(Person.class));
    }

//    @Test
//    public void testUpdateNotFind() throws Exception {
//        boolean result = personServiceImpl.update(2, "new name");
//        assertFalse("must true", result);
//        //验证是否执行过一次getPerson(1)
//        verify(mockDao, times(1)).getPerson(eq(1));
//        //验证是否执行过一次update
//        verify(mockDao, never()).update(isA(Person.class));
//    }

}
