package action;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import database.DatabaseManager;
import exception.RequestException;
import manager.SessionManager;
import model.Story;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants;
import utility.Utils;
import utility.ModelConstants.FileConstants;
import utility.ModelConstants.FileTypeConstants;
import utility.ModelConstants.SystemConstants;

public class UserCreate implements Action {
	
	private Datastore datastore;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String nickname;
	private String description;
	private String city;
	private String address;
	private String nation;
	private String image;
	private Date birthday;
	private int status;
	private SessionManager sessionManager;
	
	public UserCreate(String email, String password, String name, String surname, String nickname,
			String description, String city, String address, String nation, String image, String birthday, int status,
			SessionManager sessionManager) throws RequestException, ParseException {
		
		if (email == null || email.isEmpty()) {
			throw new RequestException(ExceptionConstants.EMAIL_NULL);
		}		
		
		if (password == null || password.isEmpty()) {
			throw new RequestException(ExceptionConstants.PASSWORD_NULL);
		}

		if (surname == null || surname.isEmpty()) {
			throw new RequestException(ExceptionConstants.SURNAME_NULL);
		}
		
		if (nickname == null || nickname.isEmpty()) {
			throw new RequestException(ExceptionConstants.NICKNAME_NULL);
		}
		
		if (description == null || description.isEmpty()) {
			throw new RequestException(ExceptionConstants.DESCRIPTION_NULL);
		}
		
		if (city == null || city.isEmpty()) {
			throw new RequestException(ExceptionConstants.CITY_NULL);
		}
		
		if (address == null || address.isEmpty()) {
			throw new RequestException(ExceptionConstants.ADDRESS_NULL);
		}
		
		if (nation == null || nation.isEmpty()) {
			throw new RequestException(ExceptionConstants.NATION_NULL);
		}
		
		if (birthday == null || birthday.isEmpty()) {
			throw new RequestException(ExceptionConstants.BIRTHDAY_NULL);
		}
		if (image == null || image.isEmpty()) {
			this.image = "";
		}else{
			this.image = image;
		}
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
		this.email = email;
		this.name = name;
		this.password = password;
		this.surname = surname;
		this.nickname = nickname;
		this.description = description;
		this.city = city;
		this.address = address;
		this.nation = nation;
		this.birthday = Utils.getInstance(SystemConstants.DATE_ONLY).parseDate(birthday);
		this.status = status;
		this.sessionManager = sessionManager;
	}

	public JSONObject run() throws Exception {
		
		ObjectId imageObj = null;
		
		Pattern pattern = Pattern.compile("^" + this.email + "$", Pattern.CASE_INSENSITIVE);
		Query<User> query = this.datastore.createQuery(User.class).field(ModelConstants.UserConstants.FIELD_EMAIL).equal(pattern).retrievedFields(true, ModelConstants.UserConstants.FIELD_ID);
		User user = query.get();
		
		if (user != null) {
			System.out.println(user.toString());
			throw new RequestException(ExceptionConstants.EMAIL_ALREADY_EXIST);
		}
		
		if (image != null && !image.isEmpty()) {
			FileCreate newFile = new FileCreate(email, FileTypeConstants.TYPE_IMAGE_JPG, image);
			JSONObject imageJSON = newFile.run();
			imageObj = new ObjectId(imageJSON.getString(FileConstants.FIELD_ID));
		}
		
		user = new User(email, password, name, surname, nickname, description, city, address, nation, birthday, status, imageObj);
		datastore.save(user);
		return sessionManager.generateSession(user);
	}

}
