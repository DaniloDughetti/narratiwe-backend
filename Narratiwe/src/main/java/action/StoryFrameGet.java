package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.StoryFrame;
import utility.ExceptionConstants;

public class StoryFrameGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public StoryFrameGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		StoryFrame storyFrame = datastore.get(StoryFrame.class, id);

		if (storyFrame == null) {
			throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
		}
		
		return storyFrame.toJSON();
	}

}