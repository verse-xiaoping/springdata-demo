package com.github.springdata.test;

import com.github.springdata.config.Config;
import com.github.springdata.entity.User;
import com.github.springdata.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by verse on 2018/4/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SpringDataTest {
    @Resource
    private UserRepository userRepository;

    @Test
    public void save(){
        User user = null;
        List<User> users = new ArrayList<User>();
        for (int i = 100; i < 200; i++) {
            user = new User();
            user.setUserName("xp" + i);
            user.setPassword("123");
            user.setAddress("成都");
            user.setAge(28);
            users.add(user);
            user=null;
        }
        userRepository.save(users);
    }
    @Test
    public void findAll(){
        List<User> list = (List<User>) userRepository.findAll();
        for (User user: list) {
            System.out.println(user);
        }
    }
    @Test
    public void  findOne(){
        System.out.println(userRepository.findOne(1));
    }
    @Test
    public void update() {
        User user = new User();
        user.setId(1);
        user.setAddress("南京");
        userRepository.save(user);
    }
    @Test
    @Transactional
    public void delete(){
        userRepository.delete(3);
//        int i=1/0;
    }
    @Test
    public void page(){
        //分页查询
        Page<User> users = userRepository.findAll(new PageRequest(0, 5));
        System.out.println("一页多少数据："+users.getSize());
        System.out.println("第几页："+(users.getNumber() + 1));
        System.out.println("总页数："+users.getTotalPages());
        System.out.println("数据总数："+users.getTotalElements());
        System.out.println("是否是第一页："+users.isFirst());
        System.out.println("是否是最后页："+users.isLast());
        System.out.println("数据："+users.getContent());
    }
    @Test
    public void query(){
        //根据条件查询
        List<User> users = userRepository.findUserByUserName("xp");
        System.out.println(users.size());
    }
    @Test
    public void queryLike(){
        //根据条件模糊查找
        List<User> users = userRepository.findByUserName("xp");
        System.out.println(users.size());

    }
    @Test
    public void updateQuery(){
        userRepository.updateQuery("xp002", 4);
    }
}
