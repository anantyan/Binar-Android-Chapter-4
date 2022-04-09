package id.anantyan.challangechapter4.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import id.anantyan.challangechapter4.MainActivity
import id.anantyan.challangechapter4.R
import id.anantyan.challangechapter4.databinding.FragmentLoginBinding
import id.anantyan.challangechapter4.model.Users
import id.anantyan.utils.emailValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private val preferences: PreferenceHelper by lazy { PreferenceManager((context as MainActivity)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            onValidation((context as MainActivity), onSignIn)
        }
        binding.txtRegister.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(destination)
        }
        onObserverData(view)
    }

    private fun onSelectByEmailAndPasswordData() {
        val item = Users(
            email = binding.txtInputEmail.text.toString().trim(),
            password = binding.txtInputPassword.text.toString().trim()
        )
        viewModel.selectByEmailAndPassword(item)
    }

    private fun onObserverData(view: View) {
        viewModel.users.observe(viewLifecycleOwner) {
            it?.let { item ->
                preferences.setId(item.id!!)
                preferences.setUsername(item.username!!)
            }
        }
        viewModel.selectByEmailAndPassword.observe(viewLifecycleOwner) {
            if (it != null) {
                onSnackbar(binding.root, it)
            } else {
                val destination = LoginFragmentDirections.actionLoginFragmentToNoteFragment()
                view.findNavController().navigate(destination)
            }
        }
    }

    private fun onSnackbar(viewContext: View, message: String) {
        Snackbar.make(viewContext, message, Snackbar.LENGTH_LONG).show()
    }

    private fun onValidation(context: Context, obj: Validator.OnValidateListener) {
        validator(context) {
            mode = Mode.CONTINUOUS
            listener = obj
            validate(
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    private val onSignIn = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            onSelectByEmailAndPasswordData()
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}