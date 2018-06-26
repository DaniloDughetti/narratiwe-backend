package action;

import java.text.ParseException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import model.Like;
import model.Story;
import model.StoryFrame;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.ModelIdConstants;
import utility.ModelConstants.SystemConstants;
import utility.Utils;

public class LikeCreate implements Action {
	
	private Datastore datastore;
	private Date date;
	private ObjectId authorId;
	private ObjectId targetId;
	private int targetModel;
	
	public LikeCreate(ObjectId author, String target, String targetId, String date) throws RequestException, ParseException {
		
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

		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.authorId = author;
		
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
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
	}

	public JSONObject run() throws Exception {
		
		User user = this.datastore.get(User.class, authorId);
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
			
		Like like = new Like(user, date);
		datastore.save(like);
		
		switch(targetModel){
			case ModelIdConstants.MODEL_CHAPTER:{
				Chapter chapter = datastore.get(Chapter.class, targetId);
				if(chapter == null){
					throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
				}
				chapter.addLike(like);
				datastore.save(chapter);
				break;
			}
			case ModelIdConstants.MODEL_BOOK:{
				Book book = datastore.get(Book.class, targetId);
				if(book == null){
					throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
				}
				book.addLike(like);
				datastore.save(book);
				break;
			}
			case ModelIdConstants.MODEL_STORY:{
				Story story = datastore.get(Story.class, targetId);
				if(story == null){
					throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
				}
				story.addLike(like);
				datastore.save(story);
				break;
			}
			case ModelIdConstants.MODEL_STORYFRAME:{
				StoryFrame storyFrame = datastore.get(StoryFrame.class, targetId);
				if(storyFrame == null){
					throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
				}
				storyFrame.addLike(like);
				datastore.save(storyFrame);
				break;
			}
			default:{
				throw new RequestException(ExceptionConstants.LIKE_NOT_FOUND);
			}
		}
				
		return like.toJSON();
	}

}
