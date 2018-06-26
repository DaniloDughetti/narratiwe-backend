package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import utility.ExceptionConstants;
import utility.ModelConstants.ChapterConstants;
import utility.ModelConstants.SystemConstants;
import utility.Utils;

public class BookUpdate implements Action {

	private Datastore datastore;
	private Book book;
	private ObjectId id;		
	private String title;
	private String description;
	private Date date;
	private int lastIndex;
	private List<ObjectId> chaptersId;

	public BookUpdate(final String id, final String title, final String description, final String date, final int lastIndex, final JSONArray chapters) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = new ObjectId(id);
		this.title		  	= title;
		this.description  	= description;
		if(date != null && !date.isEmpty())
			this.date    		= Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.lastIndex   	= lastIndex;
		this.chaptersId = new ArrayList<ObjectId>();
		
		if (chapters.length() != 0)
			for(int i = 0; i < chapters.length(); i++)
				this.chaptersId.add(new ObjectId(chapters.getString(i)));
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		this.book = datastore.get(Book.class, id);

		if(this.book == null)
			throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
				
		if(this.description != null && !this.description.isEmpty()){
			book.setDescription(this.description);
		}
		
		if(this.title != null && !this.title.isEmpty()){
			book.setTitle(this.title);
		}
		
		if(this.date != null){
			book.setDate(this.date);
		}
		
		if(this.lastIndex != 0){
			book.setLastIndex(this.lastIndex);
		}
		
		if(this.chaptersId != null && !this.chaptersId.isEmpty()){
			List<Chapter> chapters = new ArrayList<Chapter>();
			chapters = datastore.createQuery(Chapter.class).field(ChapterConstants.FIELD_ID).hasAnyOf(chaptersId).asList();
			book.setChapters(chapters);
		}
		
		datastore.save(book);
		
		return new JSONObject();
	}

}
