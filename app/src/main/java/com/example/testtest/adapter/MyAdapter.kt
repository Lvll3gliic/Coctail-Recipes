package com.example.testtest.adapter

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtest.R
import com.example.testtest.model.FullData


class MyAdapter(val context: FragmentActivity?, val names: FullData?): RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        var cocktailName: TextView = itemView.findViewById(R.id.title)
        var imgUrl: ImageView = itemView.findViewById(R.id.img)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cocktailName.text = names!!.fullRes[position].strDrink
        context?.let { Glide.with(it).load(names.fullRes[position].strDrinkThumb).into(holder.imgUrl) }

    }

    override fun getItemCount(): Int {
        return names!!.fullRes.size
    }
}