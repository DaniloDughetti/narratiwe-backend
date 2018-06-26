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
import model.User;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class ChapterCreate implements Action {
	
	private Datastore datastore;
	private User user;
	private Book book;
	private String title;
	private Date date;
	private int index; //E' l'ultimo indice
	private ObjectId authorId;
	private ObjectId bookId;
	
	public ChapterCreate(ObjectId author, String book, String title, String date, int index) throws RequestException, ParseException {
		
		if (author == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}
		
		if (book == null || book.isEmpty()) {
			throw new RequestException(ExceptionConstants.BOOK_NULL);
		}
		
		if (title == null || title.isEmpty()) {
			throw new RequestException(ExceptionConstants.TITLE_NULL);
		}
		
		if (date == null || date.isEmpty()) {
			throw new RequestException(ExceptionConstants.DATE_NULL);
		}

		if (index == 0) {
			throw new RequestException(ExceptionConstants.INDEX_NULL);
		}

		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.authorId = author;
		this.bookId = new ObjectId(book);
		this.title = title;
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.index = index;
	}

	public JSONObject run() throws Exception {
		
		this.user = this.datastore.get(User.class, authorId);
		if(this.user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
			
		this.book = this.datastore.get(Book.class, bookId);
		
		if(this.book == null)
			throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
		
		Chapter chapter = new Chapter(this.user, this.title, this.date, this.index);
		
		datastore.save(chapter);
			
		this.book.addChapter(chapter);
			
		datastore.save(this.book);
		
		return chapter.toJSON();
	}

}
