package com.cannybits.cannysocial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : Fragment() {

    private var mAuth : FirebaseAuth? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAuth = FirebaseAuth.getInstance()


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

       view.btnSignUp.setOnClickListener {
           val email = etEmailSignup.text.toString()
           val password = etPasswordSignup.text.toString()

            //Add User to Firebase
            signUpUserToFirebase(email, password)

            //Go to user profile page
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_userProfileFragment)
        }

        return view
    }



        private fun signUpUserToFirebase(email: String, password: String){
            mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener() {
                    task ->
                    if(task.isSuccessful){
                       Toast.makeText(activity,"User Successfully Added",Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity,"Failed To Add The User",Toast.LENGTH_LONG).show()
                    }
                }
        }




}