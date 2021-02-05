package com.nyp.sit.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.LoginET
import kotlinx.android.synthetic.main.login.PasswordET
import kotlinx.android.synthetic.main.login.RegisterButton
import kotlinx.android.synthetic.main.register.*

class Login: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        RegisterButton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }
        LoginButton.setOnClickListener{
            if (PasswordET.text.toString().isEmpty()){
                PasswordET.error = "Password is empty"
                Toast.makeText(this,"password wrong try again",Toast.LENGTH_LONG).show()

            }else if (PasswordET.text.toString().equals("123456") ){
                val intent = Intent(this, SimpleViewListOfMoviesActivity::class.java)
                startActivity(intent)
            }else{
                PasswordET.error = "Password is wrong"

            }


        }


    }
}