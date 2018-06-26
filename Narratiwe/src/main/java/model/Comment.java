package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Comment extends BaseEntity{
	
	
 	@Reference(idOnly = true)
	private User author;
 	
	private String content;
	private Date date;
	
 	@Reference(idOnly = true)	
	private List<Like> likes;
 	
	private int likesNumber;

	public Comment() {
		super();
	}

	public Comment(User author, String content, Date date) {
		super();
		this.author 	= author;
		this.content 	= content;
		this.date 		= date;
		this.likes = new ArrayList<Like>();
		this.likesNumber = 0;
	}
	
	public Comment(User author, String content, Date date, List<Like> likes, int likesNumber) {
		super();
		this.author 	= author;
		this.content 	= content;
		this.date 		= date;
		this.likes = likes;
		this.likesNumber = likesNumber;
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
		
	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public int getLikesNumber() {
		return likesNumber;
	}

	public void setLikesNumber(int likesNumber) {
		this.likesNumber = likesNumber;
	}

	public void addLike(Like like){
		if(like != null){
			this.likes.add(like);
			this.likesNumber++;
		}
	}
	
	public void removeLike(Like like){
		if(like != null){
			this.likes.remove(like);
			if(this.likesNumber > 0){
				this.likesNumber--;
			}
		}
	}
	
	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.CommentConstants.FIELD_CONTENT, content);
			jsonObject.put(ModelConstants.CommentConstants.FIELD_DATE, date);
			
			JSONArray likesJSON = new JSONArray();
			for(Like like : likes){
				JSONObject likeObject = new JSONObject();
				likeObject.put(ModelConstants.BaseConstants.FIELD_ID, like.getId());
				likesJSON.put(likeObject);
			}
 			jsonObject.put(ModelConstants.CommentConstants.FIELD_LIKES, likesJSON);
		
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
		if (!(arg0 instanceof Comment))return false;
		Comment comment = (Comment)arg0;
		return this.id.equals(comment.getId());
	}
}
