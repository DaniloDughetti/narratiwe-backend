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
import action.UserCreate;
import action.UserDelete;
import action.UserGet;
import action.UserLogin;
import action.UserUpdate;
import exception.RequestException;
import manager.SessionManager;
import model.Session;
import utility.ActionConstants;
import utility.ActionConstants.OperationConstants;
import utility.ExceptionConstants;
import utility.ModelConstants.SessionConstants;
import utility.ModelConstants.UserConstants;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

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
			System.out.println(stringBuffer.toString());
			requestObj = new JSONObject(stringBuffer.toString());
			String command = requestObj.getString(ActionConstants.OperationConstants.COMMAND);

			//CREATE
			if (command.equals(OperationConstants.CREATE)){
				String email			=	requestObj.getString(UserConstants.FIELD_EMAIL);
				String password			= 	requestObj.getString(UserConstants.FIELD_PASSWORD);
				String name				= 	requestObj.getString(UserConstants.FIELD_NAME);
				String surname			= 	requestObj.getString(UserConstants.FIELD_SURNAME);
				String nickname			= 	requestObj.getString(UserConstants.FIELD_NICKNAME);
				String description		= 	requestObj.getString(UserConstants.FIELD_DESCRIPTION);
				String city				= 	requestObj.getString(UserConstants.FIELD_CITY);
				String address			= 	requestObj.getString(UserConstants.FIELD_ADDRESS);
				String nation			= 	requestObj.getString(UserConstants.FIELD_NATION);
				String birthday			= 	requestObj.getString(UserConstants.FIELD_BIRTHDAY);
				int status				= 	requestObj.getInt(UserConstants.FIELD_STATUS);
				String image			= 	requestObj.getString(UserConstants.FIELD_IMAGE);
				
				action = new UserCreate(email, password, name, surname, nickname, description, city, address, nation, image, birthday, status, sessionManager);
				
			}
			
			//GET
			if (command.equals(OperationConstants.GET)){
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				String email = requestObj.getString(UserConstants.FIELD_EMAIL);
				action = new UserGet(email);
			}

			//UPDATE
			if (command.equals(OperationConstants.UPDATE)){
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				
				String password			= 	requestObj.getString(UserConstants.FIELD_PASSWORD);
				String name				= 	requestObj.getString(UserConstants.FIELD_NAME);
				String surname			= 	requestObj.getString(UserConstants.FIELD_SURNAME);
				String nickname			= 	requestObj.getString(UserConstants.FIELD_NICKNAME);
				String description		= 	requestObj.getString(UserConstants.FIELD_DESCRIPTION);
				String city				= 	requestObj.getString(UserConstants.FIELD_CITY);
				String address			= 	requestObj.getString(UserConstants.FIELD_ADDRESS);
				String nation			= 	requestObj.getString(UserConstants.FIELD_NATION);
				String image			= 	requestObj.getString(UserConstants.FIELD_IMAGE);
				String birthday			= 	requestObj.getString(UserConstants.FIELD_BIRTHDAY);
				int status				= 	requestObj.getInt(UserConstants.FIELD_STATUS);
				
				action = new UserUpdate(session.getUser().getId(), password, name, surname, nickname, description, city, address, nation, birthday, status, image);
				
			}
			
			//DELETE
			if (command.equals(OperationConstants.DELETE)){ 
				String token = requestObj.getString(SessionConstants.FIELD_TOKEN);
				session = sessionManager.validateSession(token);
				
				action = new UserDelete(session.getUser().getId());
				
			}

			//LOGIN
			if (command.equals(OperationConstants.LOGIN)){ 
				
				String email = requestObj.getString(UserConstants.FIELD_EMAIL);
				String password = requestObj.getString(UserConstants.FIELD_PASSWORD);
				action = new UserLogin(email, password, sessionManager);
				
				
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
