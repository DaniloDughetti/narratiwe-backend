package exception;

public class RequestException extends Exception{
	
	private String message;
	private static final long serialVersionUID = -7824453269908831106L;

	public RequestException() {
		super("Request Exception");
	}
	
	public RequestException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		if(message.isEmpty() || message == null){
			return "generic error";
		}else{
			return message;
		}
	}

}