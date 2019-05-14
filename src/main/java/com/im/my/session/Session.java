package com.im.my.session;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session
{
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId, String userName)
    {
        this.userId = userId;
        this.userName = userName;
    }

}