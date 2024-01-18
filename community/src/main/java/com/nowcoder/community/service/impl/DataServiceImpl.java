package com.nowcoder.community.service.impl;

import com.nowcoder.community.service.IDataService;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements IDataService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


    @Override
    public void recordUV(String ip) {
        String uvKey = RedisKeyUtil.getUVKey(sdf.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(uvKey, ip);
    }

    @Override
    public Long calculateUV(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 整理该日期范围内的key
        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String uvKey = RedisKeyUtil.getUVKey(sdf.format(calendar.getTime()));
            keyList.add(uvKey);
            calendar.add(Calendar.DATE, 1);
        }
        String unionKey = RedisKeyUtil.getUVKey(sdf.format(start), sdf.format(end));
        redisTemplate.opsForHyperLogLog().union(unionKey, keyList.toArray(new String[0]));
        return redisTemplate.opsForHyperLogLog().size(unionKey);
    }

    @Override
    public void recordDAU(Integer userId) {
        String dauKey = RedisKeyUtil.getPrefixDAUKey(sdf.format(new Date()));
        redisTemplate.opsForValue().setBit(dauKey, userId, true);
    }

    @Override
    public Long calculateDAU(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 整理该日期范围内的key
        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String dauKey = RedisKeyUtil.getPrefixDAUKey(sdf.format(calendar.getTime()));
            keyList.add(dauKey.getBytes());
            calendar.add(Calendar.DATE, 1);
        }
        String orKey = RedisKeyUtil.getPrefixDAUKey(sdf.format(start), sdf.format(end));
        // 进行or运算
        return (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        orKey.getBytes(),
                        keyList.toArray(new byte[0][0]));
                return connection.bitCount(orKey.getBytes());
            }
        });
    }
}
