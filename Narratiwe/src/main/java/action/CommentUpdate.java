package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Comment;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class CommentUpdate implements Action {

	private Datastore datastore;
	private ObjectId id;
	private String content;
	private Date date;
	private ObjectId userRequestId;
	
	public CommentUpdate(final String id, final ObjectId userRequestId, final String content, final String date) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		if(date != null && !date.isEmpty())
			this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.id = new ObjectId(id);
		this.content = content;
		this.userRequestId = userRequestId;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		Comment comment = datastore.get(Comment.class, id);

		if(comment == null)
			throw new RequestException(ExceptionConstants.COMMENT_NOT_FOUND);
		
		if(!comment.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		if(this.content != null && !this.content.isEmpty()){
			comment.setContent(this.content);
		}
		
		if(this.date != null){
			comment.setDate(this.date);
		}
		
		datastore.save(comment);
		
		return new JSONObject();
	}

}
