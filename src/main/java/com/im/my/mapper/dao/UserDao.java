package com.im.my.mapper.dao;

import com.im.my.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserDao
{
    User selectByName(String name);
    void addUser(User user);

    @Select({"select * from user where id=#{userId}"})
    String selectById( @Param(value = "userId") String userId);
}
