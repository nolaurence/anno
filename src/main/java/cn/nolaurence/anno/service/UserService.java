package cn.nolaurence.anno.service;

import cn.nolaurence.anno.entity.User;

public interface UserService {
    User findUserByUsernameAndPassword(String username, String password);
}
