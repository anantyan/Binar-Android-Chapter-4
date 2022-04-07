package id.anantyan.exerciseproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.databinding.ActivitySignInBinding
import id.anantyan.exerciseproject.databinding.DialogExampleBinding
import id.anantyan.exerciseproject.ui.fragment.DialogExampleFragment
import id.anantyan.exerciseproject.ui.fragment.DialogExampleFragment.Companion.ARG_DIALOG_EXAMPLE
import id.anantyan.utils.Constant.PASSING_TO_SIGN_UP_ACTIVITY
import id.anantyan.utils.Validation.emailValid
import id.anantyan.utils.Validation.passwordValid
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val preferences: PreferenceHelper by lazy { PreferenceManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBinding()
    }

    private fun onBinding() {
        binding.txtLayoutSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra(PASSING_TO_SIGN_UP_ACTIVITY, true)
            startActivity(intent)
        }
        binding.btnSignIn.setOnClickListener {
            onValidation(this, onSignIn)
        }
        binding.textView5.setOnClickListener {
            onToast(this, "Forgot password!")
        }
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
            when {
                preferences.getEmail() != binding.txtInputLayoutEmail.text.toString() -> {
                    onSnackbar(binding.root, getString(R.string.txt_not_found_email))
                }
                preferences.getPassword() != binding.txtInputLayoutPassword.text.toString() -> {
                    onSnackbar(binding.root, getString(R.string.txt_not_found_password))
                }
                else -> {
                    preferences.setLogIn(true)
                    val intent = Intent(this@SignInActivity, BaseFragmentActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    /**
     * Materi terkait toast & dialog*/
    private fun onToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(viewContext: View, message: String) {
        Snackbar.make(viewContext, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Materi terkait dialog*/
    private fun onDialog(context: Context, title: String, message: String, listener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Yes") { _, _ ->
            listener.invoke()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun onDialogCustom(context: Context, title: String, message: String, listener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogExampleBinding.inflate(LayoutInflater.from(context))
        builder.setTitle(title)
        builder.setView(binding.root)
        builder.setCancelable(false)
        val dialog = builder.show()
        binding.txtView.text = message
        binding.btnPositif.setOnClickListener {
            listener.invoke()
        }
        binding.btnNegatif.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun onDialogFragment(title: String, message: String, listener: () -> Unit) {
        val dialog = DialogExampleFragment()
        dialog.show(supportFragmentManager, ARG_DIALOG_EXAMPLE)
    }
}

