package com.example.testtest.adapter

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testtest.R
import com.example.testtest.model.Data
import retrofit2.Response


class MyAdapterCategory(val context: FragmentActivity?, val names: Response<Data>): RecyclerView.Adapter<MyAdapterCategory.ViewHolder>() {


    lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView = itemView.findViewById(R.id.category_name)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_category, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryName.text = names.body()!!.res.get(position).category
    }

    override fun getItemCount(): Int {
        return names.body()!!.res.size
    }
}