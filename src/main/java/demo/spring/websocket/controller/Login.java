package demo.spring.websocket.controller;

import demo.spring.websocket.entity.Result;
import demo.spring.websocket.entity.User;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by free on 18-7-21.
 */
@RestController
@RequestMapping
public class Login {

    @Autowired
    private RedissonClient redissonClient;


    @RequestMapping("/reg")
    public Result register(User user) {

        /** RMap */
        RMap<String, String> rMap = redissonClient.getMap(user.getUserName());
        rMap.put("userName", user.getUserName());
        rMap.put("passwd", user.getPasswd());
        return Result.ok(null);
    }


    @RequestMapping("/login")
    public Result login(User user) {
        RMap<String, String> rMap = redissonClient.getMap(user.getUserName());
        String passwd = rMap.get("passwd");
        String userName = rMap.get("userName");
        if (!user.getPasswd().equals(passwd) &&
                !user.getUserName().equals(userName)) {
            return Result.error();
        }
        return Result.ok(user.getUserName());
    }
}
