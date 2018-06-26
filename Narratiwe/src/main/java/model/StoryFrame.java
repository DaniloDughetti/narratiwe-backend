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

public class StoryFrame extends BaseEntity{
	
  	@Reference(idOnly = true)
	private User author;
 	
	private String content;

	private Date date;
	private int status;

 	@Reference(idOnly = true)	
	private List<Like> likes;
 	
	@Reference(idOnly = true)	
	private List<Comment> comments;


	@Reference(idOnly = true)	
	private List<Rating> ratings;
 	
	public StoryFrame() {
		super();
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.status = PageStatusConstants.NOT_LOCKED;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	}
	
	public StoryFrame(User author, String content, Date date) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = PageStatusConstants.NOT_LOCKED;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	}



	public StoryFrame(User author, String content, Date date, String language, int status) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = status;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	
	}

	public StoryFrame(User author, String content, Date date, int status, List<Like> likes,
			List<Comment> comments, List<Rating> ratings) {
		super();
		this.author = author;
		this.content = content;
		this.date = date;
		this.status = status;
		this.likes = likes;
		this.comments = comments;
		this.ratings = ratings;
	
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

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
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

	public void addRating(Rating rating){
		if(rating != null){
			this.ratings.add(rating);	
		}
	}
	
	public void removeRating(Rating rating){
		if(rating != null){
			this.ratings.remove(rating);
		}
	}

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_AUTHOR, author.getId());
			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_CONTENT, content);
			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_DATE, date);
			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_STATUS, status);
			
 			JSONArray likesJSON = new JSONArray();
			for(Like like : likes){
				JSONObject likeObject = new JSONObject();
				likeObject.put(ModelConstants.BaseConstants.FIELD_ID, like.getId());
				likesJSON.put(likeObject);
			}
 			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_LIKES, likesJSON);
 			jsonObject.put(ModelConstants.LikeConstants.FIELD_NUMBER, this.likes.size());
 			
 			JSONArray commentsJSON = new JSONArray();
			for(Comment comment : comments){
				JSONObject commentObject = new JSONObject();
				commentObject.put(ModelConstants.BaseConstants.FIELD_ID, comment.getId());
				commentsJSON.put(commentObject);
			}
 			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_COMMENTS, commentsJSON);
 			jsonObject.put(ModelConstants.CommentConstants.FIELD_NUMBER, this.comments.size());
 			
			JSONArray ratingsJSON = new JSONArray();
			for(Rating rating : ratings){
				JSONObject ratingObject = new JSONObject();
				ratingObject.put(ModelConstants.BaseConstants.FIELD_ID, rating.getId());
				ratingsJSON.put(ratingObject);
			}
 			jsonObject.put(ModelConstants.StoryFrameConstants.FIELD_RATINGS, commentsJSON);
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
		if (!(arg0 instanceof StoryFrame))return false;
		StoryFrame storyFrame = (StoryFrame)arg0;
		return this.id.equals(storyFrame.getId());
	}
	
}
