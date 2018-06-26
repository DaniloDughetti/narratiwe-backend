package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class ChapterUpdate implements Action {

	private Datastore datastore;
	private Chapter chapter;
	private String id;
	private String title;
	private Date date;
	private int index;

	public ChapterUpdate(final String id, final String title, final String date, final int index) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = id;
		this.title = title;
		if(date != null && !date.isEmpty())
			this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.index = index;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		this.chapter = datastore.get(Chapter.class, new ObjectId(id));

		if(this.chapter == null)
			throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);

		if(this.title != null && !this.title.isEmpty()){
			chapter.setTitle(this.title);
		}
		
		if(this.date != null){
			chapter.setDate(this.date);
		}
		
		if(this.index != 0){
			chapter.setIndex(this.index);
		}
		
		datastore.save(chapter);
		
		return new JSONObject();
	}

}
