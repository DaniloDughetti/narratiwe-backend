package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Page;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class PageUpdate implements Action {

	private Datastore datastore;
	private Page page;
	private String id;
	private String content;
	private Date date;
	
	public PageUpdate(final String id, final String content, final String date) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = id;
		this.content       	= content;
		if(date != null && !date.isEmpty())
			this.date    		= Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		this.page = datastore.get(Page.class, new ObjectId(id));

		if(this.page == null)
			throw new RequestException(ExceptionConstants.PAGE_NOT_FOUND);

		if(this.content != null && !this.content.isEmpty()){
			page.setContent(this.content);
		}
		
		if(this.date != null){
			page.setDate(this.date);
		}
	
		datastore.save(page);
		
		return new JSONObject();
	}

}
