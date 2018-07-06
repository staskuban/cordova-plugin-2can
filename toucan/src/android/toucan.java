package cordova.plugin.toucan;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * This class echoes a string called from JavaScript.
 */
public class toucan extends CordovaPlugin {
CallbackContext callbackctx;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackctx = callbackContext;
        if (action.equals("pay")) {
           JSONObject obj = args.getJSONObject(0);
            this.pay(obj);
            return true;
        }
        return false;
    }

   /**
     * toucanpayment
     */
    private void pay(JSONObject obj) {
        JSONObject res = new JSONObject();
        try {
            if (obj != null) {
               Context context = cordova.getActivity();
               Intent intent = new Intent("ru.toucan.PAYMENT");
               intent.putExtra("PackageName", obj.getString("packagename"));
               intent.putExtra("SecureCode", obj.getString("securesode"));
               intent.putExtra("Amount", obj.getInt("amount"));
               intent.putExtra("Description", obj.getString("description"));




                cordova.setActivityResultCallback(this);
                cordova.getActivity().startActivityForResult(intent, 4);
            } else {
               res.put("success", new Boolean(false));
               this.callbackctx.error(res);
            }
        } catch (Exception e) {
          this.callbackctx.error(new JSONObject());
        }
    }

        /**
         * Обработка результата из интента
         *
         * @param requestCode
         * @param resultCode
         * @param data
         */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        JSONObject res = new JSONObject();
        try {
            JSONObject json = new JSONObject();

            for (String key : data.getExtras().keySet()) {
                Object value = data.getExtras().get(key);
                if (value != null) {
                    json.put(key, value);
                }
            }
            if (requestCode == 4) {
                res.put("action", "ru.toucan.PAYMENT");
                if (resultCode == -1) {
                    res.put("success", new Boolean(true));
                } else if (resultCode == 0) {
                    res.put("success", new Boolean(false));
                }
                res.put("additional_data", json);
                   this.callbackctx.success(res);
            }
            else {
            res.put("success", new Boolean(false));
            this.callbackctx.error(res);
            }

        } catch (JSONException e) {

           this.callbackctx.error(new JSONObject());
        }


    }
}
