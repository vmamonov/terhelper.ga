package terhelper.services.repo;

import java.lang.reflect.Field;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import terhelper.models.*;
import terhelper.services.storages.Redis;

public abstract class Repo {
	protected String cacheKeyRoot = "th";
    protected Redis cache;
	
    public void setCache(Redis jedis) {
    	cache = jedis;
    }  
    
//    public void SaveInCache(String key, ArrayList<Location> obj) {
//    	for (Location t : obj) {
//        	for (Field f: t.getClass().getFields()) {
//        		cache.set(key + "." + f.getName(), null);
//        		response.getWriter().append(f.getName());
//        	}
//    	}
//    }
    
    
}
