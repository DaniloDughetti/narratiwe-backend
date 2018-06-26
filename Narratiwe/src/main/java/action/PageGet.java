package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Page;
import utility.ExceptionConstants;

public class PageGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public PageGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		Page page = datastore.get(Page.class, id);

		if (page == null) {
			throw new RequestException(ExceptionConstants.PAGE_NOT_FOUND);
		}
		
		return page.toJSON();
	}

}