package action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import model.Page;
import model.User;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class PageCreate implements Action {
	
	private Datastore datastore;
	private User user;
	private Chapter chapter;
	private String content;
	private Date date;
	private int index; //E' l'ultimo indice
	private ObjectId authorId;
	private ObjectId chapterId;
	
	public PageCreate(ObjectId author, String chapter, String content, String date, int index) throws RequestException, ParseException {
		
		if (author == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}
		
		if (chapter == null || chapter.isEmpty()) {
			throw new RequestException(ExceptionConstants.CHAPTER_NULL);
		}
		
		if (content == null || content.isEmpty()) {
			throw new RequestException(ExceptionConstants.CONTENT_NULL);
		}
		
		if (date == null || date.isEmpty()) {
			throw new RequestException(ExceptionConstants.DATE_NULL);
		}

		if (index == 0) {
			throw new RequestException(ExceptionConstants.INDEX_NULL);
		}

		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.authorId = author;
		this.chapterId = new ObjectId(chapter);
		this.content = content;
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.index = index;
	}

	public JSONObject run() throws Exception {
		
		this.user = this.datastore.get(User.class, authorId);
		if(this.user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
			
		this.chapter = this.datastore.get(Chapter.class, chapterId);
		if(this.chapter == null)
			throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
		Page page = new Page();
		int pageSize = this.content.length();
		List<String> contentList = new ArrayList<String>();
		int i = 0;
		while (i < pageSize) {
			contentList.add(content.substring(i, Math.min(i + SystemConstants.PAGE_LIMIT, content.length())));
		    i += SystemConstants.PAGE_LIMIT;		
		    
		    page = new Page(user, content, date, index);

			datastore.save(page);
					
			this.chapter.addPage(page);
			
			datastore.save(this.chapter);
		}

		
		return page.toJSON();
	}

}
