package terhelper;

import java.util.HashMap;

public class ResultStatus {
	public static final Integer SUCCESS = 1; 
	public static final Integer ERROR = 0;
	
	private Integer statusCode = ResultStatus.SUCCESS;
	private String message = "";
	private HashMap<String, String> data = new HashMap<String, String>();
	
	public ResultStatus(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public ResultStatus(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public ResultStatus(Integer statusCode, String message, HashMap<String, String> data) {
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	
	public Integer getStatus() {
		return this.statusCode;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public HashMap<String, String> getData() {
		return this.data;
	}
}
