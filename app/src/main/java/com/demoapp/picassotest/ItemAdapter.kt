package com.demoapp.picassotest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoapp.picassotest.databinding.RecyclerViewItemBinding
import com.squareup.picasso.Picasso

class ItemAdapter(val context: Context, val itemsList: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemsList[position]
        with(holder) {
            binding.apply {
                creatorTextView.text = item.creator
                likesTextView.text = context.getString(R.string.likes, item.likes)
                Picasso.get().load(item.imageUrl).fit().centerInside().into(itemImageView)
            }
        }
    }
}