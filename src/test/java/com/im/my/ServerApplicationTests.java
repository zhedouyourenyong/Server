package com.im.my;

import com.im.my.mapper.dao.UserDao;
import com.im.my.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests
{
    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads ()
    {
        User user=new User();
        user.setName("zzh");
        user.setPassword("123");
        user.setSalt("adghj45");

        userDao.addUser(user);

        System.out.println(user.getId());
    }

}
