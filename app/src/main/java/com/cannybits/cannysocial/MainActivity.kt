package com.cannybits.cannysocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var toLoginPage : Button
    private lateinit var toSignUpPage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        toLoginPage.setOnClickListener {
          Intent(this@MainActivity, LoginActivity::class.java).also {
              startActivity(it)
          }
        }

        toSignUpPage.setOnClickListener {
        Intent(this@MainActivity,SignUpActivity::class.java).also {
            startActivity(it)
        }

        }


    }

    private fun initView(){
        toLoginPage = btnToLoginPage
        toSignUpPage = btnToSignUpPage
    }
}