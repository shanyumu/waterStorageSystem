package com.veyit.waterstoragesystem.mapper;


import com.veyit.waterstoragesystem.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);

    //更改用户信息
    int updateUser(User user);

    User getUserById(Integer id);

    void deleteUserById(Integer id);

    List<User> getUserList();

    int updateUserPwd(String newPwd,Integer id);

    User getUById(Integer id);

    int update(User user);
}
