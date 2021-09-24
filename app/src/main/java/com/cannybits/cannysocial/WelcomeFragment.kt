package com.cannybits.cannysocial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_welcome.view.*

class WelcomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.btnToSignInPage.setOnClickListener {
           // to Signing in
        }

        view.btnToSignUpPage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }

        return view
    }

}