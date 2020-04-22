package com.rumi.navigationcomponentdemo

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.todayFragment, R.id.cart_fragment, R.id.leave_request_fragment),
            drawerLayout
        )

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            checkIfDrawerNeedsToBeLock(destination.id)
        }
        setupNavigationMenu(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    // similar to onbackpressed
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when (menu.itemId) {
            R.id.today_fragment -> {
                // Pops today fragment if today menu is pressed multiple times
                navController.popBackStack()
                navController.navigate(R.id.todayFragment)
            }
            R.id.leave_request_fragment -> {
                navController.navigate(R.id.leave_request_fragment)
            }
            R.id.login_fragment -> {
                navController.popBackStack()
                navController.navigate(R.id.login_fragment)
            }
        }
        return true
    }

    private fun checkIfDrawerNeedsToBeLock(fragmentId: Int) {
        if (fragmentId in appBarConfiguration.topLevelDestinations)
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
}