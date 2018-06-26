package model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Entity;

import utility.ModelConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

@Entity
public class User extends BaseEntity{

	private String email;
	private String password;
	private String name;
	private String surname;
	private String nickname;
	private String description;
	private String city;
	private String address;
	private String nation;
	private Date birthday;
	private int status;
	private ObjectId image;


	public User(){
		super();
	}
		
	public User(String email, String password, String name, String surname, String nickname, String description, String city,
			String address, String nation, Date birthday, int status, ObjectId image) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.nickname = nickname;
		this.description = description;
		this.city = city;
		this.address = address;
		this.nation = nation;
		this.birthday = birthday;
		this.status = status;
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public ObjectId getImage() {
		return image;
	}

	public void setImage(ObjectId image) {
		this.image = image;
	}
		
	public Date getBirthday() {
		return birthday;
	}
	
	public String getBirthdayFormatted() {
		Utils utility = Utils.getInstance(SystemConstants.DATE_ONLY);
		return utility.formatDate(birthday);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public JSONObject toJSON() throws Exception {

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ModelConstants.BaseConstants.FIELD_ID, id);
			jsonObject.put(ModelConstants.UserConstants.FIELD_EMAIL, email);
			jsonObject.put(ModelConstants.UserConstants.FIELD_NAME, name);
			jsonObject.put(ModelConstants.UserConstants.FIELD_SURNAME, surname);
			jsonObject.put(ModelConstants.UserConstants.FIELD_NICKNAME, nickname);
			jsonObject.put(ModelConstants.UserConstants.FIELD_BIRTHDAY, this.getBirthdayFormatted());
			jsonObject.put(ModelConstants.UserConstants.FIELD_DESCRIPTION, description);
			jsonObject.put(ModelConstants.UserConstants.FIELD_CITY, city);
			jsonObject.put(ModelConstants.UserConstants.FIELD_ADDRESS, address);
			jsonObject.put(ModelConstants.UserConstants.FIELD_NATION, nation);
			jsonObject.put(ModelConstants.UserConstants.FIELD_STATUS, status);
			jsonObject.put(ModelConstants.UserConstants.FIELD_IMAGE, image);
		
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
		if (!(arg0 instanceof User))return false;
		User user = (User)arg0;
		return this.id.equals(user.getId());
	}
		
}