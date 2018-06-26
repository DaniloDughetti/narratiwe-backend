package action;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import model.Rating;
import model.StoryFrame;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.ModelIdConstants;

public class RatingGetAll implements Action {

	private ObjectId targetId;
	private int targetModel;
	private Datastore datastore;
	
	public RatingGetAll(String target, String targetId) throws Exception {
		
		if (target == null || target.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_NULL);
		}
		
		if (targetId == null || targetId.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		if(target.toLowerCase().equals(ModelConstants.PageConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_CHAPTER;
		}else if(target.toLowerCase().equals(ModelConstants.StoryFrameConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_STORYFRAME;
		}else{
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		
		this.targetId = new ObjectId(targetId);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		JSONObject result = new JSONObject();
		JSONArray ratingList = new JSONArray();
		
		switch(targetModel){
		case ModelIdConstants.MODEL_CHAPTER:{
			Chapter chapter = datastore.get(Chapter.class, targetId);
			if(chapter == null){
				throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
			}
			for(Rating rating : chapter.getRatings()){
				ratingList.put(rating.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_RATINGS, ratingList);
			result.put(ModelConstants.RatingConstants.FIELD_NUMBER, ratingList.length());
			break;
		}
		case ModelIdConstants.MODEL_STORYFRAME:{
			StoryFrame storyFrame = datastore.get(StoryFrame.class, targetId);
			if(storyFrame == null){
				throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
			}
			for(Rating rating : storyFrame.getRatings()){
				ratingList.put(rating.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_RATINGS, ratingList);
			result.put(ModelConstants.RatingConstants.FIELD_NUMBER, ratingList.length());
			break;
		}
		default:{
			throw new RequestException(ExceptionConstants.RATING_NOT_FOUND);
		}
	}
		
		return result;
	}

}