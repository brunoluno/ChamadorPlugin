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
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.connectToSyncService(cordova);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getMedias")) {
		    Cursor result = cordova.getActivity().getContentResolver().query("content://cativa.com.br.chamadorclientservice.contentprovider/medias", null, null, null, null);
            resultJSONArray = new JSONArray();
            
            if(result == null) {
                callback.error("Error!");
            } else {
            
                try {
            
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
                        resultJSONArray.put(resultRow);
                    }
                } finally {
                    if(result != null) result.close();
                }
            
                callback.success(resultJSONArray);
                
            }
            return true;
        }
        return false;
    }
}
