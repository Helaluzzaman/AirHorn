package com.hbsoft.airhorn.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.hbsoft.airhorn.R

const val TAG = "button"

class HomeFragment : Fragment(), View.OnTouchListener {
    lateinit var button: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        button = view.findViewById(R.id.b_press)
        button.setOnTouchListener(this)
        return view
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                Toast.makeText(requireContext(), "started down", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "onTouch: action down")
                return false
            }
            MotionEvent.ACTION_UP ->{
                Log.i(TAG, "onTouch: action up")
                Toast.makeText(requireContext(), "started up", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return false
        }
    }
}