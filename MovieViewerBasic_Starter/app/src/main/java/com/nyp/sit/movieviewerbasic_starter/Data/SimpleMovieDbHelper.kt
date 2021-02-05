package com.nyp.sit.movieviewerbasic_starter.Data
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.nyp.sit.movieviewerbasic_starter.Data.DatabaseAdapter

import java.util.ArrayList

class SimpleMovieDbHelper(c: Context, db_name : String, ver_no : Int) : SQLiteOpenHelper(c, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_NAME = "simplemovie.db"
        val DATABASE_TABLE = "myTestDb"
        val DATABASE_VERSION = 1
        var _db: SQLiteDatabase? = null
        val context: Context? = null
        val KEY_ID = "id"
        val COLUMN_KEY_ID = 0
        val OVERVIEW = "overview"
        val COLUMN_OVERVIEW_ID = 1
        val RELEASE = "release"
        val COLUMN_RELEASE_ID = 2
        val ORIGINAL_LANGUAGE = "language"
        val COLUMN_ORIGINAL_LANGUAGE_ID = 3
        val TITLE = "title"
        val COLUMN_TITLE_ID = 4
        //val FAVOURITE = "favourite"
        //val COLUMN_FAVOURITE_ID = 5

        public val DATABASE_CREATE = ("create table " + DATABASE_TABLE + " " + "(" + KEY_ID + " integer primary key autoincrement,"
                + OVERVIEW + " Text, "
                + RELEASE + " Text, "
                + ORIGINAL_LANGUAGE + " Text, "
                + TITLE + " text not null); ")
                //+ FAVOURITE + " Boolean); ")
        public val MYDBADAPTER_LOG_CAT = "MY_LOG"





    }

override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL(DATABASE_CREATE)
        Log.w(MYDBADAPTER_LOG_CAT,"Helper :DB $DATABASE_TABLE created!")


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }








}