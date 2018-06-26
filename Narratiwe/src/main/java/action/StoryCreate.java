package action;

import java.text.ParseException;
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
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.Utils;
import utility.ModelConstants.BookCateogoriesConstants;
import utility.ModelConstants.StoryTypeConstants;
import utility.ModelConstants.SystemConstants;

public class StoryCreate implements Action {
	
	private Datastore datastore;
	private ObjectId author;
	private Story story;
	private String title;
	private String description;
	private int category;
	private int type;
	private String language;
	private Date date;
	private Locale locale;
	
	public StoryCreate(ObjectId author, String title, String description, String date, int category, int type, String language) throws RequestException, ParseException {
		
		if (author == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}	
		
		if (title == null || title.isEmpty()) {
			throw new RequestException(ExceptionConstants.TITLE_NULL);
		}	
		
		if (description == null || description.isEmpty()) {
			throw new RequestException(ExceptionConstants.DESCRIPTION_NULL);
		}	
		
		if (date == null || date.isEmpty()) {
			throw new RequestException(ExceptionConstants.DATE_NULL);
		}

		if (category > SystemConstants.CATEOGRIES_NUMBER)
			this.category = BookCateogoriesConstants.GENERAL;
		else
			this.category = category;

		if (type != StoryTypeConstants.TYPE_SHARED &&
				type != StoryTypeConstants.TYPE_PRIVATE)
			this.type = StoryTypeConstants.TYPE_SHARED;
		else
			this.type = this.type;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.title = title.trim();
		this.author = author;
		this.description = description.trim();
		Utils utils = Utils.getInstance(SystemConstants.DATE_HOUR);
		this.date = utils.parseDate(date);
		locale = utils.parseLocale(language);
		  if (utils.isValid(locale)) {
				this.language = locale.getLanguage();
		  } else {
				this.language = locale.getDefault().getLanguage();
		  }

	}

	public JSONObject run() throws Exception {
		

		User user = this.datastore.get(User.class, author);
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		Pattern pattern = Pattern.compile("^" + this.title + "$", Pattern.CASE_INSENSITIVE);
		Query<Story> query = this.datastore.createQuery(Story.class).field(ModelConstants.StoryConstants.FIELD_TITLE).equal(pattern).retrievedFields(true, ModelConstants.StoryConstants.FIELD_ID);
		Story storyTest = query.get();
		if(storyTest != null){
			throw new RequestException(ExceptionConstants.STORY_TITLE_ALREADY_EXIST);
		}

		this.story = new Story(user, title, description, date, category, type, language);

		this.datastore.save(story);
		
		return this.story.toJSON();
	}

}
