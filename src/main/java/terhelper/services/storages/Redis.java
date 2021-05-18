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
//		System.out.println("res " + res);
//		return res;
	}
	
	public Long exists(final String... keys) {
		return self.exists(keys);
	}
}
