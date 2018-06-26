package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.User;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.FileConstants;
import utility.ModelConstants.FileTypeConstants;
import utility.ModelConstants.SystemConstants;

public class UserUpdate implements Action {

	private Datastore datastore;
	private ObjectId userId;
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
	private String image;

	public UserUpdate(final ObjectId user, final String password, final String name, final String surname, final String nickname, 
					  final String description, final String city, final String address, final String nation, final String birthday, 
					  final int status, final String image) throws Exception {

		if (user == null) {
			throw new RequestException(ExceptionConstants.USER_NULL);
		}

		this.userId 		= user;
		this.password   	= password; 
		this.name       	= name;
		this.surname    	= surname;
		this.nickname   	= nickname;
		this.description	= description;
		this.city       	= city;
		this.address    	= address;
		this.nation     	= nation;
		this.image      	= image;
		if(birthday != null && !birthday.isEmpty()){
			this.birthday = Utils.getInstance(SystemConstants.DATE_ONLY).parseDate(birthday);
		}
			
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {
		
		User user = this.datastore.get(User.class, userId);
	
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);

		if(this.password != null && !this.password.isEmpty()){
			user.setPassword(this.password);
		}

		if(this.name != null && !this.name.isEmpty()){
			user.setName(this.name);
		}
		
		if(this.surname != null && !this.surname.isEmpty()){
			user.setSurname(this.surname);
		}
		
		if(this.nickname != null && !this.nickname.isEmpty()){
			user.setNickname(this.nickname);
		}
		
		if(this.description != null && !this.description.isEmpty()){
			user.setDescription(this.description);
		}
		
		if(this.city != null && !this.city.isEmpty()){
			user.setCity(this.city);
		}
		
		if(this.address != null && !this.address.isEmpty()){
			user.setAddress(this.address);
		}
		
		if(this.nation != null && !this.nation.isEmpty()){
			user.setNation(this.nation);
		}

		if(this.birthday != null){
			user.setBirthday(this.birthday);
		}

		if(this.status != user.getStatus()){
			user.setStatus(this.status);
		}

		
		if(image != null && !image.isEmpty()){
			FileCreate newFile = new FileCreate(user.getEmail(), FileTypeConstants.TYPE_IMAGE_JPG, image);
			JSONObject imageJSON = newFile.run();
			ObjectId imageObj = new ObjectId(imageJSON.getString(FileConstants.FIELD_ID));
			user.setImage(imageObj);
		}

		datastore.save(user);
		
		return new JSONObject();
	}

}
