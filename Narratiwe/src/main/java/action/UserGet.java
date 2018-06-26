package action;

import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;

public class UserGet implements Action {

	private User user;
	private String email;
	private Datastore datastore;
	
	public UserGet(String email) throws Exception {
		
		datastore = DatabaseManager.getInstance().getDatastore();
		
		if (email == null || email.isEmpty()) {
			throw new RequestException(ExceptionConstants.EMAIL_NULL);
		}
		
		this.email = email;
	}
	
	public JSONObject run() throws Exception {
		
		this.user = this.datastore.find(User.class).field(ModelConstants.UserConstants.FIELD_EMAIL).equal(email).get();

		if (this.user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);

		return user.toJSON();
	}

}