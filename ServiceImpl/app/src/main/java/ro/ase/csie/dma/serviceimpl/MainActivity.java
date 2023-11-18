package ro.ase.csie.dma.serviceimpl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mFirst,mSecond;
    Button btnCallServ;
    TextView mResult;
    private IService mService;

    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            mService = null;
            Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_SHORT);
            Log.d("IRemote","Binding - Service disconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT);
            Log.d("IRemote","Binding - Service connected");
            mService = IService.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT);
            Log.d("IRemote","Binding - Service connected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirst =(EditText) findViewById(R.id.firstValue);
        mSecond = (EditText) findViewById(R.id.secondValue);
        mResult = (TextView) findViewById(R.id.resultText);
        btnCallServ = (Button) findViewById(R.id.srvCall);

        if(mService == null){
            Intent intent = new Intent(this, RemoteService.class);
            boolean test = getApplicationContext().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            Toast.makeText(getApplicationContext(),"Service: " + test,Toast.LENGTH_SHORT).show();
//            Intent it = new Intent("com.remote.service.CONCATENATE");
//            bindService(it,mServiceConnection, Service.BIND_AUTO_CREATE);
        }


        btnCallServ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.srvCall)
                {
                    String a = mFirst.getText().toString();
                    String b = mSecond.getText().toString();

                    try {
                        mResult.setText("Concatenated text:"+ mService.concatenate(a, b));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("IRemote", "Binding - Add operation");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
