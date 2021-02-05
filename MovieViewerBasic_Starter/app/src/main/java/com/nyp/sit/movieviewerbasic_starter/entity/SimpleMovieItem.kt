package com.nyp.sit.movieviewerbasic_starter

import java.io.Serializable

class  SimpleMovieItem(
    var overview: String? = null,
    var release_date: String? = null, var original_langauge: String? = null,
    var title: String? = null
) : Serializable {
    init {

        this.overview = overview
        this.release_date = release_date
        this.original_langauge = original_langauge
        this.title = title

    }

}

