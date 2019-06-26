package com.im.my.service;


import com.im.my.mapper.dao.MessageDao;
import com.im.my.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class MessageService
{
    @Autowired
    MessageDao messageDao;
    @Autowired
    SensitiveService sensitiveService;

    public int addMessage (Message message)
    {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message);
    }
}
