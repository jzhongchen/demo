package com.example.demo.controller;

import cn.hutool.json.JSONUtil;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * DESC：
 *
 * @author jzhongchen
 * @since 2022/05/10 14:03
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @PostMapping("/mysql")
    public Long addEntity(@RequestBody User user) {
        userDao.insert(user);
        return user.getId();
    }

    @GetMapping("/mysql")
    public User getDetail(Long id) {
        return userDao.queryById(id);
    }

    @PostMapping("/redis")
    public String addCache(@RequestBody User user) {
        redisTemplate.opsForValue().set("user", JSONUtil.toJsonStr(user));
        return "success";
    }

    @GetMapping("/redis")
    public User listCache() {
        String user = redisTemplate.opsForValue().get("user");
        return JSONUtil.toBean(user, User.class);
    }

}
