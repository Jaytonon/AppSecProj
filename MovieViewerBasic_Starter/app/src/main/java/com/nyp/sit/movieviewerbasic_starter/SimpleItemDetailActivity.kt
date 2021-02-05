package com.nyp.sit.movieviewerbasic_starter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData.Companion.favouriteMovies

import kotlinx.android.synthetic.main.simple_activity_item_detail.*
import java.util.*
import kotlin.collections.ArrayList

class SimpleItemDetailActivity : AppCompatActivity() {
    companion object {
        val RETURN_LIST = "favlist"
        var TempList: ArrayList<String> = ArrayList<String>()
        var favMovieList: ArrayList<SimpleMovieItem> = arrayListOf<SimpleMovieItem>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_activity_item_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var position = intent.getIntExtra("position", 0)
        var movieviewer = MovieViewerApplication.appInstance
        var movie = movieviewer.retrieveOne(position, applicationContext)
        movie_title.text = movie!![0].title
        movie_release_date.text = movie!![0].release_date
        movie_langauge.text = movie!![0].original_langauge
        movie_overview.text = movie!![0].overview
//        var favMovieList = favMovieList.add(movie)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        var intent = Intent()

        intent.putExtra(RETURN_LIST, favMovieList)
        setResult(Activity.RESULT_OK, intent)
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simpleitem, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.Favourite) {
            var myIntent = Intent(this, Favourite::class.java)
            myIntent.putExtra("overviewadd", movie_overview.text.toString())
            myIntent.putExtra("titleadd", movie_title.text.toString())
            myIntent.putExtra("releaseadd", movie_release_date.text.toString())
            myIntent.putExtra("langadd", movie_langauge.text.toString())
            startActivityForResult(myIntent, 1)
        }
        return super.onOptionsItemSelected(item)
    }
}