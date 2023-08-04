package com.yudikryn.productapps.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudikryn.productapps.databinding.FragmentChatBinding

class ChatFragment : Fragment(){

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messageList = mutableListOf<Message>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter(messageList)

        binding.rvChat.adapter = chatAdapter
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())

        addMessage(Message("Hi, how are you?", MessageType.MY_MESSAGE))
        addMessage(Message( "Hello! I'm good, thank you.", MessageType.THEIR_MESSAGE))


        binding.rvChat.scrollToPosition(messageList.size - 1)
        binding.ivSend.setOnClickListener {
            if (binding.etTyping.text.toString().isNotEmpty()){
                addMessage(message = Message(text = binding.etTyping.text.toString(), messageType = MessageType.MY_MESSAGE))
                binding.etTyping.text = null
            }
        }
    }



    private fun addMessage(message: Message) {
        messageList.add(message)
        chatAdapter.notifyItemInserted(messageList.size - 1)
    }
}