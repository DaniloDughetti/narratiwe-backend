package utility;

public class ModelConstants {

	public static class BaseConstants {
		public static final String FIELD_ID 			= "id";
		public static final String FIELD_LAST_CHANGE 	= "lastChange";
		public static final String FIELD_CREATION_DATE 	= "creationDate";
		public static final String FIELD_IMAGE 			= "image";
		public static final String FIELD_SESSION 		= "session";
		public static final String FIELD_LIKES 			= "likes";
		public static final String FIELD_COMMENTS		= "comments";
		public static final String FIELD_RATINGS		= "ratings";
		public static final String FIELD_LANGUAGE 		= "language";
		public static final String FIELD_TARGET			= "target";
		public static final String FIELD_TARGET_ID		= "targetId";
		public static final String FIELD_STATUS 		= "status";
	}
	
	public static class UserConstants extends BaseConstants {
		public static final String ENTITY 				= "user";
		public static final String FIELD_EMAIL 			= "email";
		public static final String FIELD_PASSWORD 		= "password";
		public static final String FIELD_NAME 			= "name";
		public static final String FIELD_SURNAME 		= "surname";
		public static final String FIELD_NICKNAME 		= "nickname";
		public static final String FIELD_DESCRIPTION 	= "description";
		public static final String FIELD_CITY 			= "city";
		public static final String FIELD_ADDRESS 		= "address";
		public static final String FIELD_NATION 		= "nation";
		public static final String FIELD_BIRTHDAY 		= "birthday";
	}

	public static class BookConstants extends BaseConstants {
		public static final String ENTITY 				= "book";
		public static final String FIELD_DESCRIPTION	= "description";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_LAST_INDEX		= "lastIndex";
		public static final String FIELD_TITLE 			= "title";
		public static final String FIELD_PAGES_NUMBER	= "pagesNumber";
		public static final String FIELD_CHAPTER_NUMBER	= "chapterNumber";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_CHAPTERS 		= "chapters";
	}
	
	public static class StoryConstants extends BaseConstants {
		public static final String ENTITY 				= "story";
		public static final String FIELD_DESCRIPTION	= "description";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_TITLE 			= "title";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_STORYFRAMES	= "storyFrames";
		public static final String FIELD_CATEGORY	 	= "category";
		public static final String FIELD_TYPE	 		= "type";
	}

	public static class StoryFrameConstants extends BaseConstants {
		public static final String ENTITY 				= "storyFrame";
		public static final String FIELD_STORY 			= "story";
		public static final String FIELD_CONTENT 		= "content";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_DATE 			= "date";
	}
	
	public static class ChapterConstants extends BaseConstants {
		public static final String ENTITY 				= "chapter";
		public static final String FIELD_BOOK 			= "book";
		public static final String FIELD_TITLE	 		= "title";
		public static final String FIELD_AUTHOR 		= "author";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_INDEX 			= "index";
		public static final String FIELD_PAGES 			= "pages";
		public static final String FIELD_PAGE_NUMBER	= "pageNUmber";
	}
	
	public static class PageConstants extends BaseConstants {
		public static final String ENTITY 				= "page";
		public static final String FIELD_CHAPTER 		= "chapter";
		public static final String FIELD_CONTENT 		= "content";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_INDEX 			= "index";
	}
		
	public static class CommentConstants extends BaseConstants {
		public static final String ENTITY 				= "comment";
		public static final String FIELD_CONTENT		= "content";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_NUMBER			= "commentsNumber";
	}
	
	public static class LikeConstants extends BaseConstants {
		public static final String ENTITY 				= "like";
		public static final String FIELD_DATE 			= "date";
		public static final String FIELD_AUTHOR			= "author";
		public static final String FIELD_NUMBER			= "likesNumber";
	}
	
