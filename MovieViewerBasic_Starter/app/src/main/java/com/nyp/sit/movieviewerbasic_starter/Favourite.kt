package com.nyp.sit.movieviewerbasic_starter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData.Companion.favouriteMovies
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData.Companion.mutablList

import kotlinx.android.synthetic.main.activity_view_list_of_movies.*
import kotlinx.android.synthetic.main.favourite.*

class Favourite: AppCompatActivity() {
    var FavAdapter: CustListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourite)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        var OverviewFromList = intent.getStringExtra("overviewadd")
        var TitleFromList = intent.getStringExtra("titleadd")
        var ReleaseFromList = intent.getStringExtra("releaseadd")
        var LangFromList = intent.getStringExtra("langadd")


        mutablList.add(
            SimpleMovieItem(
                "$OverviewFromList",
                "$ReleaseFromList",
                "$LangFromList",
                "$TitleFromList"
            )
        )
        var movise = mutablList
        var titl = movise.map { it.title }
        var date = movise.map { it.release_date }
        var lang = movise.map { it.original_langauge }
        var ov = movise.map { it.overview }


        val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titl)
        fmovielist.adapter = listAdapter
        var myIntent = Intent(this, SimpleItemDetailActivity::class.java)
        fmovielist.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var MOverview = ov[p2]
                var MTitle = titl[p2]
                var MRelease = date[p2]
                var MLang = lang[p2]
                myIntent.putExtra("overview", MOverview.toString())
                myIntent.putExtra("title", MTitle.toString())
                myIntent.putExtra("release", MRelease.toString())
                myIntent.putExtra("lang", MLang.toString())
                startActivityForResult(myIntent, 1)


            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}