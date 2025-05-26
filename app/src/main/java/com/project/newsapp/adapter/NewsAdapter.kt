package com.project.newsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsapp.R
import com.project.newsapp.models.Article
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class NewsAdapter
    : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(ArticleDiffCallback()) {


        // declare setOnItemClicklistener here
        private var onItemClickListener: ((Article) -> Unit)? = null
        fun setOnItemClickListener(listener: (Article) -> Unit) {
                onItemClickListener = listener
        }



        //long click listener for saving articles
        private var onItemLongClickListener: ((Article) -> Unit)? = null

        fun setOnItemLongClickListener(listener: (Article) -> Unit) {
            onItemLongClickListener = listener
        }

          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            return ArticleViewHolder(view,onItemClickListener,onItemLongClickListener)
          }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    // ViewHolder class
    class ArticleViewHolder(itemView: View,private val onItemClickListener: ((Article) -> Unit)?,private var onItemLongClickListener: ((Article) -> Unit)?) : RecyclerView.ViewHolder(itemView) {

        private lateinit var urlToImage: ImageView
        private lateinit var authorNameText: TextView
        private lateinit var titleText: TextView
        private lateinit var descriptionText: TextView
        private lateinit var sourceIcon: ImageView // TODO: Add source icon
        private lateinit var sourceNameText: TextView
        private lateinit var publishedAtTimeText: TextView

        @SuppressLint("SetTextI18n")
        fun bind(article: Article) {

            urlToImage = itemView.findViewById(R.id.newsUrlImage)
            authorNameText = itemView.findViewById(R.id.authorNameText)
            titleText = itemView.findViewById(R.id.titleText)
            descriptionText = itemView.findViewById(R.id.descriptionText)
            sourceIcon = itemView.findViewById(R.id.sourceIcon)
            sourceNameText = itemView.findViewById(R.id.sourceNameText)
            publishedAtTimeText = itemView.findViewById(R.id.publishedAtTimeText)

            //use Glide to load the image
            Glide.with(itemView)
                .load(article.urlToImage)
                .into(urlToImage)

            authorNameText.text = article.author
            titleText.text = article.title
            descriptionText.text = article.description
            sourceNameText.text = article.source?.name
            // Convert and format publishedAt date
            publishedAtTimeText.text = formatPublishedDate(article.publishedAt)


            itemView.setOnClickListener(){
                onItemClickListener?.invoke(article)

            }

            // Set a long click listener for saving articles
            itemView.setOnLongClickListener {
                onItemLongClickListener?.invoke(article)
                true
            }









        }



        private fun formatPublishedDate(publishedAt: String?): String {
            if (publishedAt.isNullOrEmpty()) return "Unknown date"

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Parse UTC time

            val outputFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())

            return try {
                val date = inputFormat.parse(publishedAt)
                if (date != null) outputFormat.format(date) else "Unknown date"
            } catch (e: Exception) {
                "Unknown date"
            }
        }



    }

    // DiffUtil class to efficiently update the list
    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url // Assuming Article has a unique url field
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem // Compare all properties
        }
    }

}