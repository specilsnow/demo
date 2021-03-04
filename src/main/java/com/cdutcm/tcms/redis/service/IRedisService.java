package com.cdutcm.tcms.redis.service;

import java.util.List;

/**
 * @author       zhufj
 * @Description  redis 接口
 * @Date         2017-7-6
 */
public interface IRedisService {

	public boolean set(String key, String value);
	
	public boolean set(String key, Object obj);
	
	public String get(String key);
	
	public <T> T get(String key,Class<T> clz);
	
	public boolean expire(String key,long expire);
	
	public <T> boolean setList(String key ,List<T> list);
	
	public <T> List<T> getList(String key,Class<T> clz);
	
	public long lpush(String key,Object obj);
	
	public long rpush(String key,Object obj);
	
	public String lpop(String key);
	
	public <T> T lpop(final String key,Class<T> clz);
	
	//public long delete(final String... keys);
}
