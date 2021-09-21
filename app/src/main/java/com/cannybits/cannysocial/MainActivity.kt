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
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        toSignUpPage.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initView(){
        toLoginPage = btnToLoginPage
        toSignUpPage = btnToSignUpPage
    }
}