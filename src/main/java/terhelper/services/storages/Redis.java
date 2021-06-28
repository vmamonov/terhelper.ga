package terhelper.services.storages;

import redis.clients.jedis.JedisPool;

public class Redis {
	private JedisPool pool;
	private String keyRoot = "th";

	public Redis() {
		pool = new JedisPool("localhost", 6379);
	}
	
	public String get(String key) {
		return pool.getResource().get(keyRoot +"." + key);
	}
	
	
	public String set(String key, String value) {
		return pool.getResource().set(keyRoot +"."+ key, value);
	}
	
	public long ttl(String key) {
		String keyResult = keyRoot +"."+ key;
		return pool.getResource().ttl(keyResult);
	}

	
	public String set(String key, String value, long seconds) {
		String keyResult = keyRoot +"."+ key;
		String result = pool.getResource().set(keyResult, value);
		pool.getResource().expire(keyResult, seconds);
		return result;
	}
	
	
	public Boolean exists(final String key) {
		return pool.getResource().exists(keyRoot +"."+ key);
	}
	
	public Long del(final String key) {
		return pool.getResource().del(keyRoot +"."+ key);
	}
}
