package ro.ase.csie.dma.soaexample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;

public class MainActivity extends AppCompatActivity {

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_NAME = "HelloService";
    private static final String SOAP_ACTION = "http://tempuri.org/HelloService";
    private static final String SERVICE_URL = "http://pdm.ase.ro/aService/CinemaService.asmx";
    private final String HOST = "pdm.ase.ro";
    private static final String PARAM = "Test from Android!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread() {
            @Override
            public void run() {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("serviceName", PARAM);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpsTransportSE androidHttpTransport = new HttpsTransportSE(HOST,443,"/aService/CinemaService.asmx",2000);
                androidHttpTransport.debug = true;
                try{
                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    String result = envelope.getResponse().toString();
                    Log.d("aServiceApp",result);
                }
                catch(Exception ex){
                    Log.e("aServiceApp", ex.getMessage());
                }
            }
        };
        thread.start();
    }
}