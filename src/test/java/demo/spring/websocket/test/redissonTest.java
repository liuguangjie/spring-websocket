package demo.spring.websocket.test;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by free on 18-7-17.
 */
public class redissonTest {

    @Test
    public void test1() {
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.56.10:6379") //
                .setConnectionPoolSize(2000)/**redis连接池 大小*/
                .setConnectionMinimumIdleSize(100)/**最小空闲连接数*/
                .setPassword("123456");
        RedissonClient redisson = Redisson.create(config);
        /*RKeys keys = redisson.getKeys();
        Iterable<String> iterable =keys.getKeys();
        Iterator<String> iterator = iterable.iterator();

        while (iterator.hasNext()) {
            keys.delete(iterator.next());
        }*/
        RBucket<String> bucket = redisson.getBucket("shop");
        System.out.println(bucket.get());
    }


    /**
     * redis 分布式锁
     */
    @Test
    public void test2() {

        Config config = new Config();
        config.useSingleServer()//
                .setAddress("redis://192.168.56.10:6379") //
                .setPassword("123456");
        RedissonClient redisson = Redisson.create(config);

        RLock lock = redisson.getLock("anyLock");
        RLock lock1 = redisson.getLock("anyLock");
        try{
            // 1. 最常见的使用方法
            //lock.lock();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if(res){ //成功
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            if (lock.isHeldByCurrentThread()) {
                lock.unlinkAsync();
            }

        }



    }


    /**
     * 常用操作
     */
    @Test
    public void test3() {

        Config config = new Config();
        config.useSingleServer()//
                .setAddress("redis://192.168.56.10:6379") //
                .setPassword("123456");
        RedissonClient redisson = Redisson.create(config);

        RScoredSortedSet<Integer> scoredSortedSet = redisson.getScoredSortedSet("shop");
        System.out.println(scoredSortedSet.getScore(1000).intValue());
        scoredSortedSet.add(56, 1000);
        System.out.println(scoredSortedSet.last());
        Object[] objects = scoredSortedSet.toArray();
        for (Object o : objects) {

            System.out.println(o.getClass());
        }
    }

    RedissonClient redisson;
    @Before
    public void init() {

        Config config = new Config();
        config.useSingleServer()//
                .setAddress("redis://192.168.56.10:6379") //
                .setPassword("123456")
        .setDatabase(0);
        redisson = Redisson.create(config);
    }
    /***
     * hmset hmget
     *
     * 命令操作
     */
    @Test
    public void test4() {
        RMap<String, String> rMap = redisson.getMap("test1");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("tt", "2");
        //hashMap.put("3", "4");
        //rMap.put("name", "zhangsan");
        //rMap.putAll(hashMap);
        System.out.println(rMap.get("name"));
        //rMap.clear();
    }

}
