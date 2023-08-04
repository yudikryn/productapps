package com.yudikryn.productapps.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.yudikryn.productapps.R

class ChatAdapter(private val messageList: List<Message>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messageList[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].messageType.viewType
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.messageTextView)
        private val reactionMessage: ImageView = itemView.findViewById(R.id.ivReaction)

        fun bind(message: Message) {
            textMessage.text = message.text

            if (message.reaction != null){
                reactionMessage.visibility = View.VISIBLE
            }else{
                reactionMessage.visibility = View.GONE
            }

            message.reaction?.let {
                reactionMessage.setImageResource(getReactionIconResource(it))
            }

            itemView.setOnClickListener {
                showReactionDialog(itemView.context, adapterPosition)
            }
        }

        private fun showReactionDialog(context: Context, position: Int) {
            val message = messageList[position]

            val reactions = arrayOf(
                Reaction.LOVE,
                Reaction.SMILE,
                Reaction.LAUGHTER,
            )

            val reactionNames = reactions.map { it.name }.toTypedArray()

            val builder = AlertDialog.Builder(context)
            builder.setItems(reactionNames) { _, which ->
                message.reaction = reactions[which]
                notifyItemChanged(position)
            }

            builder.show()
        }

        private fun getReactionIconResource(reaction: Reaction): Int {
            return when (reaction) {
                Reaction.LOVE -> R.drawable.ic_love
                Reaction.SMILE -> R.drawable.ic_smile
                Reaction.LAUGHTER -> R.drawable.ic_laughter
            }
        }
    }
}