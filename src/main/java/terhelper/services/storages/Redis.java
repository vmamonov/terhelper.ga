package terhelper.services.storages;

import redis.clients.jedis.Jedis;

public class Redis {
	private Jedis self;
	private String keyRoot = "th";

	public Redis() {
		self = new Jedis("localhost", 6379);
	}
	
	public String get(String key) {
		return self.get(keyRoot +"." + key);
	}
	
	
	public String set(String key, String value) {
		return self.set(keyRoot +"."+ key, value);
	}

	
	public String set(String key, String value, long seconds) {
		String keyResult = keyRoot +"."+ key;
		String result = self.set(keyResult, value);
		self.expire(keyResult, seconds);
		return result;
	}
	
	
	public Boolean exists(final String key) {
		return self.exists(keyRoot +"."+ key);
	}
	
	public Long del(final String key) {
		return self.del(keyRoot +"."+ key);
	}
}
