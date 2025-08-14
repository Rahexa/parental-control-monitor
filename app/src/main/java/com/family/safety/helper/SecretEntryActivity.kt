package com.family.safety.helper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecretEntryActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Handle different entry methods
        when {
            intent.data?.scheme == "parentalcontrol" -> {
                // URL scheme: parentalcontrol://open
                if (intent.data?.host == "open") {
                    openMainActivity()
                } else {
                    showInvalidAccess()
                }
            }
            intent.data?.scheme == "tel" -> {
                // Dial code: *#12345#*
                val dialCode = intent.data?.schemeSpecificPart
                if (dialCode == "*#12345#*") {
                    openMainActivity()
                } else {
                    showInvalidAccess()
                }
            }
            else -> {
                showInvalidAccess()
            }
        }
    }
    
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showInvalidAccess() {
        Toast.makeText(this, "Invalid access", Toast.LENGTH_SHORT).show()
        finish()
    }
}
