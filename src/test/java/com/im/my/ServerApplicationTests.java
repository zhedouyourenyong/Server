package com.im.my;

import com.im.my.mapper.dao.UserDao;
import com.im.my.model.Message;
import com.im.my.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests
{
    @Autowired
    UserDao userDao;

    @Autowired
    MessageService messageService;

    @Test
    public void contextLoads ()
    {
        Message message=new Message();
        message.setContent("adf");
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        message.setFromId("1");
        message.setToId("3");
        message.setConversationId(message.getConversationId());

        messageService.addMessage(message);
    }

}
