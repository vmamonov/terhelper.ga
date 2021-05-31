package terhelper.services.storages;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedisTest {

	private Redis redis = new Redis();
	private String key = "jutest";
	private String val = "hello";
	
	@Test
	public void setGetDel_Test() {
		redis.set(key, val);
		assertEquals(val, redis.get(key));
		redis.del(key);
		assertEquals(null, redis.get(key));
	}
	
	@Test
	public void expire_Test() throws InterruptedException {
		long ttlSec = 1;
		redis.set(key, val, ttlSec);
		assertEquals(val, redis.get(key));
		Thread.sleep((ttlSec * 1000)+1000);
		assertFalse(redis.exists(key));
	}
}
