package com.cannybits.cannysocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var emailSignIn: EditText
    private lateinit var passwordSignIn: EditText
    private lateinit var signIn: Button
    private lateinit var googleSignIn: Button
    private lateinit var createAccount: Button
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        firebaseAuth = FirebaseAuth.getInstance()

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
        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
//            firebaseAuthWithGoogle(account!!)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
             //   SavedPreference.setEmail(this,account.email.toString())
             //   SavedPreference.setUsername(this,account.displayName.toString())
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Google SignIn Failed", Toast.LENGTH_LONG).show()
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Toast.makeText(this,"User Logged In Successfully", Toast.LENGTH_LONG).show()
                    Intent(this, UserProfileActivity::class.java).also {
                        startActivity(it)
                    }
                    clearEditText()
                } else{
                    Toast.makeText(this,"User Login Failed", Toast.LENGTH_LONG).show()
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
    }

    private fun clearEditText(){
        emailSignIn.setText("")
        passwordSignIn.setText("")

    }


}