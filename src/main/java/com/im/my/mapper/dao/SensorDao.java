package com.im.my.mapper.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "sensorDao")
@Mapper
public interface SensorDao
{
    @Insert({"insert into sensor (data) values (#{data})"})
    int insertData(@Param(value = "data") String data);
}
