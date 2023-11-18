package ro.ase.csie.dma.serviceimpl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RemoteService extends Service{
    private final IService.Stub mBinder = new IService.Stub() {

        public String concatenate(String a, String b)
        {
            return a + b;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
