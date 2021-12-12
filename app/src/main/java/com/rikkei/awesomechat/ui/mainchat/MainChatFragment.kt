package com.rikkei.awesomechat.ui.mainchat

import androidx.fragment.app.viewModels
import com.rikkei.awesomechat.R
import com.rikkei.awesomechat.base.BaseFragment
import com.rikkei.awesomechat.databinding.FragmentMainChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainChatFragment : BaseFragment<FragmentMainChatBinding>() {
    override val layoutResource: Int
        get() = R.layout.fragment_main_chat
    override val viewModel: MainChatViewModel by viewModels()

    override fun initViews() {

    }

    override fun initData() {

    }

    override fun initEvents() {

    }
}