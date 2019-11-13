package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.User;

import java.util.List;

public interface UserDao {

    public List<User> findAll(String companyId);

    void insert(User user);

    User selectByPrimaryKey(String id);

    void updateByPrimaryKey(User user);

    void deleteByPrimaryKey(String id);
}
