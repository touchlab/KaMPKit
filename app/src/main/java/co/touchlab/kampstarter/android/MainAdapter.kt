package co.touchlab.kampstarter.android

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.jsondata.Breed

class MainAdapter : ListAdapter<Breed, MainViewHolder>(breedCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        //nameTextView = itemView.findViewById(R.id.contact_name) as TextView
        //messageButton = itemView.findViewById(R.id.message_button) as Button
    }

    fun bindTo(breed:Breed){

    }
}

private val breedCallback = object : DiffUtil.ItemCallback<Breed>(){
    override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
        oldItem.id == newItem.id
}