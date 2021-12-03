package com.rikkei.awesomechat.ui.register

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import com.rikkei.awesomechat.R
import com.rikkei.awesomechat.base.BaseFragment
import com.rikkei.awesomechat.databinding.FragmentRegisterBinding
import com.rikkei.awesomechat.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {

    override val layoutResource: Int
        get() = R.layout.fragment_register
    override val viewModel: RegisterViewModel by viewModels()

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RegisterFragment.viewModel
        }
    }

    override fun initEvents() {
        with(viewBinding) {
            listOf(buttonRegister, ivCheck, textLoginNow).forEach {
                it.setOnClickListener(this@RegisterFragment)
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it.uid?.let { uid ->
                if (uid.isNotBlank() && uid.isNotEmpty()) {
                    registerSuccess(
                        viewBinding.editEmail.text.toString().trim(),
                        viewBinding.editPassword.text.toString().trim()
                    )
                } else {
                    context?.showToast(getString(R.string.error_something_wrong))
                }
            }
        }

        viewModel.validateUser(::invalidSuccess, ::invalidFailed)
    }

    private fun registerSuccess(email: String, password: String) {

        context?.showToast(getString(R.string.success_register_login))
//        callBack?.callBack(email, password)
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun invalidSuccess() {

    }

    private fun invalidFailed() {
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(view: View?) {
        view?.let {
            when (it) {
                viewBinding.buttonRegister -> register()
                viewBinding.textLoginNow -> openLoginFragment()
                viewBinding.ivCheck -> handleStateButton()
            }
        }
    }


    private fun handleStateButton() {

        viewBinding.ivCheck.isSelected = !viewBinding.ivCheck.isSelected

        if (viewBinding.ivCheck.isSelected) {
            viewBinding.buttonRegister.isEnabled = true
            context?.getColor(R.color.san_marino)
                ?.let { viewBinding.buttonRegister.background.setTint(it) }
        } else {
            viewBinding.buttonRegister.isEnabled = false
            viewBinding.buttonRegister.background.setTint(resources.getColor(R.color.silver))
        }
    }

    private fun register() {
        viewModel.apply {
            setUser(
                viewBinding.editName.text.toString(),
                viewBinding.editEmail.text.toString(),
                viewBinding.editPassword.text.toString()
            )
            register()
        }
    }

    private fun openLoginFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initViews() {
        viewBinding.ivCheck.setImageDrawable(context?.resources?.getDrawable(R.drawable.bg_ic_state))
    }

}