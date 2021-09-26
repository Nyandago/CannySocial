package com.cannybits.cannysocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUp : Button
    private lateinit var emailSignUp : EditText
    private lateinit var passwordSignUp : EditText
    private lateinit var googleSignUp : Button

    private var mAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()
        mAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {
            val email = emailSignUp.text.toString()
            val password = passwordSignUp.text.toString()
            signUpUserToFirebase(email, password)

            Intent(this, UserProfileActivity::class.java).also {
                startActivity(it)
            }
            clearScreen()
        }

        googleSignUp.setOnClickListener {
            signUpWithGoogle()
        }
    }

    private fun signUpUserToFirebase(email: String, password: String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener() {
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"User Successfully Added", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"Failed To Add The User", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signUpWithGoogle(){

    }

    private fun initView(){
        signUp = btnSignUp
        emailSignUp = etEmailSignup
        passwordSignUp = etPasswordSignup
        googleSignUp = btnSignUpWithGoogle
    }

    private fun clearScreen(){
        emailSignUp.setText("")
        passwordSignUp.setText("")
    }
}