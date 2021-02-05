package com.nyp.sit.movieviewerbasic_starter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustListAdapter(context: Context, data:ArrayList<SimpleMovieItem>) : BaseAdapter() {
    internal val mList:ArrayList<SimpleMovieItem> ?= ArrayList()
    private val mInflater : LayoutInflater
    init{
        this.mInflater= LayoutInflater.from(context)
        mList?.addAll(data)
    }
    fun addAll(data: Collection<SimpleMovieItem>){
        mList?.addAll(data)
    }
    fun clear(){
        mList?.clear()
    }
    override fun getItem(p0: Int):Any? {
        return mList?.get(p0)
    }
    override fun getItemId(p0: Int): Long{
        return 0
    }

    override fun getCount(): Int {
        return if(mList == null) 0 else mList?.size
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?{
        val v : View
        v = this.mInflater.inflate(R.layout.custom_movie_list_movie_adapter_item,parent,false )
        val label: TextView = v.findViewById(R.id.movie_name)
        label.text = mList?.get(position)?.title
        return v

    }

}