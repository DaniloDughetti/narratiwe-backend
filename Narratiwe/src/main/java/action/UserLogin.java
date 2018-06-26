package action;

import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import manager.SessionManager;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;

public class UserLogin implements Action {

	private User user;
	private Datastore datastore;
	private SessionManager sessionManager;
	
	public UserLogin(String email, String password, SessionManager sessionManger) throws Exception {
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.sessionManager = sessionManger;
		
		if (email == null || email.isEmpty()) {
			throw new RequestException(ExceptionConstants.EMAIL_NULL);
		}
		if (password == null || password.isEmpty()) {
			throw new RequestException(ExceptionConstants.PASSWORD_NULL);
		}
		

		User user = this.datastore.find(User.class).field(ModelConstants.UserConstants.FIELD_EMAIL).equal(email).get();
				
		
		if (user == null) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}else{
			if(!user.getPassword().equals(password)){
				throw new RequestException(ExceptionConstants.PASSWORD_NOT_MATCHING);		
			}
			this.user = user;
		}
	}
	
	public JSONObject run() throws Exception {
		return sessionManager.generateSessionLogin(user);
	}

}