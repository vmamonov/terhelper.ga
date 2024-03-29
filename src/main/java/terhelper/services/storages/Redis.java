package terhelper.services.storages;

import redis.clients.jedis.Jedis;
import terhelper.AppConfig;

public class Redis {
	private Jedis redisClient;
	private String keyRoot = "th";

	public Redis() { 
		redisClient = new Jedis("redis", 50000);
		redisClient.auth(AppConfig.getInstance().getRedisPassword());
	}
	
	public String get(String key) {
		return redisClient.get(keyRoot +"." + key);
	}
	
	
	public String set(String key, String value) {
		return redisClient.set(keyRoot +"."+ key, value);
	}
	
	public long ttl(String key) {
		String keyResult = keyRoot +"."+ key;
		return redisClient.ttl(keyResult);
	}

	
	public String set(String key, String value, long seconds) {
		String keyResult = keyRoot +"."+ key;
		String result = redisClient.set(keyResult, value);
		redisClient.expire(keyResult, seconds);
		return result;
	}
	
	
	public Boolean exists(final String key) {
		return redisClient.exists(keyRoot +"."+ key);
	}
	
	public Long del(final String key) {
		return redisClient.del(keyRoot +"."+ key);
	}
	
	@Override
	public void finalize() {
		redisClient.close();
	}
}
