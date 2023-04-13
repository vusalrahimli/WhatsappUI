package com.example.whatsappui.ui.homepage.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.whatsappui.data.model.homepage.ContactsModel
import com.example.whatsappui.databinding.FragmentChatsBinding
import com.example.whatsappui.ui.homepage.HomePageFragmentDirections
import com.example.whatsappui.ui.homepage.chats.adapters.chatsadapter.ChatsAdapter
import com.example.whatsappui.ui.homepage.chats.adapters.messagesadapter.MessagesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment : Fragment() {

    private val binding by lazy {
        FragmentChatsBinding.inflate(layoutInflater)
    }

    private val viewModel: ChatsVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservables()
        setClicks()
    }

    private fun subscribeToObservables() {

        val contact1 = ContactsModel(1, "Vusal", "Rahimli")
        val contact2 = ContactsModel(2, "Matin", "Imanzade")
        val contact3 = ContactsModel(3, "Khayal", "Farajov")
        val contact4 = ContactsModel(4, "Huseyn", "Ahadov")

        viewModel.insertContact(contact1)
        viewModel.insertContact(contact2)
        viewModel.insertContact(contact3)
        viewModel.insertContact(contact4)

        viewModel.fetchContacts().observe(viewLifecycleOwner) {
            adapterChats.submitList(it)
            adapterMessages.submitList(it)
        }
    }

    private fun setClicks() {
        adapterMessages.setOnItemClick = {
            findNavController().navigate(
                HomePageFragmentDirections.actionHomePageFragmentToInboxFragment(it),
            )
        }
    }

    private val adapterChats by lazy {
        ChatsAdapter().also {
            binding.rvChats.adapter = it
        }
    }

    private val adapterMessages by lazy {
        MessagesAdapter().also {
            binding.rvMessages.adapter = it
        }
    }
}
