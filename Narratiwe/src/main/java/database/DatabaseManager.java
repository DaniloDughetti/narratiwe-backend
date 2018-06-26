package database;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import model.User;
import utility.DatabaseConstants.CollectionConstants;
import utility.DatabaseConstants.ConfigurationConstants;

public class DatabaseManager {

	private static DatabaseManager databaseManager;
	private MongoClient client;
	private Morphia morphia;
	private MongoDatabase database;
	private GridFSBucket gridFSBucket;

	public static DatabaseManager getInstance(){
		if(databaseManager == null)
			databaseManager = new DatabaseManager();
		return databaseManager;
	}

	private DatabaseManager(){
		
		System.out.println("Create DatabaseManager");
		
		client = new MongoClient(ConfigurationConstants.DB_HOST);
		
		morphia = new Morphia();
		morphia.map(User.class);
		
		database = client.getDatabase(ConfigurationConstants.DB_NAME);
		gridFSBucket = GridFSBuckets.create(database, CollectionConstants.FILES);
	}
	
	public Datastore getDatastore() {
		return morphia.createDatastore(client, ConfigurationConstants.DB_NAME);
	}
	
	public GridFSBucket getGridFSBucket() {
		return gridFSBucket;
	}

}