package io.raveerocks.bookstore.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.raveerocks.bookstore.ActivityViewModel
import io.raveerocks.bookstore.AppState
import io.raveerocks.bookstore.R
import io.raveerocks.bookstore.databinding.FragmentLoginBinding
import timber.log.Timber


class LoginFragment : Fragment() {

    data class Status(val valid:Boolean,val message: String)

    private lateinit var binding: FragmentLoginBinding
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activityViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginButton.setOnClickListener(this::login)
        binding.signUpButton.setOnClickListener(this::signUp)
        return binding.root
    }

    private fun login(view: View) {
        Timber.i("Login to the application")
        authenticate(view)
        Timber.i("Login completed")
    }

    private fun signUp(view: View) {
        Timber.i("SignUp to the application")
        authenticate(view)
        Timber.i("SignUp completed")
    }

    private fun authenticate(view: View) {
        Timber.i("Authenticating credentials")
        val status = validate()
        if(status.valid){
            redirect(view)
            Timber.i("Authenticated credentials successfully")
        }else{
            Timber.i("Authentication failed : ${status.message}")
            Toast.makeText(view.context,status.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validate(): Status {
        var status = Status(true, "")
        if(binding.emailEditText.text.toString().isEmpty()){
            status = Status(false, "Email cannot be empty.")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()){
            status = Status(false, "Email is invalid.")
        } else if(binding.passwordEditText.text.toString().isEmpty()){
            status = Status(false, "Password cannot be empty.")
        }
        return status
    }

    private fun redirect(view: View) {
        // Navigate to the welcome screen if login for the first time
        if (activityViewModel.getState() == AppState.LOGIN_NOT_DONE) {
            activityViewModel.setState(AppState.LOGIN_DONE)
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        } else { // Navigate to the listing screen if login done after a logout
            activityViewModel.setState(AppState.INSTRUCTION_DONE)
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToBookListingFragment())
        }
    }

}