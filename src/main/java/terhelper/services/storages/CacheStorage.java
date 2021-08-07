package terhelper.services.storages;

import java.util.ArrayList;
import java.util.Date;

import terhelper.models.Location;
import terhelper.models.Status;
import terhelper.models.Unit;

public final class CacheStorage {
	private static Redis cache = new Redis();
	private static Integer ttlSecDefault = 60 * 60 * 24 * 7;
	
	public static void unitsSave(ArrayList<Unit> units) {
    	if (units.size() > 0) {
    		String key = "unit";
    		String keyUnitsList = key + ".ids";
    		//System.out.println("keyUnitsList: " + keyUnitsList);
    		cache.del(keyUnitsList);
    		cache.set(key + ".count", Integer.toString(units.size()), ttlSecDefault);
    		cache.set(key + ".date-upd", new Date().toString(), ttlSecDefault);
    		for (Unit unitItem : units) {    			
        		String savedUnitIds = cache.get(keyUnitsList);
        		if (null == savedUnitIds || savedUnitIds.isEmpty()) {
        			cache.set(keyUnitsList, unitItem.Id, ttlSecDefault);
        		} else {
        			if (!savedUnitIds.contains(unitItem.Id)) {
            			cache.set(keyUnitsList, savedUnitIds+"_"+unitItem.Id, ttlSecDefault);
            		} 
        		}     		
        		cache.set(key+"."+unitItem.Id+".LocationId", unitItem.LocationId, ttlSecDefault);
        		cache.set(key+"."+unitItem.Id+".Number", unitItem.Number, ttlSecDefault);
        		cache.set(key+"."+unitItem.Id+".StatusId", unitItem.StatusId, ttlSecDefault);
        	}
    	}
	}
	
	
	public static void locationsSave(ArrayList<Location> locations) {
		if (locations.size() > 0) {
    		String key = "loc";
    		for (Location loc : locations) {
        		cache.set(key+"."+loc.Id+".Address", loc.Address, ttlSecDefault);
        	}
    	}
	}
	
	
	public static void statusesSave(ArrayList<Status> statuses) {
		if (statuses.size() > 0) {
    		String key = "status";
    		for (Status statusItem : statuses) {
        		cache.set(key+"."+statusItem.Id+".InternalName", statusItem.InternalName, ttlSecDefault);
        		cache.set(key+"."+statusItem.Id+".StatusId", statusItem.LocalizedName, ttlSecDefault);
        	}
    	}
	}
}
