package com.djumabaevs.gochipapp.login

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter(@NonNull fm: FragmentManager, behavior: Int, private val numberOfTabs: Int) :
    FragmentPagerAdapter(fm, behavior) {
    @NonNull
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()

            else -> null!!
        }
    }

    override fun getCount(): Int {
        return numberOfTabs
    }
}