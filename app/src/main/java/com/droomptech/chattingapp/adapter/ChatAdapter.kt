package com.droomptech.chattingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droomptech.chattingapp.R
import com.droomptech.chattingapp.activity.ChatActivity
import com.droomptech.chattingapp.databinding.ChatUserItemBinding
import com.droomptech.chattingapp.model.UserModel

class ChatAdapter(var context : Context, var list : ArrayList<UserModel>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding : ChatUserItemBinding = ChatUserItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_user_item, parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.userImage)
        holder.binding.userName.text = user.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("uid", user.uid)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}