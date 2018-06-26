package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import utility.ExceptionConstants;

public class BookGet implements Action {

	private Book book;
	private ObjectId id;
	private Datastore datastore;
	
	public BookGet(String id) throws Exception {
		
		datastore = DatabaseManager.getInstance().getDatastore();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = new ObjectId(id);
	}
	
	public JSONObject run() throws Exception {
		
		this.book = datastore.get(Book.class, id);

		if (this.book == null) {
			throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
		}

		return this.book.toJSON();
	}

}