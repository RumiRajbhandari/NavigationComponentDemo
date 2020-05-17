package com.rumi.navigationcomponentdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.navigation.NavigationView
import com.rumi.navigationcomponentdemo.data.SharedPreferenceManager
import com.rumi.navigationcomponentdemo.databinding.ActivityMainBinding
import com.rumi.navigationcomponentdemo.databinding.LayoutBadgeBinding
import com.rumi.navigationcomponentdemo.databinding.NavHeaderMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_badge.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    private val sharedPreference by lazy { SharedPreferenceManager(this) }
    var notificationsBadge : LayoutBadgeBinding?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_fragment, R.id.cart_fragment, R.id.leave_request_fragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        setupNavigationMenu()
        setUpDestinationChangeListener()
        binding.bottomNavView.setupWithNavController(navController)
        binding.navView.menu.findItem(R.id.leave_request_fragment).setActionView(R.layout.item_custom_menu)

        val navRootView = binding.navView.getHeaderView(0)
        val navHeaderMainBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(navRootView)
        navHeaderMainBinding.imgNightmode.setOnClickListener {
            handleNightMode()
        }

        addBadge(111.toString())

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    // similar to onbackpressed
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun setupNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setUpDestinationChangeListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            checkIfDrawerNeedsToBeLock(destination.id)
            if (destination.id in appBarConfiguration.topLevelDestinations) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)
            }
        }
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when (menu.itemId) {
            R.id.home_fragment -> {
                // Pops today fragment if today menu is pressed multiple times
                navController.popBackStack()
                navController.navigate(R.id.home_fragment)
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

    private fun handleNightMode() {
        val isNightModeOn = sharedPreference.nightModeStatus
        sharedPreference.nightModeStatus = !isNightModeOn
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun addBadge(count : String) {
        getBadge()
        notificationsBadge?.notificationsBadge?.text = count
        binding.bottomNavView.addView(notificationsBadge?.root)
    }

    private fun getBadge() : LayoutBadgeBinding {
        if (notificationsBadge != null){
            return notificationsBadge!!
        }
        val child = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        notificationsBadge = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_badge, child, false)
        return notificationsBadge!!
    }
}