package com.hbsoft.airhorn.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hbsoft.airhorn.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_about, container, false)
        val privacyPolicy = view.findViewById<TextView>(R.id.tv_privacyPolicy)
        privacyPolicy.movementMethod = LinkMovementMethod.getInstance()
        return view
    }
}