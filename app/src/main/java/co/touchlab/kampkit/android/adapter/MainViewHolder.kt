package co.touchlab.kampkit.android.adapter

import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampkit.android.R
import co.touchlab.kampkit.android.databinding.ItemBreedBinding
import co.touchlab.kampkit.db.Breed

class MainViewHolder(binding: ItemBreedBinding, breedClickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.breedNameTextView
    private val favoriteButton = binding.favoriteButton

    init {
        favoriteButton.setOnClickListener { breedClickListener(adapterPosition) }
    }

    fun bindTo(breed: Breed) {
        nameTextView.text = breed.name
        if (breed.favorite == 0L) {
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_24px)
        } else {
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_24px)
        }
    }
}
