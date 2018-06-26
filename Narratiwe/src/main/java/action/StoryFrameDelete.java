package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Story;
import model.StoryFrame;
import utility.ExceptionConstants;

public class StoryFrameDelete implements Action {

	private Datastore datastore;
	private ObjectId id;
	private ObjectId storyId;
	private ObjectId userRequestId;

	public StoryFrameDelete(final String id, final ObjectId userRequestId, final String story) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
		}

		this.id = new ObjectId(id);	
		this.storyId = new ObjectId(story);	
		this.userRequestId = userRequestId;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		StoryFrame storyFrame = datastore.get(StoryFrame.class, id);
		
		if(storyFrame == null){
			throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
		}
		
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!storyFrame.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		Story story = this.datastore.get(Story.class, storyId);
		if(story != null){
			story.removeStoryFrame(storyFrame);
			this.datastore.save(story);
		}
		
		if(datastore.delete(storyFrame).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_STORYFRAME_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}