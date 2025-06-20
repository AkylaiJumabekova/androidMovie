package com.azamovme.MoviePlayerAkylai.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azamovme.MoviePlayerAkylai.ui.screens.favorite.FavoriteScreen
import com.azamovme.MoviePlayerAkylai.ui.screens.home.HomeScreen
import com.azamovme.MoviePlayerAkylai.ui.screens.trending.TrendingScreen

class BottomNavigationAdapter(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteScreen()
            1 -> HomeScreen()
            else -> {
                TrendingScreen()
            }
        }
    }
}