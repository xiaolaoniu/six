package cn.itcast.service.system.impl;

import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    @Override
    public void save(User user) {
        String password = user.getPassword(); //明文

        password = new Md5Hash(password,user.getUserName(),2).toString(); //可以加盐  ,加几次

        user.setPassword(password); //密文
        userDao.insert(user);
    }

    @Override
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public void deleteById(String id) {
        userDao.deleteByPrimaryKey(id);
    }

    /*@Override
    public PageBean findPage(int pageNum, int pageSize) {
        Long total = userDao.findCount();//select count(id) from ss_user
//        起始位置有一个计算公式 （当前页码-1）*每页显示条数
        List<User> list = userDao.findPage((pageNum-1)*pageSize,pageSize);//select * from ss_user limit 其实位置，显示的条数pageSize
        return new PageBean(pageNum,pageSize,total,list);
    }*/
    @Override
    public PageInfo findPage(String companyId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize); //为了保证线程安全，PageHelper.startPage方法一定要紧跟一个查询方法
//        List<User> list = userDao.findAll();
        Page list = (Page) userDao.findAll(companyId);
        PageInfo pageInfo = new PageInfo(list,5);
        return pageInfo;
    }

    public static void main(String[] args) {
        String xiaowang = new Md5Hash("123456", "xiaowang", 20).toString();
        System.out.println(xiaowang); //69e36cdd747d5c0d89dda29b236cf4f1
//        https://www.cmd5.com/  TODO 2019年10月24日说5天后
    }
}
