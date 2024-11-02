package com.jonathansligh.fetchcodingexercise.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonathansligh.fetchcodingexercise.databinding.ItemListRowBinding
import com.jonathansligh.fetchcodingexercise.models.Item

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id && oldItem.listId == newItem.listId &&
                    oldItem.name == newItem.name
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData( itemList: List<Item>){
        asyncListDiffer.submitList(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.nameTv.text = asyncListDiffer.currentList[position].name
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class ViewHolder(val binding: ItemListRowBinding) : RecyclerView.ViewHolder(binding.root)
}