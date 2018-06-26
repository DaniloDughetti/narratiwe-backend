package action;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import model.Like;
import model.Story;
import model.StoryFrame;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.ModelIdConstants;

public class LikeGetAll implements Action {

	private ObjectId targetId;
	private int targetModel;
	private Datastore datastore;
	
	public LikeGetAll(String target, String targetId) throws Exception {
		
		if (target == null || target.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_NULL);
		}
		
		if (targetId == null || targetId.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		if(target.toLowerCase().equals(ModelConstants.PageConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_CHAPTER;
		}else if(target.toLowerCase().equals(ModelConstants.BookConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_BOOK;
		}else if(target.toLowerCase().equals(ModelConstants.StoryConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_STORY;
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
		JSONArray likesList = new JSONArray();
		
		switch(targetModel){
		case ModelIdConstants.MODEL_CHAPTER:{
			Chapter chapter = datastore.get(Chapter.class, targetId);
			if(chapter == null){
				throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
			}
			for(Like like : chapter.getLikes()){
				likesList.put(like.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_LIKES, likesList);
			result.put(ModelConstants.LikeConstants.FIELD_NUMBER, likesList.length());
			break;
		}
		case ModelIdConstants.MODEL_BOOK:{
			Book book = datastore.get(Book.class, targetId);
			if(book == null){
				throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
			}
			for(Like like : book.getLikes()){
				likesList.put(like.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_LIKES, likesList);
			result.put(ModelConstants.LikeConstants.FIELD_NUMBER, likesList.length());
			break;
		}
		case ModelIdConstants.MODEL_STORY:{
			Story story = datastore.get(Story.class, targetId);
			if(story == null){
				throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
			}
			for(Like like : story.getLikes()){
				likesList.put(like.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_LIKES, likesList);
			result.put(ModelConstants.LikeConstants.FIELD_NUMBER, likesList.length());
			break;
		}
		case ModelIdConstants.MODEL_STORYFRAME:{
			StoryFrame storyFrame = datastore.get(StoryFrame.class, targetId);
			if(storyFrame == null){
				throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
			}
			for(Like like : storyFrame.getLikes()){
				likesList.put(like.toJSON());
			}
			result.put(ModelConstants.BaseConstants.FIELD_LIKES, likesList);
			result.put(ModelConstants.LikeConstants.FIELD_NUMBER, likesList.length());
			break;
		}
		default:{
			throw new RequestException(ExceptionConstants.LIKE_NOT_FOUND);
		}
	}
		
		return result;
	}

}