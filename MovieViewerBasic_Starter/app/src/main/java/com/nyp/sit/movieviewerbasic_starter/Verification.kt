package com.nyp.sit.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.verification.*

class Verification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verification)
        var actionbar = supportActionBar
        actionbar!!.title = "MovieViewer"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true);
        VerifyButton.setOnClickListener({
            if(VerificationET.text.toString().equals("123456") ){
                val intent = Intent(this, SimpleItemDetailActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, " verification code is wrong try again", Toast.LENGTH_SHORT).show()
            }

        })



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        var myIntent = Intent(this, Register::class.java)
        //myIntent.putExtra("movieObject", movieObject)
        startActivity(myIntent)
        return true

    }
    fun displayToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }
}