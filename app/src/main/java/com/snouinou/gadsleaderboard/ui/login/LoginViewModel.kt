package com.snouinou.gadsleaderboard.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snouinou.gadsleaderboard.R
import com.snouinou.gadsleaderboard.api.ApiServiceBuilder
import com.snouinou.gadsleaderboard.api.FormSubmitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(firstName: String, lastName: String, email: String, githubUrl: String ) {

        // form submit
        val submitServiceService: FormSubmitApiService =
            ApiServiceBuilder.buildApiService(FormSubmitApiService::class.java)

        val submitFormData: Call<Void?>? = submitServiceService.submitForm(
            "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse",
            firstName,
            lastName,
            email,
            githubUrl
        )

        submitFormData!!.enqueue(object : Callback<Void?> {
            override fun onResponse(
                call: Call<Void?>,
                response: Response<Void?>
            ) {
                if (response.isSuccessful) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(response = "success"))
                } else {
                    _loginResult.value = LoginResult(error = R.string.submit_failed)
                }
            }

            override fun onFailure(
                call: Call<Void?>,
                t: Throwable
            ) {
                _loginResult.value = LoginResult(error = R.string.submit_failed)
            }
        })
    }

    fun loginDataChanged(firstName: String, lastName: String, email: String, githubUrl: String) {
        if (!isFirstNameValid(firstName)) {
            _loginForm.value = LoginFormState(firstNameError = R.string.invalid_first_name)
        } else if (!isLastNameValid(lastName)) {
            _loginForm.value = LoginFormState(lastNameError = R.string.invalid_last_name)
        }
        else if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        }
        else if (!isUrlValid(githubUrl)) {
            _loginForm.value = LoginFormState(githubUrlError = R.string.invalid_github_url)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder email validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder email validation check
    private fun isUrlValid(url: String): Boolean {
        return if (url.contains('.')) {
            Patterns.WEB_URL.matcher(url).matches()
        } else {
            url.isNotBlank()
        }
    }

    // A placeholder first name validation check
    private fun isFirstNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    // A placeholder last name validation check
    private fun isLastNameValid(name: String): Boolean {
        return name.isNotBlank()
    }
}