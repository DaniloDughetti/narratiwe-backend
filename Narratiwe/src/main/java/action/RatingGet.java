package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Like;
import model.Rating;
import utility.ExceptionConstants;

public class RatingGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public RatingGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		Rating rating = datastore.get(Rating.class, id);

		if (rating == null) {
			throw new RequestException(ExceptionConstants.RATING_NOT_FOUND);
		}
		
		return rating.toJSON();
	}

}