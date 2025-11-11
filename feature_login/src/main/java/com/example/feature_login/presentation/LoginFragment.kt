package com.example.feature_login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.feature_login.presentation.LoginNavigator
import com.example.feature_login.presentation.LoginViewModel
import com.example.feature_login.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private var navigator: LoginNavigator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as? LoginNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isButtonEnabled.collect { enabled ->
                    binding.btnLogin.isEnabled = enabled
                }
            }
        }

        binding.etEmail.addTextChangedListener { onFieldsChanged() }
        binding.etPassword.addTextChangedListener { onFieldsChanged() }

        binding.btnLogin.setOnClickListener {
            navigator?.navigateToHome()
        }
        binding.btnVK.setOnClickListener { openUrl("https://vk.com/") }
        binding.btnOK.setOnClickListener { openUrl("https://ok.ru/") }
    }

    private fun onFieldsChanged() {
        viewModel.onFieldsChanged(
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
}
