package action;

import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Comment;
import model.Like;
import model.Story;
import model.StoryFrame;
import utility.ExceptionConstants;

public class StoryDelete implements Action {

	private Datastore datastore;
	private ObjectId id;
	private ObjectId userRequestId;

	public StoryDelete(String id, ObjectId userRequestId) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}
		
		this.id = new ObjectId(id);
		this.userRequestId = userRequestId;
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		Story story = this.datastore.get(Story.class, id);
		
		if(story == null){
			throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
		}
		
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!story.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}



		List<StoryFrame> storyFrames = story.getStoryFrames();
		
		for(StoryFrame storyFrame: storyFrames){

			for(Like like : storyFrame.getLikes()){
				this.datastore.delete(like);
			}
			for(Comment comment : storyFrame.getComments()){
				this.datastore.delete(comment);
			}
			this.datastore.delete(storyFrame);		
		}
		
		for(Like like : story.getLikes()){
			this.datastore.delete(like);
		}
		for(Comment comment : story.getComments()){
			this.datastore.delete(comment);
		}

		if(datastore.delete(story).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_STORY_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}