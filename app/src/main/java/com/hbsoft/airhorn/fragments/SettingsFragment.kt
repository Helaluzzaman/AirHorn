package com.hbsoft.airhorn.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hbsoft.airhorn.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}