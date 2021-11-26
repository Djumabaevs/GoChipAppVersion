package com.djumabaevs.gochipapp.login.token

val DEFAULT_LOCALE = AppLocale.RU

enum class AppLocale(val code: String, val title: String) {
    RU("ru", "Русский"),
    EN("en", "English");

    companion object {
        fun isSupported(code: String): Boolean {
            return values().find { it.code == code } != null
        }

        fun fromCode(code: String): AppLocale {
            return when (code) {
                RU.code -> RU
                EN.code -> EN
                else -> throw IllegalArgumentException("Locale code '$code' is not supported")
            }
        }
    }
}