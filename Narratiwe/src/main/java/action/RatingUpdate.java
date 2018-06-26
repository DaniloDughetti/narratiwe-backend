package action;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;

import database.DatabaseManager;
import exception.RequestException;
import model.Rating;
import utility.ExceptionConstants;
import utility.Utils;
import utility.ModelConstants.SystemConstants;

public class RatingUpdate implements Action {

	private Datastore datastore;
	private ObjectId id;
	private Date date;
	private int voteCreativity;
	private int voteCharacter;
	private int voteIncipit;
	private int voteLanguage;
	private int voteGeneral;
	private ObjectId userRequestId;
	 
	public RatingUpdate(final String id, final ObjectId userRequestId, final String date, int voteCreativity, int voteCharacter, 
			int voteIncipit, int voteLanguage, int voteGeneral) throws Exception {

		if (id == null || id.isEmpty()) {
			throw new RequestException(ExceptionConstants.ID_NULL);
		}
		if(date != null && !date.isEmpty())
			this.date = Utils.getInstance(SystemConstants.DATE_HOUR).parseDate(date);
		this.id = new ObjectId(id);
		this.userRequestId = userRequestId;
		
		if(voteCreativity < SystemConstants.RATING_LIMIT_LOWER || voteCreativity > SystemConstants.RATING_LIMIT_UPPER ||
				voteCharacter < SystemConstants.RATING_LIMIT_LOWER || voteCharacter > SystemConstants.RATING_LIMIT_UPPER ||
				voteIncipit < SystemConstants.RATING_LIMIT_LOWER || voteIncipit > SystemConstants.RATING_LIMIT_UPPER ||
				voteLanguage < SystemConstants.RATING_LIMIT_LOWER || voteLanguage > SystemConstants.RATING_LIMIT_UPPER ||
				voteGeneral < SystemConstants.RATING_LIMIT_LOWER || voteGeneral > SystemConstants.RATING_LIMIT_UPPER){
					throw new RequestException(ExceptionConstants.RATING_OUT_OF_RANGE);
			}

		this.voteCreativity = voteCreativity;
		this.voteCharacter = voteCharacter;
		this.voteIncipit = voteIncipit;
		this.voteLanguage = voteLanguage;
		this.voteGeneral = voteGeneral;
		
		this.datastore = DatabaseManager.getInstance().getDatastore();
	}
	
	public JSONObject run() throws Exception {

		Rating rating = datastore.get(Rating.class, id);

		if(rating == null)
			throw new RequestException(ExceptionConstants.RATING_NOT_FOUND);
		//Verifico che l'utente l'entita' che sto cercando di eliminare sia di proprieta' dell'utente richiedente
		if(!rating.getAuthor().getId().equals(this.userRequestId)){
			throw new RequestException(ExceptionConstants.OPERATION_NOT_PERMITTED);
		}
		
		if(this.date != null){
			rating.setDate(this.date);
		}

		rating.setVoteCreativity(voteCreativity);
		rating.setVoteCharacter(voteCharacter);
		rating.setVoteIncipit(voteIncipit);
		rating.setVoteLanguage(voteLanguage);
		rating.setVoteGeneral(voteGeneral);
		
		datastore.save(rating);
		
		return new JSONObject();
	}

}
