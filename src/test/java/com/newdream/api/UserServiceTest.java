package com.newdream.api;

import com.newdream.api.dao.PersonDao;
import com.newdream.api.entitiy.Person;
import com.newdream.api.service.UserDtoService;
import com.newdream.api.service.UserDtoServiceImpl;
import com.newdream.api.service.UserException;
import com.newdream.api.service.UserServiceImpl;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest  extends  AbstractTestNGSpringContextTests{



    @Autowired
    private UserDtoService userDtoSerivce;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PersonDao mockUserDao;

    @BeforeTest
    public void setUp() {
        mockUserDao = mock(PersonDao.class);
        userService = new UserServiceImpl();
        userService.setUserDao(mockUserDao);
        userDtoSerivce = new UserDtoServiceImpl();
//        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getUserByLoginName() {
        Person rtnUser = new Person(1,"sdsd");
        rtnUser.setName("admin");
        // Stud 设置方法调用的预期返回
        when(mockUserDao.findUserByLoginName(anyString())).thenReturn(rtnUser);
        Person user = userService.getUserByLoginName("admin");
        // Mock 验证方法调用
        verify(mockUserDao, times(1)).findUserByLoginName(anyString());
        // assert 返回值是否和预期一样
//        assertThat(user).isNotNull();
//        assertThat(user.getName()).isEqualTo("admin");
    }

    @Test(expectedExceptions = UserException.class)
    public void throwIfOrderIsNull() throws UserException {
        userService.save(null);
    }


    @Test
    public void test() throws Exception {
        userDtoSerivce.deleteAllUsers();
        // 插入5个用户
        userDtoSerivce.create("a", 1);
        userDtoSerivce.create("b", 2);
        userDtoSerivce.create("c", 3);
        userDtoSerivce.create("d", 4);
        userDtoSerivce.create("e", 5);

        // 查数据库，应该有5个用户
        Assert.assertEquals(5, userDtoSerivce.getAllUsers().intValue());

        // 删除两个用户
        userDtoSerivce.deleteByName("a");
        userDtoSerivce.deleteByName("e");

        // 查数据库，应该有5个用户
        Assert.assertEquals(3, userDtoSerivce.getAllUsers().intValue());

    }

}
