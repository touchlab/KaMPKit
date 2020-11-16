package co.touchlab.kampkit.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.touchlab.kampkit.android.databinding.ItemBreedBinding
import co.touchlab.kampkit.db.Breed

class MainAdapter(private val breedClickListener: (Breed) -> Unit) :
    ListAdapter<Breed, MainViewHolder>(breedCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding) {
            breedClickListener(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val breedCallback = object : DiffUtil.ItemCallback<Breed>() {
            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem == newItem

            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem.id == newItem.id
        }
    }
}
