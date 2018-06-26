package action;

import java.text.ParseException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class BookCreate implements Action {
	
	private Datastore datastore;
	private ObjectId author;
	private Book book;
	private String title;
	private String description;
	private Date date;
	
	public BookCreate(ObjectId author, String title, String description, String date) throws RequestException, ParseException {
		
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
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.title = title;
		this.author = author;
		this.description = description;
		this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
	}

	public JSONObject run() throws Exception {
		

		User user = this.datastore.get(User.class, author);
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		
		Book bookTest = this.datastore.find(Book.class).field(ModelConstants.BookConstants.FIELD_TITLE).equal(this.title).get();
		if(bookTest != null){
			throw new RequestException(ExceptionConstants.BOOK_TITLE_ALREADY_EXIST);
		}
		this.book = new Book(user, title, description, date);
		System.out.println(this.book.toString());
		this.datastore.save(book);
		
		return this.book.toJSON();
	}

}
