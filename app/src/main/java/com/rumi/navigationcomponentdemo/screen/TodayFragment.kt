package com.rumi.navigationcomponentdemo.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.rumi.navigationcomponentdemo.R
import com.rumi.navigationcomponentdemo.databinding.FragmentTodayBinding
import com.rumi.navigationcomponentdemo.model.SkuModel

class TodayFragment : Fragment() {

    val sku = SkuModel(1, "Tiger Biscuit", 10f)
    lateinit var binding: FragmentTodayBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_today, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar);
        val drawerLayout: DrawerLayout? = activity?.findViewById(R.id.drawer_layout)
        val navController = NavHostFragment.findNavController(this);
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.todayFragment,
                R.id.cart_fragment,
                R.id.leave_request_fragment
            ),
            drawerLayout
        )
        setupActionBarWithNavController(activity as AppCompatActivity, navController, appBarConfiguration)

        childFragmentManager.beginTransaction().replace(
            R.id.fragment,
            DashboardFragment()
        ).commit()
        binding.bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.today_fragment -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.fragment,
                        DashboardFragment()
                    ).commit()

                    true
                }
                R.id.cart_fragment -> {
                    childFragmentManager.beginTransaction().replace(
                        R.id.fragment,
                        CartFragment()
                    ).commit()

                    true
                }
                else -> {
                    true
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }
}
