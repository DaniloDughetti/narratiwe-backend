package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.StoryFrame;
import utility.ExceptionConstants;
import utility.ModelConstants.SystemConstants;
import utility.Utils;

public class StoryFrameUpdate implements Action {

	private Datastore datastore;
	private StoryFrame storyFrame;
	private String id;
	private String content;
	private Date date;
	private int status;
	private ObjectId userRequestId;

	public StoryFrameUpdate(final String id, final ObjectId userRequestId, final String content, final String date, int status) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		this.id = id;

		this.content = content;

		if(date != null && !date.isEmpty())
			this.date    		= Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.status = status;
		
		this.userRequestId = userRequestId;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		this.storyFrame = datastore.get(StoryFrame.class, new ObjectId(id));

		if(this.storyFrame == null)
			throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
		
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!storyFrame.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		if(this.content != null && !this.content.isEmpty()){
			if (this.content.length() > SystemConstants.PAGE_LIMIT){
				throw new RequestException(ExceptionConstants.CONTENT_OVERFLOW);
			}
			storyFrame.setContent(this.content);
		}
		
		if(this.date != null){
			storyFrame.setDate(this.date);
		}
		
		if(this.status >= 0 && this.status <= 1){
			storyFrame.setStatus(status);
		}
		
		datastore.save(storyFrame);
		
		return new JSONObject();
	}

}
