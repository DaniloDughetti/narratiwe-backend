package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Story extends BaseEntity{
	
	
 	@Reference(idOnly = true)
	private User author;

 	private String title;
 	private String description;
 	private Date date;
 	private int category;
 	private int type;
	private String language;
 	
 	@Reference(idOnly = true)
	private List<StoryFrame> storyFrames;
 	
 	@Reference(idOnly = true)	
	private List<Like> likes;
	
	@Reference(idOnly = true)	
	private List<Comment> comments;
 	
 	
 	public Story() {
		super();
		this.storyFrames = new ArrayList<StoryFrame>();
	}
	public Story(User author, String title, String description, Date date, int category, int type, String language) {
		super();
		this.author 		= author;
		this.title 			= title;
		this.description	= description;
		this.date 			= date;
		this.category 		= category;
		this.type	 		= type;
		this.language		= language;
		this.storyFrames 	= new ArrayList<StoryFrame>();
		this.likes 			= new ArrayList<Like>();
		this.comments 		= new ArrayList<Comment>();
	}
 	
	public Story(User author, String title, String description, Date date, int category, int type, String language, List<StoryFrame> storyFrames) {
		super();
		this.author 		= author;
		this.title 			= title;
		this.description	= description;
		this.date 			= date;
		this.category 		= category;
		this.type	 		= type;
		this.language		= language;
		this.storyFrames	= storyFrames;
		this.likes 			= new ArrayList<Like>();
		this.comments 		= new ArrayList<Comment>();
	}
	
	public Story(User author, String title, String description, Date date, int category, int type, String language,
			List<StoryFrame> storyFrames, List<Like> likes) {
		super();
		this.author 		= author;
		this.title 			= title;
		this.description 	= description;
		this.date 			= date;
		this.category 		= category;
		this.type	 		= type;
		this.language		= language;
		this.storyFrames	= storyFrames;
		this.likes 			= likes;
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
		
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public int getLikesNumber() {
		return this.likes.size();
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getCommentsNumber() {
		return this.comments.size();
	}

	public void addStoryFrame(StoryFrame storyFrame){
		if(storyFrames != null){
			this.storyFrames.add(storyFrame);
		}
	}
	
	public void removeStoryFrame(StoryFrame storyFrame){
		if(storyFrames != null){
			this.storyFrames.remove(storyFrame);
		}
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
	
	public List<StoryFrame> getStoryFrames() {
		return storyFrames;
	}
	
	public void setStoryFrames(List<StoryFrame> storyFrames) {
		this.storyFrames = storyFrames;
	}
	
	
	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.StoryConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_TITLE, title);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_DESCRIPTION, description);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_AUTHOR, author.getId());
			jsonObject.put(ModelConstants.StoryConstants.FIELD_CATEGORY, category);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_LANGUAGE, language);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_TYPE, type);
			jsonObject.put(ModelConstants.StoryConstants.FIELD_DATE, date);
			
			JSONArray storyFramesJSON = new JSONArray();
			for(StoryFrame storyFrame : this.storyFrames){
				JSONObject storyFrameObject = new JSONObject();
				storyFrameObject.put(ModelConstants.BaseConstants.FIELD_ID, storyFrame.getId());
				storyFramesJSON.put(storyFrameObject);
			}
 			jsonObject.put(ModelConstants.StoryConstants.FIELD_STORYFRAMES, storyFramesJSON);
			
 			JSONArray likesJSON = new JSONArray();
			for(Like like : likes){
				JSONObject likeObject = new JSONObject();
				likeObject.put(ModelConstants.BaseConstants.FIELD_ID, like.getId());
				likesJSON.put(likeObject);
			}
 			jsonObject.put(ModelConstants.StoryConstants.FIELD_LIKES, likesJSON);
 			jsonObject.put(ModelConstants.LikeConstants.FIELD_NUMBER, this.likes.size());
 			
 			JSONArray commentsJSON = new JSONArray();
			for(Comment comment : comments){
				JSONObject commentObject = new JSONObject();
				commentObject.put(ModelConstants.BaseConstants.FIELD_ID, comment.getId());
				commentsJSON.put(commentObject);
			}
 			jsonObject.put(ModelConstants.StoryConstants.FIELD_COMMENTS, commentsJSON);
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
		if (!(arg0 instanceof Story))return false;
		Story story = (Story)arg0;
		return this.id.equals(story.getId());
	}
	
}
