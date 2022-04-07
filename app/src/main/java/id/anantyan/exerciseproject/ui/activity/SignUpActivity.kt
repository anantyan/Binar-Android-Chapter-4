package id.anantyan.exerciseproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.databinding.ActivitySignUpBinding
import id.anantyan.utils.Constant.PASSING_TO_SIGN_UP_ACTIVITY
import id.anantyan.utils.Validation.emailValid
import id.anantyan.utils.Validation.generalValid
import id.anantyan.utils.Validation.passwordValid
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val preferences: PreferenceHelper by lazy { PreferenceManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setTitle(R.string.page_sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        onBinding()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (onCheckIntent()) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
    }

    private fun onBinding() {
        binding.btnContinue.setOnClickListener {
            onValidation(this)
        }
    }

    private fun onCheckIntent(): Boolean {
        return intent.hasExtra(PASSING_TO_SIGN_UP_ACTIVITY)
    }

    private fun onValidation(context: Context) {
        validator(context) {
            mode = Mode.CONTINUOUS
            listener = object : Validator.OnValidateListener {
                override fun onValidateSuccess(values: List<String>) {
                    when {
                        preferences.getFullname() == binding.txtInputLayoutFullName.text.toString() -> {
                            onSnackbar(binding.root, getString(R.string.txt_found_full_name))
                        }
                        preferences.getProfession() == binding.txtInputLayoutPekerjaan.text.toString() -> {
                            onSnackbar(binding.root, getString(R.string.txt_found_profession))
                        }
                        preferences.getEmail() == binding.txtInputLayoutEmail.text.toString() -> {
                            onSnackbar(binding.root, getString(R.string.txt_found_email))
                        }
                        else -> {
                            preferences.setFullname(binding.txtInputLayoutFullName.text.toString())
                            preferences.setProfession(binding.txtInputLayoutPekerjaan.text.toString())
                            preferences.setEmail(binding.txtInputLayoutEmail.text.toString())
                            preferences.setPassword(binding.txtInputLayoutPassword.text.toString())
                            onBackPressed()
                        }
                    }
                }

                override fun onValidateFailed(errors: List<String>) {}
            }
            validate(
                generalValid(binding.txtLayoutFullName),
                generalValid(binding.txtLayoutPekerjaan),
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    private fun onToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(viewContext: View, message: String) {
        Snackbar.make(viewContext, message, Snackbar.LENGTH_LONG).show()
    }
}