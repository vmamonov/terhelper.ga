package terhelper.services.storages;

import redis.clients.jedis.Jedis;

public class Redis {
	private Jedis jedis;
	private String keyRoot = "th";

	public Redis() { 
		jedis = new Jedis("localhost", 6379);
	}
	
	public String get(String key) {
		return jedis.get(keyRoot +"." + key);
	}
	
	
	public String set(String key, String value) {
		return jedis.set(keyRoot +"."+ key, value);
	}
	
	public long ttl(String key) {
		String keyResult = keyRoot +"."+ key;
		return jedis.ttl(keyResult);
	}

	
	public String set(String key, String value, long seconds) {
		String keyResult = keyRoot +"."+ key;
		String result = jedis.set(keyResult, value);
		jedis.expire(keyResult, seconds);
		return result;
	}
	
	
	public Boolean exists(final String key) {
		return jedis.exists(keyRoot +"."+ key);
	}
	
	public Long del(final String key) {
		return jedis.del(keyRoot +"."+ key);
	}
}
