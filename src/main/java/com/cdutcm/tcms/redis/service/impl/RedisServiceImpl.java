package com.cdutcm.tcms.redis.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.JSONUtil;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.redis.service.IRedisService;

/**
 * @author zhufj
 * @Description Redis 接口实现
 * @Date 2017-7-6
 */
@Service
public class RedisServiceImpl implements IRedisService {

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public boolean set(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				connection.set(serializer.serialize(key),
						serializer.serialize(value));
				return true;
			}
		});
		return result;
	}
	
	@Override
	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				byte[] value = connection.get(serializer.serialize(key));
				return serializer.deserialize(value);
			}
		});
		return result;
	}
	
	@Override
	public boolean set(final String key,Object obj){
		String str = JSONUtil.toJson(obj);
		return set(key, str);
	}
	
	@Override
	public <T> T get(final String key,Class<T> clz) {
		String result = get(key);
		return JSONUtil.toBean(result, clz);
	}

	@Override
	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	@Override
	public <T> boolean setList(String key, List<T> list) {
		String value = JSONUtil.toJson(list);
		return set(key, value);
	}

	@Override
	public <T> List<T> getList(String key, Class<T> clz) {
		String json = get(key);
		if (StringUtil.notEmpty(json)) {
			List<T> list = JSONUtil.toList(json, clz);
			return list;
		}
		return null;
	}

	@Override
	public long lpush(final String key, Object obj) {
		final String value = JSONUtil.toJson(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				long count = connection.lPush(serializer.serialize(key),
						serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public long rpush(final String key, Object obj) {
		final String value = JSONUtil.toJson(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				long count = connection.rPush(serializer.serialize(key),
						serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public String lpop(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				byte[] res = connection.lPop(serializer.serialize(key));
				return serializer.deserialize(res);
			}
		});
		return result;
	}
	
	@Override
	public <T> T lpop(final String key,Class<T> clz) {
		String result = lpop(key);
		return JSONUtil.toBean(result, clz);
	}
	
	/**
     * @param key
     */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//    public long delete(final String... keys) {
//        return redisTemplate.execute(new RedisCallback() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                long result = 0;
//                for (int i = 0; i < keys.length; i++) {
//                    result = connection.del(keys[i].getBytes());
//                }
//                return result;
//            }
//        });
//    }


}
