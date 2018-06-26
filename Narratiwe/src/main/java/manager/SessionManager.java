package manager;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import database.DatabaseManager;
import exception.RequestException;
import model.Session;
import model.User;
import utility.ExceptionConstants;
import utility.ModelConstants.SessionConstants;

public class SessionManager {
	private static SessionManager sessionManager;
	private SecureRandom random = new SecureRandom();
	private Datastore datastore;
	
	public static SessionManager getInstance(){
		if(sessionManager == null)
			sessionManager = new SessionManager();
		return sessionManager;
	}
	
	public SessionManager() {
		super();
		
		System.out.println("Create SessionManager");
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	private String getNewToken() {
	    return new BigInteger(130, random).toString(32);
	}
	
	public Session validateSession(String token) throws RequestException{
		if(token == null || token.isEmpty())
			throw new RequestException(ExceptionConstants.TOKEN_EXPIRED);
		
		Session session = datastore.createQuery(Session.class).field(SessionConstants.FIELD_TOKEN).equal(token).get();
		
		if (session == null){
			throw new RequestException(ExceptionConstants.SESSION_NOT_FOUND);
		}
		
		if(!session.getToken().equals(token))
			throw new RequestException(ExceptionConstants.TOKEN_EXPIRED);
		
		return session;
	}
	
	public User getUserFromSession(String token) throws RequestException{
		if(token == null || token.isEmpty())
			throw new RequestException(ExceptionConstants.TOKEN_EXPIRED);
		Session session = datastore.createQuery(Session.class).field(SessionConstants.FIELD_TOKEN).equal(token).get();
		return session.getUser();
	}
	
	public JSONObject generateSession(User user) throws Exception{
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		Session session = new Session();
		session.setUser(user);
		session.setToken(this.getNewToken());
		datastore.save(session);
		return session.toJSON();
	}
	
	public JSONObject generateSessionLogin(User user) throws Exception{
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		Query<Session> deleteQuerySession  = datastore.createQuery(Session.class).field(SessionConstants.FIELD_USER).equal(user.getId());
		datastore.delete(deleteQuerySession);
		
		Session session = new Session();
		session.setUser(user);
		session.setToken(this.getNewToken());
		datastore.save(session);
		return session.toJSON();
	}
	
	public JSONObject generateSession(String id) throws Exception{
		Session session = new Session();
		User user = datastore.get(User.class, new ObjectId(id));
		if(user == null)
			throw new RequestException(ExceptionConstants.USER_NOT_FOUND);
		session.setUser(user);
		session.setToken(this.getNewToken());
		datastore.save(session);
		return session.toJSON();
	}
		
}