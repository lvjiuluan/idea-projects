package com.nowcoder.community;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.form.LoginForm;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CommunityApplicationTest {

    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    // 统计一组数据Boolean值
    @Test
    public void testBitMap() {
        String redisKey = "test:bm:01";
        redisTemplate.opsForValue().setBit(redisKey, 1, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);
        // 查询
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 2));
        // 统计
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(obj);
    }

    // 统计三组数据的Boolean，并对这三组数据做or运算
    @Test
    public void testBitMapOperation() {
//        String redisKey2 = "test:bm:02";
//        redisTemplate.opsForValue().setBit(redisKey2, 0, true);
//        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
//        redisTemplate.opsForValue().setBit(redisKey2, 2, true);
//
//        String redisKey3 = "test:bm:03";
//        redisTemplate.opsForValue().setBit(redisKey3, 2, true);
//        redisTemplate.opsForValue().setBit(redisKey3, 3, true);
//        redisTemplate.opsForValue().setBit(redisKey3, 4, true);
//
//        String redisKey4 = "test:bm:04";
//        redisTemplate.opsForValue().setBit(redisKey4, 4, true);
//        redisTemplate.opsForValue().setBit(redisKey4, 5, true);
//        redisTemplate.opsForValue().setBit(redisKey4, 6, true);

        String redisKey = "test:bm:or";
//        Object obj = redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.bitOp(RedisStringCommands.BitOperation.OR, redisKey.getBytes(),
//                        redisKey2.getBytes(),
//                        redisKey3.getBytes(),
//                        redisKey4.getBytes());
//            }
//        });
//        System.out.println(obj);
        for (int i = 0; i < 7; i++) {
            System.out.println(redisTemplate.opsForValue().getBit(redisKey, Long.valueOf(i)));
        }
    }


    // 统计20万个重复数据的独立总数
    @Test
    public void TestHyperLoglog() {
        String redisKey = "test:hll:01";
        for (int i = 1; i <= 100; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }
        for (int i = 1; i <= 100; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, (int) (Math.random() * 100 + 1));
        }
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    // 将3组数据合并，再统计合并后的重复数据的独立总数
    @Test
    public void testHyperLoglogUnion() {
//        String redisKey2 = "test:hll:02";
//        for (int i = 1; i <= 100; i++) {
//            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
//        }
//        String redisKey3 = "test:hll:03";
//        for (int i = 51; i <= 150; i++) {
//            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
//        }
//        String redisKey4 = "test:hll:04";
//        for (int i = 100; i <= 200; i++) {
//            redisTemplate.opsForHyperLogLog().add(redisKey4, i);
//        }
        String unionKey = "test:hll:union";
//        redisTemplate.opsForHyperLogLog().union(unionKey, redisKey2, redisKey3, redisKey4);
        System.out.println(redisTemplate.opsForHyperLogLog().size(unionKey));
    }

    @Test
    public void kafkaTest() {
        kafkaProducer.sendMsg("test", "你好");
        kafkaProducer.sendMsg("test", "在吗");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "吕九峦");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

//        mailClient.sendMail("1342409485@qq.com", "HTML", content);
    }

    @Test
    public void testRedis() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setId(1);
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date());
        loginTicket.setTicket("test");
        ValueOperations opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(loginTicket.getTicket(), gson.toJson(loginTicket));

    }

    @Test
    public void testGetFromRedis() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String loginTicketString = opsForValue.get("a08dd45431c5499ebcd2817bf3e40e8f");
        LoginTicket loginTicket = gson.fromJson(loginTicketString, LoginTicket.class);
        log.info("loginTicket = {}", loginTicket);
        Date expired = loginTicket.getExpired();
        System.out.println(expired.after(new Date()));
    }

    @Test
    public void GenerateMd5() {
        System.out.println(CommunityUtil.md5("147258364f5"));
    }
}

@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMsg(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }
}

@Component
class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {
        System.out.println(record.value());
    }
}