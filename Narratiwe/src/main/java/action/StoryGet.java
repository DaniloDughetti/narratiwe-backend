package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Story;
import utility.ExceptionConstants;

public class StoryGet implements Action {

	private Story story;
	private ObjectId id;
	private Datastore datastore;
	
	public StoryGet(String id) throws Exception {
		
		datastore = DatabaseManager.getInstance().getDatastore();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = new ObjectId(id);
	}
	
	public JSONObject run() throws Exception {
		
		this.story = datastore.get(Story.class, id);

		if (this.story == null) {
			throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
		}

		return this.story.toJSON();
	}

}