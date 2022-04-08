package id.anantyan.challangechapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import id.anantyan.challangechapter4.databinding.ActivityMainBinding
import id.anantyan.challangechapter4.ui.dialog.NoteDialog
import id.anantyan.challangechapter4.ui.dialog.NoteDialogHelper
import id.anantyan.challangechapter4.ui.login.LoginFragmentDirections
import id.anantyan.challangechapter4.ui.note.NoteFragmentDirections
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val preferences: PreferenceHelper by lazy { PreferenceManager(this) }
    private val dialog: NoteDialogHelper by lazy { NoteDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        navController.addOnDestinationChangedListener(this)
        val appBar = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.noteFragment
            )
        )
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBar)
        binding.toolbar.setOnMenuItemClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.toolbar.menu.findItem(R.id.navigation_logout).isVisible = destination.id == R.id.noteFragment
        if (destination.id == R.id.loginFragment) {
            if (preferences.getId() != -1L) {
                controller.navigate(LoginFragmentDirections.actionLoginFragmentToNoteFragment())
            }
        }
        if (destination.id == R.id.noteFragment) {
            destination.label = "Welcome, ${preferences.getUsername()}"
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.navigation_logout -> {
                dialog.dialogLogout {
                    preferences.setId(-1L)
                    preferences.setUsername("")
                    navController.navigate(NoteFragmentDirections.actionNoteFragmentToLoginFragment())
                    it.dismiss()
                }
                true
            }
            else -> false
        }
    }
}