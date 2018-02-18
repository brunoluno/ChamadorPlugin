package cordova.chamador.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;

/**
 * This class echoes a string called from JavaScript.
 */
public class ChamadorPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getMedias")) {
            JSONArray resultJSONArray = new JSONArray();
            Cursor result = null;
            try {
                Uri contentUri = Uri.parse("content://cativa.com.br.chamadorclientservice.contentprovider/medias");
                result = cordova.getActivity().getContentResolver().query(contentUri, null, null, null, null);
        
                if(result == null) {
                    callbackContext.error("Error!");
                    return false;
                } else {
                    while (result != null && result.moveToNext()) {
                        JSONObject resultRow = new JSONObject();
                        int colCount = result.getColumnCount();
                        for (int i = 0; i < colCount; i++) {
                            try {
                                resultRow.put(result.getColumnName(i), result.getString(i));
                            } catch (JSONException e) {
                                resultRow = null;
                            }
                        }
                        if (resultRow != null) {
                            resultJSONArray.put(resultRow);
                        }
                    }
                    callbackContext.success(resultJSONArray);
                }
            } catch (Exception e) {
                callbackContext.error(e.getMessage());
            } finally {
                if(result != null) result.close();
            }
            return true;
        } else {
            callbackContext.error('Command not found!');
        }
        return false;
    }
}
