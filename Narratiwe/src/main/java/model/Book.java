package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Book extends BaseEntity{
	
	
 	@Reference(idOnly = true)
	private User author;

 	private String title;
 	private String description;
 	private Date date;
 	private int lastIndex;
 	
 	@Reference(idOnly = true)
	private List<Chapter> chapters;
 	
 	@Reference(idOnly = true)	
	private List<Like> likes;
	
	@Reference(idOnly = true)	
	private List<Comment> comments;
 	
 	
 	public Book() {
		super();
		this.chapters = new ArrayList<Chapter>();
	}
	public Book(User author, String title, String description, Date date) {
		super();
		this.author 		= author;
		this.title 			= title;
		this.description	= description;
		this.date 			= date;
		this.lastIndex 		= 0;
		this.chapters 		= new ArrayList<Chapter>();
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
	}
 	
	public Book(User author, String title, String description, Date date, int lastIndex, List<Chapter> chapters) {
		super();
		this.author 		= author;
		this.title 			= title;
		this.description	= description;
		this.date 			= date;
		this.lastIndex 		= lastIndex;
		this.chapters 		= chapters;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
	}
	
	public Book(User author, String title, String description, Date date, int lastIndex,
			List<Chapter> chapters, List<Like> likes) {
		super();
		this.author = author;
		this.title = title;
		this.description = description;
		this.date = date;
		this.lastIndex = lastIndex;
		this.chapters = chapters;
		this.likes = likes;
	}
	
	public Book(User author, String title, String description, Date date, int lastIndex,
			List<Chapter> chapters, List<Like> likes, List<Comment> comments) {
		super();
		this.author = author;
		this.title = title;
		this.description = description;
		this.date = date;
		this.lastIndex = lastIndex;
		this.chapters = chapters;
		this.likes = likes;
		this.comments = comments;
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

	public int getChapterNumber() {
		return this.chapters.size();
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
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

	public void addChapter(Chapter chapter){
		if(chapter != null){
			this.chapters.add(chapter);
			this.lastIndex++;	
		}
	}
	
	public void removeChapter(Chapter chapter){
		if(chapter != null){
			this.chapters.remove(chapter);
			if(this.lastIndex > 0 && this.getChapterNumber() > 0){
				this.lastIndex--;	
			}
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
	
	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.BookConstants.FIELD_TITLE, title);
			jsonObject.put(ModelConstants.BookConstants.FIELD_DESCRIPTION, description);
			jsonObject.put(ModelConstants.BookConstants.FIELD_AUTHOR, author.getId());
			jsonObject.put(ModelConstants.BookConstants.FIELD_DATE, date);
			jsonObject.put(ModelConstants.BookConstants.FIELD_CHAPTER_NUMBER, this.getChapterNumber());
			jsonObject.put(ModelConstants.BookConstants.FIELD_LAST_INDEX, lastIndex);
			
			JSONArray chaptersJSON = new JSONArray();
			for(Chapter chapter : chapters){
				JSONObject chapterObject = new JSONObject();
				chapterObject.put(ModelConstants.BaseConstants.FIELD_ID, chapter.getId());
				chaptersJSON.put(chapterObject);
			}
 			jsonObject.put(ModelConstants.BookConstants.FIELD_CHAPTERS, chaptersJSON);
			
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
		if (!(arg0 instanceof Book))return false;
		Book book = (Book)arg0;
		return this.id.equals(book.getId());
	}
	
}
