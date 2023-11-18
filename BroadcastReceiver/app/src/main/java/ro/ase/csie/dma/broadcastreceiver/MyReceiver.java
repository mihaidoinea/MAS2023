package ro.ase.csie.dma.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        sb.append("Data: " + intent.getStringExtra("data") + "\n");
        String log = sb.toString();
        Toast.makeText(context, "Data: " + intent.getStringExtra("data"), Toast.LENGTH_LONG).show();
        Log.d(TAG, log);
    }
}

