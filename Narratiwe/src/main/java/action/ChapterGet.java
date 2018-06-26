package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import utility.ExceptionConstants;

public class ChapterGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public ChapterGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		Chapter chapter = datastore.get(Chapter.class, id);

		if (chapter == null) {
			throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
		}
		
		return chapter.toJSON();
	}

}