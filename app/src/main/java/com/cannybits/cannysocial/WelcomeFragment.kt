package com.cannybits.cannysocial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


const val RC_SIGN_IN = 123
const val TAG = "canny"

class WelcomeFragment : Fragment() {


  //private var Auth : FirebaseAuth? = null
    private var auth: FirebaseAuth? = null




    // Configure Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(activity, gso)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

  

        auth = FirebaseAuth.getInstance()

        // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.btnSignIn.setOnClickListener {

            //Sign in with Email and password
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()

            signInWithEmailAndPassword(email, password)
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_userProfileFragment)
            clearScreen()
        }

        view.btnSignInWithGoogle.setOnClickListener {
            signInWithGoogle()
        }

        view.btnToSignUpPage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }

        return view
    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Toast.makeText(activity,"User Logged In Successfully",Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(activity,"User Login Failed",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener( ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(context,"signInWithCredential:success",Toast.LENGTH_LONG).show()
                    val user = auth!!.currentUser
                    Log.d(TAG, "User: $user")
                 //   updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context,"signInWithCredential:failure",Toast.LENGTH_LONG).show()
                  //  updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth!!.currentUser
      // updateUI(currentUser)
        Toast.makeText(context,"Current User: $currentUser",Toast.LENGTH_LONG).show()
    }
    private fun clearScreen(){
        etEmailSignIn.setText("")
        etPasswordSignIn.setText("")

    }




}