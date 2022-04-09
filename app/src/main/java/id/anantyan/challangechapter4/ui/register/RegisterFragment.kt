package id.anantyan.challangechapter4.ui.register

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
import id.anantyan.challangechapter4.databinding.FragmentRegisterBinding
import id.anantyan.challangechapter4.model.Users
import id.anantyan.challangechapter4.ui.login.LoginFragmentDirections
import id.anantyan.utils.confirmPasswordValid
import id.anantyan.utils.emailValid
import id.anantyan.utils.generalValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            onValidation((context as MainActivity), onSignUp)
        }
        onObserverData()
    }

    private fun onInsertData() {
        val item = Users(
            username = binding.txtInputUsername.text.toString().trim(),
            email = binding.txtInputEmail.text.toString().trim(),
            password = binding.txtInputRePassword.text.toString().trim()
        )
        viewModel.insert(item)
    }

    private fun onObserverData() {
        viewModel.insert.observe(viewLifecycleOwner) {
            onSnackbar(binding.root, it!!)
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
                generalValid(binding.txtLayoutUsername),
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword),
                confirmPasswordValid(binding.txtLayoutRePassword, binding.txtInputPassword.text.toString())
            )
        }
    }

    private val onSignUp = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            onInsertData()
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}