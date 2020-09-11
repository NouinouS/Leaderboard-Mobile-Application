package com.snouinou.gadsleaderboard.ui.login

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.snouinou.gadsleaderboard.R

class SubmitFormActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var submitDialog: Dialog
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var githubUrl: EditText
    private lateinit var login: Button
    private lateinit var loading: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_submit_form)

        firstName = findViewById<EditText>(R.id.first_name)
        lastName = findViewById<EditText>(R.id.last_name)
        email = findViewById<EditText>(R.id.email)
        githubUrl = findViewById<EditText>(R.id.github_url)

        login = findViewById<Button>(R.id.submit_form)
        loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@SubmitFormActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.firstNameError != null) {
                firstName.error = getString(loginState.firstNameError)
            }
            if (loginState.lastNameError != null) {
                lastName.error = getString(loginState.lastNameError)
            }
            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
            if (loginState.githubUrlError != null) {
                lastName.error = getString(loginState.githubUrlError)
            }
        })

        loginViewModel.loginResult.observe(this@SubmitFormActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        firstName.afterTextChanged {
            loginViewModel.loginDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                lastName.text.toString(),
                githubUrl.text.toString()
            )
        }

        lastName.afterTextChanged {
            loginViewModel.loginDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                lastName.text.toString(),
                githubUrl.text.toString()
            )
        }

        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                lastName.text.toString(),
                githubUrl.text.toString()
            )
        }

        githubUrl.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    lastName.text.toString(),
                    githubUrl.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            firstName.text.toString(),
                            lastName.text.toString(),
                            lastName.text.toString(),
                            githubUrl.text.toString()
                        )
                }
                false
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {

        // Calls method to create and show success message dialog
        showFeedBackDialog(R.drawable.success_icon, R.string.submission_success)
        resetForm()

        val respMessage = model.response
        // TODO : popup successful
        Toast.makeText(
            applicationContext,
            "submission successful : $respMessage",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        // TODO : popup fail
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    fun showConfirmationDialog(view: View?) {
        submitDialog = Dialog(this)
        submitDialog.setContentView(R.layout.dialog_confirmation)

        val closeIBtn: ImageButton =
            submitDialog.findViewById(R.id.cancel_imageButton)

        closeIBtn.setOnClickListener { submitDialog.cancel() }

        val continueBtn: Button =
            submitDialog.findViewById(R.id.confirm_submit_button)
        continueBtn.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(firstName.text.toString(), lastName.text.toString(), email.text.toString(), githubUrl.text.toString())
         }

        submitDialog.setCanceledOnTouchOutside(false)
        submitDialog.show()
    }

    fun showFeedBackDialog(responseImage: Int, responseText: Int) {

        submitDialog.dismiss()
        submitDialog = Dialog(this@SubmitFormActivity)
        submitDialog.setContentView(R.layout.dialog_feedback)
        val feedBackIcon: ImageView =
            submitDialog.findViewById(R.id.response_imageView)
        val feedBackText: TextView = submitDialog.findViewById(R.id.feedback_desc)

        feedBackIcon.setImageResource(responseImage)
        feedBackText.text = getString(responseText)
        submitDialog.show()
    }

    fun resetForm() {
            firstName.text.clear()
            lastName.text.clear()
            email.text.clear()
            githubUrl.text.clear()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

