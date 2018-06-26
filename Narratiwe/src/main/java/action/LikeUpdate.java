package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Like;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class LikeUpdate implements Action {

	private Datastore datastore;
	private ObjectId id;
	private Date date;
	private ObjectId userRequestId;
	 
	public LikeUpdate(final String id, final ObjectId userRequestId, final String date) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		if(date != null && !date.isEmpty())
			this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.id = new ObjectId(id);
		this.userRequestId = userRequestId;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		Like like = datastore.get(Like.class, id);

		if(like == null)
			throw new RequestException(ExceptionConstants.LIKE_NOT_FOUND);
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!like.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		if(this.date != null){
			like.setDate(this.date);
		}
		
		
		datastore.save(like);
		
		return new JSONObject();
	}

}
