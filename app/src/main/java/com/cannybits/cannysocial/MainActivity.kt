package com.cannybits.cannysocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var emailSignIn: EditText
    private lateinit var passwordSignIn: EditText
    private lateinit var signIn: Button
    private lateinit var googleSignIn: Button
    private lateinit var createAccount: Button
    private var auth: FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        auth = FirebaseAuth.getInstance()

        createAccount.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

        googleSignIn.setOnClickListener{
            signInWithGoogle()
        }

        btnSignIn.setOnClickListener {

            val email = emailSignIn.text.toString()
            val password = passwordSignIn.text.toString()

            signInWithEmailAndPassword(email, password)
            Intent(this, UserProfileActivity::class.java).also {
                startActivity(it)
            }
            clearScreen()
        }

    }

    private fun initView(){
        emailSignIn = etEmailSignIn
        passwordSignIn = etPasswordSignIn
        signIn = btnSignIn
        googleSignIn = btnSignInWithGoogle
        createAccount = btnToSignUpPage
    }

    private fun signInWithGoogle(){
        //google Sign In
    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Toast.makeText(this,"User Logged In Successfully", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this,"User Login Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun clearScreen(){
        emailSignIn.setText("")
        passwordSignIn.setText("")

    }


}