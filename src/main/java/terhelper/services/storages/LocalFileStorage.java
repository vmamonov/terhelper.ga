package terhelper.services.storages;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import terhelper.Env;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;


public class LocalFileStorage implements DataStore<StoredCredential>{
    private File casheFile;
    public LocalFileStorage(String casheDirPath, String casheFileName) throws IOException {
        File casheDir = new File(casheDirPath);
        if (!casheDir.exists() || !casheDir.isDirectory()) {
            casheDir.mkdirs();
        }

        casheFile = new File(casheDirPath + "/" + casheFileName);
        if (!casheFile.canRead()) {
            casheFile.createNewFile();
            casheFile.setReadable(true);
        }
    }

    @Override
    public DataStoreFactory getDataStoreFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsKey(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsValue(StoredCredential v) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> keySet() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<StoredCredential> values() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StoredCredential get(String userId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(casheFile));
        String line = reader.readLine();
        if (null == line) {
            return null;
        }
        StoredCredential sc = new StoredCredential();
        StringTokenizer sTokenizer = new StringTokenizer(line);
        sc.setAccessToken((String) sTokenizer.nextElement());
        sc.setRefreshToken((String) sTokenizer.nextElement());
        sc.setExpirationTimeMilliseconds(Long.parseUnsignedLong((String) sTokenizer.nextElement()));
        return sc;
    }

    @Override
    public DataStore<StoredCredential> set(String string, StoredCredential v) throws IOException {
        System.out.println("ddddddddddddd " + v.getAccessToken());
        FileWriter fw = new FileWriter(casheFile);
        fw.write(v.getAccessToken() + " " + v.getRefreshToken() + " " + v.getExpirationTimeMilliseconds());
        fw.close();
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataStore<StoredCredential> delete(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
