package ro.ase.csie.dma.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyReceiver br = new MyReceiver();
        IntentFilter filter = new IntentFilter("ro.ase.csie.ism.mas.broadcast.SEND_DATA");
        ContextCompat.registerReceiver(this, br, filter, ContextCompat.RECEIVER_NOT_EXPORTED);

        MessageReceiver mr = new MessageReceiver();
        IntentFilter msgFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        ContextCompat.registerReceiver(this, mr, msgFilter, ContextCompat.RECEIVER_EXPORTED);

    }

    public void broadcastIntent(View view){
        Intent intent = new Intent("ro.ase.csie.ism.mas.broadcast.SEND_DATA");
        intent.putExtra("data", "Nothing to see here, move along.");
        sendBroadcast(intent);
    }
}