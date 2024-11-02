package com.jonathan.slighfetchtakehome.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.slighfetchtakehome.R
import com.jonathan.slighfetchtakehome.data.models.FetchObject

class FetchListAdapter() :
    RecyclerView.Adapter<FetchListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNameTextView: TextView
        val itemIdTextView: TextView

        init {
            itemNameTextView = view.findViewById(R.id.item_name_tv)
            itemIdTextView = view.findViewById(R.id.id_tv)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_holder_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        viewHolder.itemNameTextView.text = item.name
        viewHolder.itemIdTextView.text = item.id.toString()
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<FetchObject>(){
        override fun areItemsTheSame(oldItem: FetchObject, newItem: FetchObject): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FetchObject, newItem: FetchObject): Boolean {
            return oldItem.name == newItem.name
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

}