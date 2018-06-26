package action;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Story;
import model.StoryFrame;
import model.User;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class StoryFrameCreate implements Action {
	
	private Datastore datastore;
	private User user;
	private Story story;
	private String content;
	private Date date;
	private ObjectId authorId;
	private ObjectId storyId;
	public StoryFrameCreate(ObjectId author, String story, String content, String date) throws RequestException, ParseException {
		
		if (author == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}
		
		if (story == null || story.isEmpty()) {
			throw new RequestException(ExceptionConstants.CHAPTER_NULL);
		}
		
		if (content == null || content.isEmpty()) {
			throw new RequestException(ExceptionConstants.CONTENT_NULL);
		}
		
		if (content.length() > SystemConstants.PAGE_LIMIT){
			throw new RequestException(ExceptionConstants.CONTENT_OVERFLOW);
		}
		
		if (date == null || date.isEmpty()) {
			throw new RequestException(ExceptionConstants.DATE_NULL);
		}

		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.authorId = author;
		this.storyId = new ObjectId(story);
		this.content = content;
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);

	}

	public JSONObject run() throws Exception {
		
		this.user = this.datastore.get(User.class, authorId);
		if(this.user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
			
		this.story = this.datastore.get(Story.class, storyId);
		if(this.story == null)
			throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
		
		StoryFrame storyFrame = new StoryFrame();
		   
	    storyFrame = new StoryFrame(user, content, date);

		datastore.save(storyFrame);
		this.story.addStoryFrame(storyFrame);
		datastore.save(this.story);
				
		return storyFrame.toJSON();
	}

}
