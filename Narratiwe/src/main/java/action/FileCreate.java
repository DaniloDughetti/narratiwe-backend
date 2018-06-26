package action;

import org.bson.Document;
import org.json.JSONObject;


import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import database.DatabaseManager;
import exception.RequestException;
import utility.ExceptionConstants;
import utility.ModelConstants.FileConstants;

public class FileCreate implements Action {

	private String content;
	private String type;
	private String name;
	
	public FileCreate(String name, String type, String content) throws Exception {
		
		
		if (name == null || name.isEmpty()) {
			throw new RequestException(ExceptionConstants.NAME_NULL);
		}
		if (type == null || type.isEmpty()) {
			throw new RequestException(ExceptionConstants.TYPE_NULL);
		}
		if (content == null || content.isEmpty()) {
			throw new RequestException(ExceptionConstants.CONTENT_NULL);
		}

		this.name = name;
		this.type = type;
		this.content = content;
	}

	public JSONObject run() throws Exception {
		DatabaseManager databaseManager = DatabaseManager.getInstance();
		GridFSUploadOptions options = new GridFSUploadOptions()
		                                    .chunkSizeBytes(1024)
		                                    .metadata(new Document(FileConstants.FIELD_TYPE, type));
		
		byte[] data = Base64.decode(content);
		GridFSUploadStream uploadStream = databaseManager.getGridFSBucket().openUploadStream(name, options);
		uploadStream.write(data);
		uploadStream.close();
		
		JSONObject result = new JSONObject();
		result.put(FileConstants.FIELD_ID, uploadStream.getFileId().toHexString()); 
		return result;
	}

}
