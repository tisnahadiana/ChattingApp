package com.droomptech.chattingapp.activity

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.droomptech.chattingapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId : String
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please Wait ...")
        builder.setTitle("Loading")
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

        val phoneNumber = "+62" + intent.getStringExtra("number")

        Log.d("SHUBH", "onCreate: " +phoneNumber)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    dialog.dismiss()
//                    val smsManager = SmsManager.getDefault() as SmsManager
//                    smsManager.sendTextMessage("cloud", null, "", null, null)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity, "Please try again!!  ${p0}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()

                    Toast.makeText(this@OTPActivity, "Lihat OTP pada pesan anda!", Toast.LENGTH_SHORT).show()
                    verificationId = p0

                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button.setOnClickListener {
            if (binding.otp.text!!.isEmpty()){
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(verificationId, binding.otp.text!!.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            dialog.dismiss()
                            startActivity(Intent(this,ProfileActivity::class.java))
                            finish()
                        } else {
                            dialog.dismiss()
                            Toast.makeText(this, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}