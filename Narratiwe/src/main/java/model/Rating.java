package model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Rating extends BaseEntity{
	
	
 	@Reference(idOnly = true)
	private User author;
 	
	private Date date;
	private int voteCreativity;
	private int voteCharacter;
	private int voteIncipit;
	private int voteLanguage;
	private int voteGeneral;
				

	public Rating() {
		super();
	}

	public Rating(User author, Date date) {
		super();
		this.author = author;
		this.date = date;
		this.voteCreativity = 0;
		this.voteCharacter = 0;
		this.voteIncipit = 0;
		this.voteLanguage = 0;
		this.voteGeneral = 0;
	}

	public Rating(User author, Date date, int voteCreativity, int voteCharacter, int voteIncipit, int voteLanguage, int voteGeneral) {
		super();
		this.author = author;
		this.date = date;
		this.voteCreativity = voteCreativity;
		this.voteCharacter = voteCharacter;
		this.voteIncipit = voteIncipit;
		this.voteLanguage = voteLanguage;
		this.voteGeneral = voteGeneral;
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getVoteCreativity() {
		return this.voteCreativity;
	}

	public void setVoteCreativity(int voteCreativity) {
		this.voteCreativity = voteCreativity;
	}

	public int getVoteCharacter() {
		return this.voteCharacter;
	}

	public void setVoteCharacter(int voteCharacter) {
		this.voteCharacter = voteCharacter;
	}
	
	public int getVoteIncipit() {
		return this.voteIncipit;
	}

	public void setVoteIncipit(int voteIncipit) {
		this.voteIncipit = voteIncipit;
	}
	
	public int getVoteLanguage() {
		return this.voteLanguage;
	}

	public void setVoteLanguage(int voteLanguage) {
		this.voteLanguage = voteLanguage;
	}

	public int getVoteGeneral() {
		return this.voteGeneral;
	}

	public void setVoteGeneral(int voteGeneral) {
		this.voteGeneral = voteGeneral;
	}

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_DATE, date);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_VOTE_CREATIVITY, voteCreativity);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_VOTE_CHARACTER, voteCharacter);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_VOTE_INCIPIT, voteIncipit);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_VOTE_LANGUAGE, voteLanguage);
			jsonObject.put(ModelConstants.RatingConstants.FIELD_VOTE_GENERAL, voteGeneral);
		
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
		if (!(arg0 instanceof Rating))return false;
		Rating rating = (Rating)arg0;
		return this.id.equals(rating.getId());
	}
	
}
