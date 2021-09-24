package com.cannybits.cannysocial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.view.*

class WelcomeFragment : Fragment() {

   var mAuth : FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAuth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.btnSignIn.setOnClickListener {

            //Sign in with Email and password
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()

            signInWithEmailAndPassword(email, password)
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_userProfileFragment)
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
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    Toast.makeText(activity,"User Logged In Successfully",Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(activity,"User Login Failed",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signInWithGoogle(){

    }

}