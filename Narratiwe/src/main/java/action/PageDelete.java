package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Chapter;
import model.Page;
import utility.ExceptionConstants;

public class PageDelete implements Action {

	private Datastore datastore;
	private ObjectId id;
	private ObjectId chapterId;

	public PageDelete(String id, String chapter) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}

		this.id = new ObjectId(id);	
		this.chapterId = new ObjectId(chapter);	
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		Page page = datastore.get(Page.class, id);
		
		if(page == null){
			throw new RequestException(ExceptionConstants.PAGE_NOT_FOUND);
		}
		
		Chapter chapter = this.datastore.get(Chapter.class, chapterId);
		if(chapter != null){
			chapter.removePage(page);
			this.datastore.save(chapter);
		}
		
		if(datastore.delete(page).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_PAGE_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}