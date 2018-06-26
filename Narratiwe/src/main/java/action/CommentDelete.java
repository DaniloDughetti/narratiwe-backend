package action;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Book;
import model.Chapter;
import model.Comment;
import model.Story;
import model.StoryFrame;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.ModelConstants.ModelIdConstants;

public class CommentDelete implements Action {

	private Datastore datastore;
	private ObjectId id;
	private ObjectId targetId;
	private int targetModel;
	private ObjectId userRequestId;

	public CommentDelete(String id, final ObjectId userRequestId, String target, String targetId) throws Exception {
		super();
		
		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		}
		
		if (target == null || target.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_NULL);
		}
		
		if (targetId == null || targetId.isEmpty()) {
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		if(target.toLowerCase().equals(ModelConstants.PageConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_CHAPTER;
		}else if(target.toLowerCase().equals(ModelConstants.BookConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_BOOK;
		}else if(target.toLowerCase().equals(ModelConstants.StoryConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_STORY;
		}else if(target.toLowerCase().equals(ModelConstants.StoryFrameConstants.ENTITY)){
			targetModel = ModelIdConstants.MODEL_STORYFRAME;
		}else{
			throw new RequestException(ExceptionConstants.TARGET_ID_NULL);
		}
		
		this.targetId = new ObjectId(targetId);
		this.id = new ObjectId(id);	
		this.userRequestId = userRequestId;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}

	public JSONObject run() throws Exception {
	
		Comment comment = datastore.get(Comment.class, id);
		if(comment == null)
			throw new RequestException(ExceptionConstants.COMMENT_NOT_FOUND);
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!comment.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		switch(targetModel){
		case ModelIdConstants.MODEL_CHAPTER:{
			Chapter chapter = datastore.get(Chapter.class, targetId);
			if(chapter == null){
				throw new RequestException(ExceptionConstants.CHAPTER_NOT_FOUND);
			}
			chapter.removeComment(comment);
			datastore.save(chapter);
			break;
		}
		case ModelIdConstants.MODEL_BOOK:{
			Book book = datastore.get(Book.class, targetId);
			if(book == null){
				throw new RequestException(ExceptionConstants.BOOK_NOT_FOUND);
			}
			book.removeComment(comment);
			datastore.save(book);
			break;
		}
		case ModelIdConstants.MODEL_STORY:{
			Story story = datastore.get(Story.class, targetId);
			if(story == null){
				throw new RequestException(ExceptionConstants.STORY_NOT_FOUND);
			}
			story.removeComment(comment);
			datastore.save(story);
			break;
		}
		case ModelIdConstants.MODEL_STORYFRAME:{
			StoryFrame storyFrame = datastore.get(StoryFrame.class, targetId);
			if(storyFrame == null){
				throw new RequestException(ExceptionConstants.STORYFRAME_NOT_FOUND);
			}
			storyFrame.removeComment(comment);
			datastore.save(storyFrame);
			break;
		}
		default:{
			throw new RequestException(ExceptionConstants.COMMENT_NOT_FOUND);
		}
		}
		
		if(datastore.delete(comment).getN() == 0){
			throw new RequestException(ExceptionConstants.DELETE_ERROR_COMMENT_ID_NOT_FOUND);
		}
		
		return new JSONObject();
	}
}















