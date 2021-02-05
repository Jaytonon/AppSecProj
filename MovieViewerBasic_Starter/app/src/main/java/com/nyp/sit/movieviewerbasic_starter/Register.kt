package com.nyp.sit.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register.*

class Register: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        RegisterButton.setOnClickListener{
            Toast.makeText(this,"Login =" + LoginET.text +
                        "\n password =" + PasswordET.text +
                        "\n email=" + EmailET.text +
                        "\n admin no = " + AdminNoET.text +
                        "\n pem grp = " + PEMET.text ,Toast.LENGTH_LONG).show()
            Thread.sleep(1000)
            val intent = Intent(this, Verification::class.java)
            startActivity(intent)

        }



    }

}