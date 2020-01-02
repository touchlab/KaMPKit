package co.touchlab.kampstarter.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.touchlab.kampstarter.android.R
import co.touchlab.kampstarter.db.Breed

class MainAdapter(private val breedClickListener: (Breed) -> Unit) :
    ListAdapter<Breed, MainViewHolder>(breedCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return MainViewHolder(view) {
            breedClickListener(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val breedCallback = object : DiffUtil.ItemCallback<Breed>() {
            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                (oldItem.id == newItem.id) &&
                        (oldItem.favorite == newItem.favorite)

            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                oldItem.id == newItem.id
        }
    }
}

