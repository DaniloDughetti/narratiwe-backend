package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import action.Action;
import action.StoryFrameCreate;
import action.StoryFrameDelete;
import action.StoryFrameGet;
import action.StoryFrameUpdate;
import exception.RequestException;
import manager.SessionManager;
import model.Session;
import utility.ActionConstants;
import utility.ActionConstants.OperationConstants;
import utility.ExceptionConstants;
import utility.ModelConstants.SessionConstants;
import utility.ModelConstants.StoryFrameConstants;

@WebServlet("/StoryFrameServlet")
public class StoryFrameServlet extends HttpServlet {

	private static final long serialVersionUID = -4021355775279391670L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionManager sessionManager = SessionManager.getInstance();
		Session session = null;
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        while ((line = reader.readLine()) != null){
			stringBuffer.append(line);
		}
		JSONObject requestObj = null;
		Action action = null;
		JSONObject result = new JSONObject();

		try {
			requestObj = new JSONObject(stringBuffer.toString());
			String command = requestObj.getString(ActionConstants.OperationConstants.COMMAND);

			//CREATE
			if (command.equals(OperationConstants.CREATE)){
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);

				String story 			= 	requestObj.getString(StoryFrameConstants.FIELD_STORY);
				String content			=	requestObj.getString(StoryFrameConstants.FIELD_CONTENT);
				String date 			= 	requestObj.getString(StoryFrameConstants.FIELD_DATE);
				
				action = new StoryFrameCreate(session.getUser().getId(), story, content, date);
			}
			
			//GET
			if (command.equals(OperationConstants.GET)){
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				
				String id = requestObj.getString(StoryFrameConstants.FIELD_ID);
				action = new StoryFrameGet(id);
			}

			//UPDATE
			if (command.equals(OperationConstants.UPDATE)){
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				
				String id				= 	requestObj.getString(StoryFrameConstants.FIELD_ID);
				String content			= 	requestObj.getString(StoryFrameConstants.FIELD_CONTENT);
				String date				= 	requestObj.getString(StoryFrameConstants.FIELD_DATE);
				int	   status			= 	requestObj.getInt(StoryFrameConstants.FIELD_STATUS);

				action = new StoryFrameUpdate(id, session.getUser().getId(), content, date, status);
				
			}
			
			//DELETE
			if (command.equals(OperationConstants.DELETE)){ 

				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				String id				= 	requestObj.getString(StoryFrameConstants.FIELD_ID);	
				String story			= 	requestObj.getString(StoryFrameConstants.FIELD_STORY);
				
				action = new StoryFrameDelete(id, session.getUser().getId(), story);				
			}

			if (action == null) {
				throw new RequestException(ExceptionConstants.ACTION_NOT_FOUND);
			}
			
			JSONObject data = action.run();

			result.put(OperationConstants.RESULT, OperationConstants.RES_SUCCESS);
			result.put(OperationConstants.DATA, data);

		} catch (Exception e) {
			try {
				e.printStackTrace();
				result.put(OperationConstants.RESULT, OperationConstants.RES_FAILURE);
				result.put(OperationConstants.ERROR_MESSAGE, e.getMessage());
			} catch (JSONException je) {
				je.printStackTrace();
			}

		} finally {
		    out.println(result.toString());
	        out.close();
		}
	}
}
