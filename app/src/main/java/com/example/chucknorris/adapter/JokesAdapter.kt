package com.example.chucknorris.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorris.R
import com.example.chucknorris.model.Value
import kotlinx.android.synthetic.main.item_joke.view.*

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.ValueViewHolder>() {

    inner class ValueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Value>() {
        override fun areItemsTheSame(oldItem: Value, newItem: Value): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Value, newItem: Value): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        return ValueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_joke,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Value) -> Unit)? = null

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) {
        val joke = differ.currentList[position]
        holder.itemView.apply {
            tvJoke.text = joke.joke
            setOnClickListener {
                onItemClickListener?.let { it(joke) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Value) -> Unit) {
        onItemClickListener = listener
    }

}













