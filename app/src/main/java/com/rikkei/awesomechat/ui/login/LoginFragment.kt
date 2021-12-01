package com.rikkei.awesomechat.ui.login

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.rikkei.awesomechat.R
import com.rikkei.awesomechat.base.BaseFragment
import com.rikkei.awesomechat.databinding.FragmentLoginBinding
import com.rikkei.awesomechat.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    override val layoutResource get() = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun initViews() {

    }

    override fun initData() {
        with(viewBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }
    }

    override fun initEvents() {
        with(viewBinding) {
            listOf(
                buttonLogin,
                textForgetPassword,
                textSignupNow,
            ).forEach {
                it.setOnClickListener(this@LoginFragment)
            }

            editEmail.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && !editPassword.text.isNullOrBlank()) {
                    changeButtonStatus(true)
                } else {
                    changeButtonStatus(false)
                }
            }

            editPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank() && !editEmail.text.isNullOrBlank()) {
                    changeButtonStatus(true)
                } else {
                    changeButtonStatus(false)
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it.uid?.let { uid ->
                if (uid.isNotBlank() && uid.isNotEmpty()) {
                    loginSuccess()
                } else {
                    context?.showToast(getString(R.string.error_something_wrong))
                }
            }
        }

        viewModel.validateUser(::openChatFragment)
    }

    override fun onClick(v: View?): Unit = with(viewBinding) {
        when (v) {
            buttonLogin -> login()
            textForgetPassword -> openForgotPasswordFragment()
            textSignupNow -> openRegisterFragment()
        }
    }

    private fun changeButtonStatus(isEnable: Boolean) = with(viewBinding) {
        if (isEnable) {
            buttonLogin.isEnabled = true
            context?.getColor(R.color.san_marino)?.let { buttonLogin.background.setTint(it) }
        } else {
            buttonLogin.isEnabled = false
            context?.getColor(R.color.silver)?.let { buttonLogin.background.setTint(it) }
        }
    }

    private fun login() {
        viewModel.apply {
            setUser(viewBinding.editEmail.text.toString(), viewBinding.editPassword.text.toString())
            login()
        }
    }

    private fun openChatFragment() {

    }

    private fun openForgotPasswordFragment() {

    }

    private fun openRegisterFragment() {

    }

    private fun loginSuccess() {

    }
}
