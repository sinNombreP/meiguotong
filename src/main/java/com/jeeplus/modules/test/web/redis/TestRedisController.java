package com.jeeplus.modules.test.web.redis;

import com.jeeplus.core.web.BaseController;
import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.redis.support.RedisClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/interface/test")
public class TestRedisController extends BaseController {

    @Autowired
    RedisClientFactory redisClientFactory;

    @RequestMapping(value = "redis")
    public String test() {
        RedisClient client=redisClientFactory.getResource();
        client.set("test", "美国通项目");
        System.out.println(client.get("test"));
        return null;
    }
}
