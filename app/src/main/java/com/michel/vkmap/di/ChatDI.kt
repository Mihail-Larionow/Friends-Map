package com.michel.vkmap.di

import com.michel.vkmap.presentation.chat.conversation.ConversationViewModel
import com.michel.vkmap.presentation.chat.dialogs.DialogsViewModel
import com.michel.vkmap.presentation.chat.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatDI = module {
    viewModel<UsersViewModel>{
        UsersViewModel(
            getFriendsListUseCase = get(),
            getUserInfoUseCase = get(),
            getNetworkStateUseCase = get()
        )
    }

    viewModel<DialogsViewModel>{
        DialogsViewModel(
            getConversationsListUseCase = get(),
            getConversationInfoUseCase = get(),
            getUserInfoUseCase = get(),
            getMessageUseCase = get(),
            getNetworkStateUseCase = get(),
            getMessagesListUseCase = get()
        )
    }

    viewModel<ConversationViewModel>{
        ConversationViewModel(
            sendMessageUseCase = get(),
            getConversationInfoUseCase = get(),
            getUserInfoUseCase = get(),
            getMessageUseCase = get(),
            createConversationUseCase = get(),
            getNetworkStateUseCase = get(),
            getMessagesListUseCase = get()
        )
    }
}