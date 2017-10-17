package com.webside.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;

import com.webside.util.SerializeUtil;
import com.webside.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
 * @ClassName JedisManager
 * @Description TODO
 *
 * @author wjggwm
 * @data 2016年12月13日 下午1:10:16
 */
public class RedisManager {

	private JedisPool jedisPool;

	public static String BEANNAME = "redisManager";

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Jedis getJedis() throws JedisConnectionException {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
		} catch (Exception e) {
			throw new JedisConnectionException(e);
		}
		return jedis;
	}

	public byte[] getValueByKey(int dbIndex, byte[] key) {
		Jedis jedis = null;
		byte[] result = null;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			result = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
		return result;
	}

	public Long deleteByKey(int dbIndex, byte[] key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			Long result = jedis.del(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
		return null;
	}

	public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			jedis.set(key, value);
			if (expireTime > 0)
				jedis.expire(key, expireTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
	}

	/**
	 * 获取所有Session
	 * 
	 * @param dbIndex
	 * @param redisShiroSession
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<Session> AllSession(int dbIndex, String redisShiroSession) {
		Jedis jedis = null;
		Set<Session> sessions = new HashSet<Session>();
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			Set<byte[]> byteKeys = jedis.keys(redisShiroSession.getBytes());
			if (byteKeys != null && byteKeys.size() > 0) {
				for (byte[] bs : byteKeys) {
					Session obj = SerializeUtil.deserialize(jedis.get(bs), Session.class);
					if (obj instanceof Session) {
						sessions.add(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
		return sessions;
	}

	/**
	 * 简单的Get
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T>... requiredType) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			return SerializeUtil.deserialize(jds.get(key.getBytes()), requiredType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 简单的set
	 * 
	 */
	public void set(String key, Object value) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.set(key.getBytes(), svalue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
	}

	/**
	 * 过期时间的
	 * 
	 * @param timer
	 *            （秒）
	 */
	public void setex(String key, Object value, int timer) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.setex(key.getBytes(), timer, svalue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}

	}

	/**
	 * 获取hash里面的key对应的value
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> T getVByMap(String mapkey, String key, Class<T> requiredType) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			List<byte[]> result = jds.hmget(mapkey.getBytes(), key.getBytes());
			if (null != result && result.size() > 0) {
				byte[] x = result.get(0);
				T resultObj = SerializeUtil.deserialize(x, requiredType);
				return resultObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 获取hash里面的多个key对应的value集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListVByMap(String mapkey, String key, Class<T> requiredType) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			List<byte[]> result = jds.hmget(mapkey.getBytes(), key.getBytes());
			if (null != result && result.size() > 0) {
				List<T> list = new ArrayList<T>();
				for (byte[] x : result) {
					if (null != x) {
						list.add(SerializeUtil.deserialize(x, requiredType));
					}
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 往hash里面追加key-value
	 */
	public void setVByMap(String mapkey, String key, Object value) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.hset(mapkey.getBytes(), key.getBytes(), svalue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}

	}

	/**
	 * 删除hash里的指定key-value对
	 * 
	 */
	public Object delByMapKey(String mapKey, String... dkey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[][] dx = new byte[dkey.length][];
			for (int i = 0; i < dkey.length; i++) {
				dx[i] = dkey[i].getBytes();
			}
			Long result = jds.hdel(mapKey.getBytes(), dx);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 往redis里取set整个集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> getVByList(String setKey, Class<T> requiredType) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Set<T> set = new HashSet<T>();
			Set<byte[]> xx = jds.smembers(setKey.getBytes());
			for (byte[] bs : xx) {
				T t = SerializeUtil.deserialize(bs, requiredType);
				set.add(t);
			}
			return set;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 获取Set长度
	 * 
	 */
	public Long getLenBySet(String setKey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Long result = jds.scard(setKey);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 删除Set
	 * 
	 */
	public Long delSetByKey(String key, String... dkey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Long result = 0L;
			if (null == dkey) {
				result = jds.srem(key);
			} else {
				result = jds.del(key);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 随机 Set 中的一个值
	 * 
	 */
	public String srandmember(String key) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			String result = jds.srandmember(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 往redis里存Set
	 * 
	 */
	public void setVBySet(String setKey, Object value) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.sadd(setKey.getBytes(), svalue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
	}
	
	/**
	 * 往redis里存Set
	 * 
	 */
	public void setVBySet(String setKey, String value) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			jds.sadd(setKey.getBytes(), value.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
	}

	/**
	 * 取set
	 * 
	 */
	public Set<String> getSetByKey(String key) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Set<String> result = jds.smembers(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 往redis里存zset
	 * 
	 */
	public Long zadd(String key, double score, String member) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Long result = jds.zadd(key, score, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 区间取zset
	 * 
	 */
	public Set<String> zrevrangebyscore(String key, String max, String min, int offset, int count) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			if (StringUtils.isBlank(max)) {
				max = "+inf";
			}
			if (StringUtils.isBlank(min)) {
				min = "-inf";
			}
			Set<String> result = jds.zrevrangeByScore(key, max, min, offset, count);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 查询zset的成员总数
	 * 
	 */
	public Long zcard(String key) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Long result = jds.zcard(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 往redis里存List
	 * 
	 */
	public void setVByList(String listKey, Object value) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.rpush(listKey.getBytes(), svalue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
	}

	/**
	 * 往redis里取list
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getVByList(String listKey, int start, int end, Class<T> requiredType) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			List<T> list = new ArrayList<T>();
			List<byte[]> xx = jds.lrange(listKey.getBytes(), start, end);
			for (byte[] bs : xx) {
				T t = SerializeUtil.deserialize(bs, requiredType);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 获取list长度
	 * 
	 */
	public Long getLenByList(String listKey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			Long result = jds.llen(listKey.getBytes());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 */
	public Long delByKey(String... dkey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			byte[][] dx = new byte[dkey.length][];
			for (int i = 0; i < dkey.length; i++) {
				dx[i] = dkey[i].getBytes();
			}
			Long result = jds.del(dx);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return null;
	}

	/**
	 * 判断是否存在
	 * 
	 * @return
	 */
	public boolean exists(String existskey) {
		Jedis jds = null;
		try {
			jds = getJedis();
			jds.select(0);
			return jds.exists(existskey.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jds != null) {
				jds.close();
			}
		}
		return false;
	}

}
