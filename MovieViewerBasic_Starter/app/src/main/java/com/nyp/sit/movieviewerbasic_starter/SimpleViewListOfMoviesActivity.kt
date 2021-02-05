package com.nyp.sit.movieviewerbasic_starter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData.Companion.simpleMovieitemArray
import kotlinx.android.synthetic.main.activity_view_list_of_movies.*
import kotlinx.android.synthetic.main.simpleitem.view.*
import java.io.Serializable


class SimpleViewListOfMoviesActivity : AppCompatActivity() {
    //var movieslist: ArrayList<SimpleMovieItem> = arrayListOf<SimpleMovieItem>()
    var favMovieList: ArrayList<SimpleMovieItem> = arrayListOf<SimpleMovieItem>()
    var moviesAdapter: CustListAdapter? = null
    val VIEW_ACTIVITY_RESULT_CODE = 1
    companion object{
        var favouriteMovies: ArrayList<SimpleMovieItem> = arrayListOf<SimpleMovieItem>()
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_list_of_movies)
        //val moviesNameList : List<String>
        val movies = simpleMovieitemArray

        var movieNameList = movies.map { it.title }
//        var movieOverviewList = movies.map { it.overview }
//        var movieLanguageList = movies.map { it.original_langauge }
//        var movieDateList = movies.map { it.release_date }
        //val listAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,movieNameList)
        //movielist.adapter = listAdapter


        movielist.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                moviename.text = movieNameList[position] + " at position $position is being selected from the ListView"


                var intent = Intent(this@SimpleViewListOfMoviesActivity, SimpleItemDetailActivity::class.java)
                intent.putExtra("position",position+1 )
                intent.putExtra("favList",favMovieList)
                //movieNameList[p2].toString()+
                startActivityForResult(intent,VIEW_ACTIVITY_RESULT_CODE)

            }

        }






    }
    fun launchNextScreen(context: Context, people: SimpleMovieItem): Intent {
        val intent = Intent(context, SimpleItemDetailActivity::class.java)
        intent.putExtra("movieName", people)
        intent.putExtra("favList",favMovieList)
        return intent
        startActivityForResult(intent,VIEW_ACTIVITY_RESULT_CODE)
    }
    override fun onResume() {
        retrieveMovies()

        super.onResume()
    }
    private fun retrieveMovies(){
        val movieList: ArrayList<SimpleMovieItem>
        val movieviewer = MovieViewerApplication.appInstance
        movieviewer.addData(applicationContext)
        movieList = ArrayList(movieviewer.retrieveAll(applicationContext)!!)
        moviesAdapter = CustListAdapter(this,movieList)
        movielist.adapter = moviesAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simplelist,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.signout) {
            var myIntent = Intent(this, Login::class.java)
            startActivity(myIntent)

        }
        else if(item?.itemId == R.id.ViewFav){
            var myIntent = Intent(this, Favourite::class.java)
            startActivity(myIntent)
        }
        return super.onOptionsItemSelected(item)
    }




}
