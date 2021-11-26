package com.djumabaevs.gochipapp.login.token

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * Provides access to android resources.
 */
interface ResourceProvider {

    /**
     * Return a string from the given string resource.
     */
    fun getString(@StringRes stringRes: Int): String

    /**
     * Return a string with an optional list of format arguments from the given string resource.
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String

    /**
     * Return a quantity string from the given plural string resource.
     */
    fun getQuantityString(@PluralsRes resId: Int, quantity: Int): String

    /**
     * Get a drawable from the given resource.
     */
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable?

    /**
     * Get a color from the given color resource.
     */
    fun getColor(@ColorRes colorRes: Int): Int
}