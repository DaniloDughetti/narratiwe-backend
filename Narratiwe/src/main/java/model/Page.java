package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;
import utility.ModelConstants.PageStatusConstants;

public class Page extends BaseEntity{

 	@Reference(idOnly = true)
	private User author;
 	
	private String content;
	private Date date;
	private int status;

 	@Reference(idOnly = true)	
	private List<Like> likes;
 	
	@Reference(idOnly = true)	
	private List<Comment> comments;
 	
	public Page() {
		super();
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.status = PageStatusConstants.NOT_LOCKED;
	
	}
	
	public Page(User author, String content, Date date) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = PageStatusConstants.NOT_LOCKED;
	}



	public Page(User author, String content, Date date, int status) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = status;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
	
	}

	public Page(User author, String content, Date date, int status, List<Like> likes,
			List<Comment> comments) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = status;
		this.likes = likes;
		this.comments = comments;
	
	}



	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addLike(Like like){
		if(like != null){
			this.likes.add(like);
		}
	}
	
	public void removeLike(Like like){
		if(like != null){
			this.likes.remove(like);
		}
	}
	
	public void addComment(Comment comment){
		if(comment != null){
			this.comments.add(comment);	
		}
	}
	
	public void removeComment(Comment comment){
		if(comment != null){
			this.comments.remove(comment);
		}
	}

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.PageConstants.FIELD_AUTHOR, author);
			jsonObject.put(ModelConstants.PageConstants.FIELD_CONTENT, content);
			jsonObject.put(ModelConstants.PageConstants.FIELD_DATE, date);
			jsonObject.put(ModelConstants.PageConstants.FIELD_STATUS, status);
 			JSONArray likesJSON = new JSONArray();
			for(Like like : likes){
				JSONObject likeObject = new JSONObject();
				likeObject.put(ModelConstants.BaseConstants.FIELD_ID, like.getId());
				likesJSON.put(likeObject);
			}
 			jsonObject.put(ModelConstants.BookConstants.FIELD_LIKES, likesJSON);
 			jsonObject.put(ModelConstants.LikeConstants.FIELD_NUMBER, this.likes.size());
 			
 			JSONArray commentsJSON = new JSONArray();
			for(Comment comment : comments){
				JSONObject commentObject = new JSONObject();
				commentObject.put(ModelConstants.BaseConstants.FIELD_ID, comment.getId());
				commentsJSON.put(commentObject);
			}
 			jsonObject.put(ModelConstants.BookConstants.FIELD_COMMENTS, commentsJSON);
 			jsonObject.put(ModelConstants.CommentConstants.FIELD_NUMBER, this.comments.size());
 			
			return jsonObject;
		} catch (JSONException e) {
			throw e;
		}
	}
	
	@Override
	public String toString() {
		try {
			return this.toJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toString()";
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) return false;
		if (!(arg0 instanceof Page))return false;
		Page page = (Page)arg0;
		return this.id.equals(page.getId());
	}
	
}
