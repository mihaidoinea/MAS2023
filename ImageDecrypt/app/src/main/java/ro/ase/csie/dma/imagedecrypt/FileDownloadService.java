package ro.ase.csie.dma.imagedecrypt;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

public class FileDownloadService implements Callable<String> {

    String fileUrl;
    Context context;
    public FileDownloadService(MainActivity mainActivity, String urlString) {
        fileUrl = urlString;
        context = mainActivity;
    }

    @Override
    public String call() throws Exception {
        String filename = "";
        try {
            URL url = new URL(fileUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            int n;
            byte[] buffer = new byte[256];
            String[] splitted = url.toString().split("/");
            filename = splitted[splitted.length - 1];
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            while ((n = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            fos.close();
            inputStream.close();
            Log.d("FileDownloadService", "Url: " + fileUrl + ", download complete ...");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }
}
