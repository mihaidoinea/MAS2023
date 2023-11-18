package ro.ase.csie.dma.sharedpreferencesk

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException

class SecureSharedPrefs {
    companion object {
        fun getEncryptedSharedPreferences(context: MainActivity): EncryptedSharedPreferences {
            return try {
                val masterKey: MasterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
                (EncryptedSharedPreferences.create(
                    context,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                ) as EncryptedSharedPreferences)!!
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
                throw RuntimeException("Failed to create encrypted shared preferences")
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Failed to create encrypted shared preferences")
            }
        }
    }

}