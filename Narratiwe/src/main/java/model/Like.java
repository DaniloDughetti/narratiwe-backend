package model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Like extends BaseEntity{
	
	
 	@Reference(idOnly = true)
	private User author;
 	
	private Date date;

	public Like() {
		super();
	}

	public Like(User author, Date date) {
		super();
		this.author 	= author;
		this.date 		= date;
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
		

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.LikeConstants.FIELD_DATE, date);
		
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
		if (!(arg0 instanceof Like))return false;
		Like like = (Like)arg0;
		return this.id.equals(like.getId());
	}
	
}
