package co.touchlab.kampstarter.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.touchlab.kampstarter.db.Breed
import co.touchlab.kampstarter.isFavorited
import co.touchlab.kampstarter.models.BreedModel

class MainAdapter : ListAdapter<Breed, MainViewHolder>(breedCallback) {

    var data:List<Breed> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.breed_name_text_view)
    private val favoriteButton = itemView.findViewById<ImageButton>(R.id.favorite_button)

    fun bindTo(breed:Breed){
        nameTextView.text = breed.name
        if(breed.favorite == 0L){
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_24px)
        }else{
            favoriteButton.setBackgroundResource(R.drawable.ic_favorite_24px)
        }
        favoriteButton.setOnClickListener {
            val isFavorite = !breed.isFavorited()
            BreedModel{}.updateBreedFavorite(breed.id,isFavorite)
        }
    }
}

private val breedCallback = object : DiffUtil.ItemCallback<Breed>(){
    override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
        oldItem.id == newItem.id
}