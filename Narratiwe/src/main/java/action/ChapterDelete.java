package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import model.Page;
import utility.ExceptionConstants;

public class ChapterDelete implements Action {

	private Datastore datastore;
	private ObjectId id;
	private ObjectId bookId;

	public ChapterDelete(String id, String book) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}

		this.id = new ObjectId(id);	
		this.bookId = new ObjectId(book);	
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		Chapter chapter = datastore.get(Chapter.class, id);
		
		if(chapter == null){
			throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
		}
		
		Book book = this.datastore.get(Book.class, bookId);
		if(book != null){
			book.removeChapter(chapter);
			this.datastore.save(book);
		}
		
		for(Page page : chapter.getPages()){
			if(datastore.delete(page).getN() == 0){
				throw new RequestException(ExceptionConstants.DELETE_ERROR_PAGE_ID_NOT_FOUND);
			}
		}
		
		if(datastore.delete(chapter).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_CHAPTER_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}