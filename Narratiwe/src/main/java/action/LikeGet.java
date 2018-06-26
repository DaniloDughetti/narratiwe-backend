package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Like;
import utility.ExceptionConstants;

public class LikeGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public LikeGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		Like like = datastore.get(Like.class, id);

		if (like == null) {
			throw new RequestException(ExceptionConstants.LIKE_NOT_FOUND);
		}
		
		return like.toJSON();
	}

}