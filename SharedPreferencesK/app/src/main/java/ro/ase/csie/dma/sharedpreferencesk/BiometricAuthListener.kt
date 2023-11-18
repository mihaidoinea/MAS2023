package ro.ase.csie.dma.sharedpreferencesk

import androidx.biometric.BiometricPrompt


open interface BiometricAuthListener {
    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
}
