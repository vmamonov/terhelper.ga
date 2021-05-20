package terhelper.services.repo;

import terhelper.services.storages.Redis;

public abstract class Repo {
	protected String cacheKeyRoot = "th";
    protected Redis cache;
	
    public void setCache(Redis jedis) {
    	cache = jedis;
    }      
}
