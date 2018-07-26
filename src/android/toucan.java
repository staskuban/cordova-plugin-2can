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

import java.util.Iterator;

import android.net.Uri;

import java.lang.reflect.Field;

import android.content.ActivityNotFoundException;
import android.util.Log;
import android.os.Bundle;

/**
 * This class echoes a string called from JavaScript.
 */
public class toucan extends CordovaPlugin {
	private static final int PAYMENT_RESULT_CODE = 0x101010;
	CallbackContext callbackctx;@Override
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
				intent.putExtra("SecureCode", obj.getString("securecode"));
				intent.putExtra("Amount", obj.getInt("amount"));
				intent.putExtra("Description", obj.getString("description"));
				intent.putExtra("Response", "SHORT");

				cordova.setActivityResultCallback(this);
				cordova.getActivity().startActivityForResult(intent, 4);
			} else {
				res.put("success", new Boolean(false));
				res.put("stack", "pay method");
				this.callbackctx.error(res);
			}
		} catch(Exception e) {
			e.printStackTrace();
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
		JSONObject json = new JSONObject();
		try {

			Log.d("2can", "got result ");

			for (String key: data.getExtras().keySet()) {
				Object value = data.getExtras().get(key);
				if (value != null) {
					json.put(key, value);
				}
			}
			this.callbackctx.success(json);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}