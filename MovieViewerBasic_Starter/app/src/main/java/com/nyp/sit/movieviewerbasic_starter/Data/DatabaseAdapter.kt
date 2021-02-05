package com.nyp.sit.movieviewerbasic_starter.Data

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.DATABASE_NAME
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.DATABASE_TABLE
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.DATABASE_VERSION
//import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.FAVOURITE
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.KEY_ID
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.ORIGINAL_LANGUAGE
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.OVERVIEW
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.RELEASE
import com.nyp.sit.movieviewerbasic_starter.Data.SimpleMovieDbHelper.Companion.TITLE
import com.nyp.sit.movieviewerbasic_starter.MovieViewerApplication
import com.nyp.sit.movieviewerbasic_starter.SimpleMovieItem


public class DatabaseAdapter(c :Context) : Application() {


    private var dbHelper: SimpleMovieDbHelper? = null
    var _db: SQLiteDatabase? = null

    init {
        dbHelper = SimpleMovieDbHelper(c, DATABASE_NAME, DATABASE_VERSION)
    }

     fun close() {
        _db?.close()
    }

    fun open() {
        try {
            _db = dbHelper?.getWritableDatabase()
        } catch (e: SQLiteException) {
            _db = dbHelper?.readableDatabase
        }



    }
    fun insertEntry(SimpleMovieItem: SimpleMovieItem): Long? {
        val newEntryValues = ContentValues()
        newEntryValues.put(OVERVIEW, SimpleMovieItem.overview)
        newEntryValues.put(RELEASE, SimpleMovieItem.release_date)
        newEntryValues.put(ORIGINAL_LANGUAGE,SimpleMovieItem.original_langauge)
        newEntryValues.put(TITLE,SimpleMovieItem.title)
       // newEntryValues.put(FAVOURITE,Favourite)
        return _db?.insert(DATABASE_TABLE,null,newEntryValues)
    }
    fun retrieveAllEntriesCursor(): Cursor? {
        var c: Cursor? = null

        try{
            c= _db?.query(DATABASE_TABLE, arrayOf(KEY_ID, OVERVIEW, RELEASE, ORIGINAL_LANGUAGE, TITLE), null,null,null,null,null)
        }catch (e: SQLiteException){
            Log.w(DATABASE_TABLE, "Retrieve fail")
        }
        return c
    }
    fun retrieveOneEntryCursor(i : Int): Cursor? {
        var c: Cursor? = null
       // var test = "(" + MovieViewerApplication.appInstance.retrieveOne().toString().filterNot { filtered.indexOf(it) > -1 } + ")"
        //val selectQuery = "SELECT  * FROM" + DATABASE_TABLE +"WHERE" + KEY_ID + " =?);"
        try {
            c = _db?.query(DATABASE_TABLE, arrayOf(KEY_ID, OVERVIEW, RELEASE, ORIGINAL_LANGUAGE, TITLE), KEY_ID + " = " + i, null, null, null, null)

        } catch (e: SQLiteException) {

        Log.w(DATABASE_TABLE, "Retrieve fail")
    }
        return c
    }





}


