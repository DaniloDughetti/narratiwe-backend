package action;

import java.text.ParseException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import model.Rating;
import model.StoryFrame;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.ModelIdConstants;
import utility.ModelConstants.SystemConstants;
import utility.Utils;

public class RatingCreate implements Action {
	
	private Datastore datastore;
	private Date date;
	private ObjectId authorId;
	private ObjectId targetId;
	private int targetModel;
	private int voteCreativity;
	private int voteCharacter;
	private int voteIncipit;
	private int voteLanguage;
	private int voteGeneral;
	
	public RatingCreate(ObjectId author, String target, String targetId, String date, int voteCreativity, int voteCharacter, int voteIncipit, int voteLanguage, int voteGeneral) throws RequestException, ParseException {
		
		if (author == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}

		if (target == null || target.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_NULL);
		}
		
		if (targetId == null || targetId.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
				
		if (date == null || date.isEmpty()) {
			throw new RequestException(ExceptionConstants.DATE_NULL);
		}

		if(voteCreativity < SystemConstants.RATING_LIMIT_LOWER || voteCreativity > SystemConstants.RATING_LIMIT_UPPER ||
			voteCharacter < SystemConstants.RATING_LIMIT_LOWER || voteCharacter > SystemConstants.RATING_LIMIT_UPPER ||
			voteIncipit < SystemConstants.RATING_LIMIT_LOWER || voteIncipit > SystemConstants.RATING_LIMIT_UPPER ||
			voteLanguage < SystemConstants.RATING_LIMIT_LOWER || voteLanguage > SystemConstants.RATING_LIMIT_UPPER ||
			voteGeneral < SystemConstants.RATING_LIMIT_LOWER || voteGeneral > SystemConstants.RATING_LIMIT_UPPER){
				throw new RequestException(ExceptionConstants.RATING_OUT_OF_RANGE);
		}

		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.authorId = author;
		
		if(target.toLowerCase().equals(ModelConstants.PageConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_CHAPTER;
		}else if(target.toLowerCase().equals(ModelConstants.StoryFrameConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_STORYFRAME;
		}else{
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		this.voteCreativity = voteCreativity;
		this.voteCharacter = voteCharacter;
		this.voteIncipit = voteIncipit;
		this.voteLanguage = voteLanguage;
		this.voteGeneral = voteGeneral;
		
		this.targetId = new ObjectId(targetId);
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
	}

	public JSONObject run() throws Exception {
		
		User user = this.datastore.get(User.class, authorId);
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
			
		Rating rating = new Rating(user, date, voteCreativity, voteCharacter, voteIncipit, voteLanguage, voteGeneral);
		datastore.save(rating);
		
		switch(targetModel){
			case ModelIdConstants.MODEL_CHAPTER:{
				Chapter chapter = datastore.get(Chapter.class, targetId);
				if(chapter == null){
					throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
				}
				chapter.addRating(rating);
				datastore.save(chapter);
				break;
			}
			case ModelIdConstants.MODEL_STORYFRAME:{
				StoryFrame storyFrame = datastore.get(StoryFrame.class, targetId);
				if(storyFrame == null){
					throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
				}
				storyFrame.addRating(rating);
				datastore.save(storyFrame);
				break;
			}
			default:{
				throw new RequestException(ExceptionConstants.RATING_NOT_FOUND);
			}
		}
				
		return rating.toJSON();
	}

}
