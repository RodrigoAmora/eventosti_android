package br.com.rodrigoamora.eventosti.ui.activiy

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.databinding.ActivityMainBinding
import br.com.rodrigoamora.eventosti.util.PackageInfoUtil
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navView: NavigationView
    private lateinit var navController: NavController

    private lateinit var navFooterVersion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.configurarBinding()
        this.configurarNavigationView()
        this.amostarVersaoDoApp()
        this.esconderNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sobre -> {
                this.navController.navigate(R.id.nav_sobre)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun configurarBinding() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        setSupportActionBar(this.binding.appBarMain.toolbar)
    }

    private fun configurarNavigationView() {
        val drawerLayout: DrawerLayout = this.binding.drawerLayout

        this.navView = this.binding.navView
        this.navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        this.appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_sobre
            ), drawerLayout
        )

        setupActionBarWithNavController(this.navController, this.appBarConfiguration)
        this.navView.setupWithNavController(this.navController)
        this.navView.itemIconTintList = null
    }

    private fun amostarVersaoDoApp() {
        this.navFooterVersion = this.binding.navFooterVersion
        this.navFooterVersion.text = getString(R.string.versao, PackageInfoUtil.getVersionName(this))
    }

    private fun esconderNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.insetsController?.hide(WindowInsets.Type.navigationBars())
        }
    }

}
