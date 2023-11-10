package dev.vengateshm.artbookappandroidtestingudemy.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dev.vengateshm.artbookappandroidtestingudemy.R
import javax.inject.Inject

class ImageListAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageListAdapter.ImageListItemViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: ((String) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_row, parent, false)
        return ImageListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageListItemViewHolder, position: Int) {
        val ivImage = holder.itemView.findViewById<ImageView>(R.id.ivImage)
        val imageUrl = images[position]
        glide.load(imageUrl).into(ivImage)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(imageUrl)
                }
            }
        }
    }

    var images: List<String>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    // DiffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    // ViewHolder
    class ImageListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}