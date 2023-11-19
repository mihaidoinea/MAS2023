package ro.ase.csie.dma.imagedecrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import kotlin.NotImplementedError;

public class MainActivity extends AppCompatActivity {

    String urlAES_Key = new String("https://student.ism.ase.ro/access/content/group/Y2S1_MAS/AndroidEncrypt/AES_Key_encrypted_with_RSA_PrivateKey");
    String urlImage_encrypted = new String("https://student.ism.ase.ro/access/content/group/Y2S1_MAS/AndroidEncrypt/Image_encrypted_with_AES");
    String urlRSA_PublicKey = new String("https://student.ism.ase.ro/access/content/group/Y2S1_MAS/AndroidEncrypt/RSA_PublicKey");
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public static byte[] read(Context context, String file) throws IOException {
        byte[] ret = null;

        if (context != null) {
            try {
                String fileName = context.getFilesDir() + "/" + file;
                InputStream inputStream = new FileInputStream(fileName);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                int nextByte = inputStream.read();
                while (nextByte != -1) {
                    outputStream.write(nextByte);
                    nextByte = inputStream.read();
                }
                ret = outputStream.toByteArray();

            } catch (FileNotFoundException ignored) { }
        }

        return ret;
    }

    private PublicKey getRSAPublicKey(String filename) throws Exception {
        return null;
    }

    private byte[] decryptAESKey(PublicKey publicKey, String filename) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        return new byte[0];
    }

    private void decryptImage(byte[] key, String filename) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {

        //decrypt and set image
    }

    public void getImage(View view) {
        ExecutorService executors = Executors.newFixedThreadPool(4);
        Future<String> aesKey = executors.submit(new FileDownloadService(this, urlAES_Key));
        Future<String> imageEncrypted = executors.submit(new FileDownloadService(this, urlImage_encrypted));
        Future<String> rsaPublic = executors.submit(new FileDownloadService(this, urlRSA_PublicKey));

        try {
            String keyFile = aesKey.get();
            String publicKeyFile = rsaPublic.get();

            PublicKey publicKey = getRSAPublicKey(publicKeyFile);
            byte[] Key = decryptAESKey(publicKey, keyFile);
            decryptImage(Key, imageEncrypted.get());
            Toast.makeText(this, "All done", Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}