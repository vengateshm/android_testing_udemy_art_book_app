package dev.vengateshm.artbookappandroidtestingudemy.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dev.vengateshm.artbookappandroidtestingudemy.R
import dev.vengateshm.artbookappandroidtestingudemy.roomdb.Art
import javax.inject.Inject

class ArtListAdapter @Inject constructor(
    private val glide: RequestManager
) :
    RecyclerView.Adapter<ArtListAdapter.ArtListItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.art_row, parent, false)
        return ArtListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtListItemViewHolder, position: Int) {
        val ivArtImage = holder.itemView.findViewById<ImageView>(R.id.ivArtImage)
        val tvArtName = holder.itemView.findViewById<TextView>(R.id.tvArtName)
        val tvArtistName = holder.itemView.findViewById<TextView>(R.id.tvArtistName)
        val tvArtYear = holder.itemView.findViewById<TextView>(R.id.tvArtYear)

        val art = arts[position]
        tvArtName.text = "Name: ${art.name}"
        tvArtistName.text = "Artist Name: ${art.artistName}"
        tvArtYear.text = "Year: ${art.year}"
        glide.load(art.imageUrl).into(ivArtImage)
    }

    var arts: List<Art>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    // DiffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    // ViewHolder
    class ArtListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}