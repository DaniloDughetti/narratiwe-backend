package action;

import org.json.JSONObject;

public interface Action {
	JSONObject run() throws Exception;
}