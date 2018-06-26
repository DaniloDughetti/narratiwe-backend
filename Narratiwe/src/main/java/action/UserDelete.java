package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import database.DatabaseManager;
import exception.RequestException;
import model.Session;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.SessionConstants;

public class UserDelete implements Action {

	private Datastore datastore;
	private ObjectId id;

	public UserDelete(ObjectId id) throws Exception {
		super();
		
		if (id == null) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}
		
		this.id = id;
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
		//Viene cancellata solo la sessione e lo status messo a 1
		User user = this.datastore.get(User.class, id);
		
		user.setStatus(ModelConstants.UserStatusConstants.DELETED);
		this.datastore.save(user);
				
		Query<Session> deleteQuerySession = datastore.createQuery(Session.class).field(SessionConstants.FIELD_USER).equal(user.getId());
		if(datastore.delete(deleteQuerySession).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_USER_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}