package com.blume.networkoperations

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_view_github.view.*

class JsonRecyclerView(val githubUserLists: ArrayList<GithubUser>, val imageLists :ArrayList<Bitmap>) : RecyclerView.Adapter<JsonViewHolder>(){

    override fun getItemCount(): Int = githubUserLists.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JsonViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.list_item_view_github,parent,false)
        return JsonViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: JsonViewHolder, position: Int) {

        holder.tvRealUserName.text = githubUserLists[position].login
        holder.tvRealUserID.text = githubUserLists[position].id.toString()
        val intScore = githubUserLists[position].score.toInt()
        holder.tvRealScore.text = intScore.toString()
        holder.tvRealProfile.text = githubUserLists[position].html_url

        Picasso.get()
                .load(githubUserLists[position].avatar_url)
                .placeholder(R.drawable.profileplaceholder)
                .error(R.drawable.profileplaceholder)
                .into(holder.tvUserPhoto)

    }

}



class JsonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val tvRealUserName = itemView.tvRealUserName
    val tvRealUserID = itemView.tvRealUserID
    val tvRealScore = itemView.tvRealScore
    val tvRealProfile = itemView.tvRealProfile
    val tvUserPhoto = itemView.ivUserPhoto
}