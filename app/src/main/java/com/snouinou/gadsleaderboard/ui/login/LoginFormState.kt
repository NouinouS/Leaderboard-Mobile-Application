package com.snouinou.gadsleaderboard.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val emailError: Int? = null,
    val githubUrlError: Int? = null,
    val isDataValid: Boolean = false
)