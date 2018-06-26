package action;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import model.Comment;
import model.Like;
import model.Page;
import utility.ExceptionConstants;

public class BookDelete implements Action {

	private Datastore datastore;
	private ObjectId id;

	public BookDelete(String id) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}
		
		this.id = new ObjectId(id);	
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		Book book = this.datastore.get(Book.class, id);
		if(book == null){
			throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
		}

		List<Chapter> chapters = book.getChapters();
		List<Page> pages = new ArrayList<Page>(); 
		
		if(chapters != null && !chapters.isEmpty()){
			for(Chapter chapter : chapters){
				pages = chapter.getPages();
				if(pages != null && !pages.isEmpty()){
					for(Page page : pages){
						this.datastore.delete(page);	
					}
				}
				for(Like like : chapter.getLikes()){
					this.datastore.delete(like);
				}
				for(Comment comment : chapter.getComments()){
					this.datastore.delete(comment);
				}
				this.datastore.delete(chapters);
			}	
		}

		
		for(Comment comment : book.getComments()){
			this.datastore.delete(comment);
		}
		
		for(Like like : book.getLikes()){
			this.datastore.delete(like);
		}
		
		if(datastore.delete(book).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_PAGE_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}