	public static class RatingConstants extends BaseConstants {
		public static final String ENTITY 					= "rating";
		public static final String FIELD_DATE 				= "date";
		public static final String FIELD_AUTHOR				= "author";
		public static final String FIELD_NUMBER				= "ratingsNumber";
		public static final String FIELD_VOTE_CREATIVITY	= "vote_creativity";
		public static final String FIELD_VOTE_CHARACTER		= "vote_character";
		public static final String FIELD_VOTE_INCIPIT		= "vote_incipit";
		public static final String FIELD_VOTE_LANGUAGE		= "vote_language";
		public static final String FIELD_VOTE_GENERAL		= "vote_general";
	}

	public static class SessionConstants extends BaseConstants{
		public static final String FIELD_USER 			= "user";
		public static final String FIELD_TOKEN 			= "token";
	}
	
	public static class FileConstants extends BaseConstants{
		public static final String FIELD_CONTENT 		= "content";
		public static final String FIELD_TYPE 			= "type";
		public static final String FIELD_NAME 			= "name";
	}
	
	public static class FileTypeConstants{
		public static final String TYPE_IMAGE_PNG 		= "image/png";
		public static final String TYPE_IMAGE_JPG 		= "image/jpg";
		public static final String TYPE_TEXT 			= "text/plain";
		public static final String TYPE_VIDEO 			= "video/mpeg";
	}
	
	public static class ModelIdConstants{
		public static final int MODEL_USER				= 0; 
		public static final int MODEL_CHAPTER			= 1; 
		public static final int MODEL_BOOK				= 2; 
		public static final int MODEL_COMMENT			= 3; 
		public static final int MODEL_STORY				= 4; 
		public static final int MODEL_STORYFRAME		= 5; 
	}
	
	public static class UserStatusConstants{
		public static final int ACTIVE_NORMAL			= 0; 
		public static final int ACTIVE_VIP				= 1;
		public static final int DELETED					= 2; 
	}
	
	public static class StoryTypeConstants{
		public static final int TYPE_SHARED				= 0; 
		public static final int TYPE_PRIVATE			= 1;
	}
	
	public static class SystemConstants{
		public static final int PAGE_LIMIT				= 3000;
		public static final int CATEOGRIES_NUMBER		= 32;
		public static final int RATING_LIMIT_LOWER		= 0;
		public static final int RATING_LIMIT_UPPER		= 5;
		public static final int DATE_HOUR 				= 0;
		public static final int DATE_ONLY 				= 1;
	}
	
	public static class PageStatusConstants{
		public static final int NOT_LOCKED				= 0;
		public static final int LOCKED					= 1;
	}
	
	public static class BookCateogoriesConstants{
		public static final int GENERAL								= 0;
		public static final int SCIENCE_FINCTION					= 2;
		public static final int SATIRE								= 3;
		public static final int DRAMA								= 4;
		public static final int ACTION_AND_ADVENTURE				= 5;
		public static final int ROMANCE								= 6;
		public static final int MYSTERY								= 7;
		public static final int HORROR								= 8;
		public static final int SELF_HELP							= 9;
		public static final int HEALTH								= 10;
		public static final int GUIDE								= 11;
		public static final int TRAVEL								= 12;
		public static final int CHILDREN							= 13;
		public static final int RELIGION_SPRITUALITY_AND_NEW_AGE	= 14;
		public static final int SCIENCE								= 15;
		public static final int HISTORY								= 16;
		public static final int MATH								= 17;
		public static final int ANTHOLOGY							= 18;
		public static final int POETRY								= 19;
		public static final int ENCYCLOPEDIAS						= 20;
		public static final int DICTIONARIES						= 21;
		public static final int COMICS								= 22;
		public static final int ART									= 23;
		public static final int COOCKBOOKS							= 24;
		public static final int DIARIES								= 25;
		public static final int JOURNALS							= 26;
		public static final int PRAYER_BOOKS						= 27;
		public static final int SERIES								= 28;
		public static final int TRILOGY								= 29;
		public static final int BIOGRAPHIES							= 30;
		public static final int AUTOBIOGRAPHIES						= 31;
		public static final int FANTASY								= 32;
	}

}
