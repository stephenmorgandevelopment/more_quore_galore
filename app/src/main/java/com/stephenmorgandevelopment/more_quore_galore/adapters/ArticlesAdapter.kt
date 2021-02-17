package com.stephenmorgandevelopment.more_quore_galore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.stephenmorgandevelopment.more_quore_galore.R
import com.stephenmorgandevelopment.more_quore_galore.models.Article
import com.stephenmorgandevelopment.more_quore_galore.models.SimpleClient

class ArticlesAdapter(val inflater: LayoutInflater) : BaseAdapter() {

    private var articlesList: List<Article> = emptyList()


    override fun getCount(): Int {
        return articlesList.size
    }

    override fun getItem(position: Int): Any {
        return articlesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ArticlesAdapter.ArticleHolder
        var view = View(inflater.context)


        if (convertView == null) {
            view = inflater.inflate(R.layout.article_list_item, parent, false)
            holder = ArticleHolder(
                view.findViewById(R.id.headline),
                view.findViewById(R.id.author),
                view.findViewById(R.id.preview)
            )
            view.tag = holder
        } else {
            holder = convertView.tag as ArticlesAdapter.ArticleHolder
        }

        val article = articlesList[position]
        holder.headline.text = article.title
        holder.author.text = article.author
        holder.preview.text = article.description

        return convertView ?: view
    }

    internal fun setArticlesList(articlesList: List<Article>) {
        this.articlesList = articlesList
        notifyDataSetChanged()
    }

    inner class ArticleHolder(
        val headline: TextView,
        val author: TextView,
        val preview: TextView
    )
}