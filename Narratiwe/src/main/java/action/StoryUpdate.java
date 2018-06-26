package action;

import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import database.DatabaseManager;
import exception.RequestException;
import model.Story;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.BookCateogoriesConstants;
import utility.ModelConstants.StoryTypeConstants;
import utility.ModelConstants.SystemConstants;
import utility.Utils;

public class StoryUpdate implements Action {

	private Datastore datastore;
	private Story story;
	private ObjectId id;		
	private String title;
	private String description;
	private int category;
	private int type;
	private String language;
	private Date date;
	private ObjectId userRequestId;
	private Locale locale;
	
	public StoryUpdate(final String id, final ObjectId userRequestId, final String title, final String description, final String date, final int category, final int type, String language) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = new ObjectId(id);
		this.title		  	= title;
		this.description  	= description;
		if(date != null && !date.isEmpty())
			this.date    		= Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.userRequestId = userRequestId;

		if (category > SystemConstants.CATEOGRIES_NUMBER)
			this.category = BookCateogoriesConstants.GENERAL;
		else
			this.category = category;
		
		if (type != StoryTypeConstants.TYPE_SHARED &&
				type != StoryTypeConstants.TYPE_PRIVATE)
			this.type = StoryTypeConstants.TYPE_SHARED;
		else
			this.type = this.type;
		
		try{
			locale = new Locale(language);
			this.language = locale.getLanguage();
		}catch(Exception e){
			this.language = null;
		}
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		this.story = datastore.get(Story.class, id);

		if(this.story == null)
			throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
		
		if(!story.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		if(this.description != null && !this.description.isEmpty()){
			story.setDescription(this.description);
		}
		
		if(this.title != null && !this.title.isEmpty()){
			story.setTitle(this.title);
			Pattern pattern = Pattern.compile("^" + this.title + "$", Pattern.CASE_INSENSITIVE);
			Query<Story> query = this.datastore.createQuery(Story.class).field(ModelConstants.StoryConstants.FIELD_TITLE).equal(pattern).retrievedFields(true, ModelConstants.StoryConstants.FIELD_ID);
			Story storyTest = query.get();
			if(storyTest != null){
				throw new RequestException(ExceptionConstants.STORY_TITLE_ALREADY_EXIST);
			}
		}
		
		if(this.date != null){
			story.setDate(this.date);
		}
		if(this.language != null && !this.language.isEmpty()){
			story.setLanguage(this.language);	
		}
		
		story.setCategory(this.category);
		story.setType(this.type);
		
		datastore.save(story);
		
		return new JSONObject();
	}

}
