package com.nyp.sit.movieviewerbasic_starter

import android.app.Application
import android.content.Context
import android.database.Cursor
import com.nyp.sit.movieviewerbasic_starter.Data.DatabaseAdapter

import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieSampleData


class MovieViewerApplication(): Application(){
    private var MovieList: ArrayList<String> = ArrayList<String>()
    private var MovieIdList: ArrayList<Int> = ArrayList<Int>()


    companion object {

        var movieItemArrays: ArrayList<SimpleMovieItem>?=null
        val appInstance = MovieViewerApplication()
        var movieFavArray: ArrayList<SimpleMovieItem>?=null

    }
    fun addData(c: Context){
        val db = DatabaseAdapter(c)
        db.open()
        val myCursor: Cursor?
        myCursor = db.retrieveAllEntriesCursor()
        if (myCursor!!.count == 0){
            var movieSample = SimpleMovieSampleData.simpleMovieitemArray
            for (i in movieSample){
                var movieSamples = SimpleMovieItem(i.overview,i.release_date,i.original_langauge,i.title)
                val insertedEntry = db.insertEntry(movieSamples)
            }

        }
        db.close()
    }
    fun addToDatabase(movie:SimpleMovieItem, c: Context): Long?{
        val db = DatabaseAdapter(c)
        db.open()
        val rowIDofInsertedEntry = db.insertEntry(movie)
        db.close()
        return rowIDofInsertedEntry
    }
    fun addtoFav(rowId: Int, movie:SimpleMovieItem, c:Context){
        if(movieFavArray == null){
            movieFavArray= arrayListOf()
        }
        movieFavArray!!.add(movie)

    }
    fun retrieveFavs(movie: Context, c: Context): ArrayList<SimpleMovieItem> {
        if(movieFavArray == null){
            movieFavArray= arrayListOf()
        }
        return movieFavArray as ArrayList<SimpleMovieItem>
    }
//    fun retrieveOneFav(rowId: Int, c: Context): SimpleMovieItem{
//        var favList = DatabaseAdapter(c)
//        var retrieveMovie = favList[rowId]
//        var movie = SimpleMovieItem(retrieveMovie.overview,retrieveMovie.release_date,retrieveMovie.original_langauge,retrieveMovie.title)
//        return movie
//    }
    fun retrieveAll(c:Context): ArrayList<SimpleMovieItem>? {
        val myCursor: Cursor?
        var titleStr = ""
        var OverviewStr = ""
        var langStr = ""
        var DateStr = ""

        val db = DatabaseAdapter(c)
        db.open()
        movieItemArrays = ArrayList()
        myCursor = db.retrieveAllEntriesCursor()
        if (myCursor != null && myCursor.count > 0) {
            myCursor!!.moveToFirst()
            do {
                OverviewStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_OVERVIEW_ID)
                DateStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_RELEASE_ID)
                langStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_ORIGINAL_LANGUAGE_ID)
                titleStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_TITLE_ID)
                       // myCursor.getString(SimpleMovieDbHelper.COLUMN_FAVOURITE_ID)
                var movie = SimpleMovieItem(OverviewStr,DateStr,langStr,titleStr)



                movieItemArrays!!.add(movie)
            } while (myCursor.moveToNext())
        }
        db.close()
        return movieItemArrays
    }
    fun retrieveOne(i: Int, c: Context): ArrayList<SimpleMovieItem>?{
        val myCursor: Cursor?
        var titleStr = ""
        var OverviewStr = ""
        var langStr = ""
        var DateStr = ""

        val db = DatabaseAdapter(c)
        db.open()
        movieItemArrays = ArrayList()
        myCursor = db.retrieveOneEntryCursor(i)
        if (myCursor != null && myCursor.count > 0) {
            myCursor!!.moveToFirst()
            do {
                OverviewStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_OVERVIEW_ID)
                DateStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_RELEASE_ID)
                langStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_ORIGINAL_LANGUAGE_ID)
                titleStr=myCursor.getString(SimpleMovieDbHelper.COLUMN_TITLE_ID)
                // myCursor.getString(SimpleMovieDbHelper.COLUMN_FAVOURITE_ID)
                var movie = SimpleMovieItem(OverviewStr,DateStr,langStr,titleStr)



                movieItemArrays!!.add(movie)
            } while (myCursor.moveToNext())
        }
        db.close()
        return movieItemArrays

    }





}
