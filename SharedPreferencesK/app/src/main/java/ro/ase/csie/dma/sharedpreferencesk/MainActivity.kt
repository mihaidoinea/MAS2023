package ro.ase.csie.dma.sharedpreferencesk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences

class MainActivity : AppCompatActivity(), BiometricAuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun hasBiometricCapability(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(DEVICE_CREDENTIAL)
    }
    private fun isBiometricReady(context: Context) =
        hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS


    private fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String
    ): BiometricPrompt.PromptInfo {
        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setAllowedAuthenticators(DEVICE_CREDENTIAL)

        return builder.build()
    }

    private fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {

        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.w(this.javaClass.simpleName, "Authentication errorCode: $errorCode, $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

    private fun showBiometricPrompt(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Input your Fingerprint or FaceID to ensure it's you!",
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ) {
        val promptInfo = setBiometricPromptInfo(
            title,
            subtitle,
            description
        )
        if(isBiometricReady(this)) {

            val biometricPrompt = initBiometricPrompt(activity, listener)
            biometricPrompt.authenticate(promptInfo);
        }
    }

    fun putPrefs(view: View) {
        showBiometricPrompt(
            activity = this,
            listener = this
        )
    }

    fun getPrefs(view: View) {

        val prefs: EncryptedSharedPreferences =
            SecureSharedPrefs.getEncryptedSharedPreferences(this)
        val value: String? = "Saved value: " + prefs.getString("key", null)
        findViewById<TextView>(R.id.textView).setText(value)
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()

    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        // User authenticated successfully; you can now store data in encrypted shared preferences
        Log.d(this.javaClass.simpleName, "User authenticated successfully!")
        val prefs: EncryptedSharedPreferences = SecureSharedPrefs.getEncryptedSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val value = findViewById<EditText>(R.id.etSavedValue).text.toString()
        editor.putString("key", value)
        editor.apply()
        Toast.makeText(this, "Value was saved!", Toast.LENGTH_SHORT).show()
    }
}