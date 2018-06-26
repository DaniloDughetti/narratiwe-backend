package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;
import utility.ModelConstants.RatingConstants;


public class Chapter extends BaseEntity{
	
 	@Reference(idOnly = true)
	private User author;
 	
	private String title;
	private Date date;
	private int pageNumber;
	private int index;

 	@Reference(idOnly = true)	
	private List<Page> pages;
 	
 	@Reference(idOnly = true)	
	private List<Like> likes;
 	
	@Reference(idOnly = true)	
	private List<Comment> comments;

	@Reference(idOnly = true)	
	private List<Rating> ratings;
 	
	public Chapter() {
		super();
		this.pages = new ArrayList<Page>();
		this.pageNumber = 0;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	}

	public Chapter(User author, String title, Date date, int index) {
		super();
		this.author = author;
		this.title = title;
		this.date = date;
		this.index = index;
		this.pages = new ArrayList<Page>();
		this.pageNumber = 0;
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	}
	
	public Chapter(User author, String title, Date date, int index, List<Page> pages) {
		super();
		this.author = author;
		this.title = title;
		this.date = date;
		this.index = index;
		this.pages = pages;
		if(this.pages != null){
			this.pageNumber = this.pages.size();
		}else{
			this.pageNumber = 0;
		}
		this.likes = new ArrayList<Like>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Rating>();
	}
		
	public Chapter(User author, String title, Date date, int index, List<Like> likes, List<Page> pages, List<Comment> comments) {
		super();
		this.author = author;
		this.title = title;
		this.date = date;
		this.index = index;
		this.likes = likes;
		this.pages = pages;
		if(this.pages != null){
			this.pageNumber = this.pages.size();
		}else{
			this.pageNumber = 0;
		}
		this.comments = comments;
		this.ratings = new ArrayList<Rating>();
	}
	
	public Chapter(User author, String title, Date date, int index, List<Like> likes, List<Page> pages, List<Comment> comments, List<Rating> ratings) {
		super();
		this.author = author;
		this.title = title;
		this.date = date;
		this.index = index;
		this.likes = likes;
		this.pages = pages;
		if(this.pages != null){
			this.pageNumber = this.pages.size();
		}else{
			this.pageNumber = 0;
		}
		this.comments = comments;
		this.ratings = ratings;
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

	public void setTitle(String content) {
		this.title = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
	
	public void addPage(Page page){
		if(page != null){
			this.pages.add(page);
			this.pageNumber++;	
		}
	}
	
	public void removePage(Page page){
		if(page != null){
			this.pages.remove(page);
			if(this.getPageNumber() > 0){
				this.pageNumber--;	
			}
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
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.ChapterConstants.FIELD_AUTHOR, author);
			jsonObject.put(ModelConstants.ChapterConstants.FIELD_TITLE, title);
			jsonObject.put(ModelConstants.ChapterConstants.FIELD_DATE, date);
			jsonObject.put(ModelConstants.ChapterConstants.FIELD_INDEX, index);
			jsonObject.put(ModelConstants.ChapterConstants.FIELD_PAGE_NUMBER, pageNumber);
		
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
 			jsonObject.put(ModelConstants.BaseConstants.FIELD_COMMENTS, commentsJSON);
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
		if (!(arg0 instanceof Page))return false;
		Chapter chapter = (Chapter)arg0;
		return this.id.equals(chapter.getId());
	}
	
}
