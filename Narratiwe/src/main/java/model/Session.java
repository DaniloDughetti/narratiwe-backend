package model;

import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Reference;

import utility.ModelConstants;

public class Session extends BaseEntity{
	
	@Reference(idOnly=true)
	private User user;
	private String token;
	
	public Session() {
		super();
	}

	public Session(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public JSONObject toJSON() throws Exception {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.SessionConstants.FIELD_USER, user.toJSON());
			jsonObject.put(ModelConstants.SessionConstants.FIELD_TOKEN, token);
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
		if (!(arg0 instanceof Session))return false;
		Session session = (Session)arg0;
		return this.id.equals(session.getId());
	}
}
