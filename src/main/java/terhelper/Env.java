package terhelper;

public final class Env {
	public static final String PROD = "prod";
	public static final String TEST = "test";
	public static final String APP_PATH_ROOT = Env.class.getProtectionDomain().getCodeSource().getLocation().getFile();
}