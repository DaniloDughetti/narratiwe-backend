package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Comment;
import utility.ExceptionConstants;

public class CommentGet implements Action {

	private ObjectId id;
	private Datastore datastore;
	
	public CommentGet(String id) throws Exception {
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		
		this.id = new ObjectId(id);
		this.datastore = DatabaseManager.getInstance().getDatastore();
		
	}

	
	public JSONObject run() throws Exception {
		
		Comment comment = datastore.get(Comment.class, id);

		if (comment == null) {
			throw new RequestException(ExceptionConstants.COMMENT_NOT_FOUND);
		}
		
		return comment.toJSON();
	}

}