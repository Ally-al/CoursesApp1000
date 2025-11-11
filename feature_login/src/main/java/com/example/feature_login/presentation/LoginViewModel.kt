package com.example.feature_login.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    fun onFieldsChanged(email: String, password: String) {
        val emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val isEmailValid = email.matches(emailPattern.toRegex())
        val isPasswordFilled = password.isNotEmpty()
        _isButtonEnabled.value = isEmailValid && isPasswordFilled
    }
}
