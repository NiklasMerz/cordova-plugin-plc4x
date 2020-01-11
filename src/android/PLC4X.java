package dev.merz.cordova.plc4x;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.apache.log4j.Level;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class PLC4X extends CordovaPlugin {

    private static final String TAG = "PLC4XPlugin";
    private CallbackContext mCallbackContext = null;
    private PlcConnection plcConnection = null;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {

        this.mCallbackContext = callbackContext;
        Log.v(TAG, "PLC4XPlugin action: " + action);

        if (action.equals("connect")) {
            this.connect(args);

        } else if (action.equals("read")){
            this.read();
        }

        return true;
    }

    private void connect(JSONArray args) {
        final LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(cordova.getActivity().getApplicationContext().getExternalFilesDir("log").getAbsolutePath() + "app.log");
        logConfigurator.setRootLevel(Level.DEBUG);
        logConfigurator.setLevel("org.apache", Level.DEBUG);
        logConfigurator.configure();

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String connectionString = args.getString(0);
                    plcConnection = new PlcDriverManager().getConnection(connectionString);

                    if (!plcConnection.getMetadata().canRead()) {
                        sendError("This connection doesn't support reading.");
                        return;
                    }

                    sendSuccess(plcConnection.isConnected() ? "Connected": "Not connected");
                } catch (JSONException e) {
                    sendError("Cannot parse connection string" + e.getMessage());
                    return;
                } catch (Exception e) {
                    sendError("PlcConnection: " + e.getMessage());
                }
            }
        });
    }

    private void read() {
        if(this.plcConnection == null || !this.plcConnection.isConnected()) {
            this.sendError("Not connected");
            return;
        }

        try {
            // TODO: Just some random stuff right, arguments of function must be parsed
            PlcReadResponse response = plcConnection.readRequestBuilder()
                    .addItem("item1", "%DB555.DBD0:DINT")
                    .build()
                    .execute()
                    .get();
            Long res = response.getLong("item1");

            this.sendSuccess(res.toString());

        } catch (Exception e) {
            LOG.e(TAG, e.getMessage());
            this.sendError("PlcConnection: " + e.getMessage());
        }
    }

    private void sendError(String message) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, message);
        result.setKeepCallback(true);
        cordova.getActivity().runOnUiThread(() ->
                PLC4X.this.mCallbackContext.sendPluginResult(result));
    }

    private void sendSuccess(String message) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, message);
        result.setKeepCallback(true);
        cordova.getActivity().runOnUiThread(() ->
                PLC4X.this.mCallbackContext.sendPluginResult(result));
    }
}
