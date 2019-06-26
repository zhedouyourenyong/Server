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
    String TABLE_NAME = " user ";

    User selectByName(String name);
    void addUser(User user);

    @Select({"select * from ",TABLE_NAME," where id=#{userId}"})
    String selectById( @Param(value = "userId") String userId);
}
