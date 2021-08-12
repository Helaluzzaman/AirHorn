package com.hbsoft.airhorn.fragments

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.hbsoft.airhorn.MainActivity
import com.hbsoft.airhorn.R
import com.hbsoft.airhorn.fragments.viewModel.HomeViewModel

const val TAG = "button"
const val VIBRATIONTIME = 30  //seconds

class HomeFragment : Fragment(), View.OnTouchListener {


    var shouldVibrate = true
    lateinit var button: ImageButton
    lateinit var vibrator: Vibrator

    val mHomeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        button = view.findViewById(R.id.b_press)
        button.setOnTouchListener(this)
        return view
    }
    fun startVibrate(){
        if(shouldVibrate && vibrator.hasVibrator()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator.vibrate(VibrationEffect.createOneShot(1000 * VIBRATIONTIME.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else{
                vibrator.vibrate(1000* VIBRATIONTIME.toLong())
            }
        }
    }
    fun stopVibrate(){
        vibrator.cancel()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                Toast.makeText(requireContext(), "down", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "onTouch: action down")
                startVibrate()
                mHomeViewModel.startAudio()
                return false
            }
            MotionEvent.ACTION_UP ->{
                Log.i(TAG, "onTouch: action up")
                Toast.makeText(requireContext(), "up", Toast.LENGTH_SHORT).show()
                stopVibrate()
                mHomeViewModel.stopAudio()
                return false
            }
            else -> return false
        }
    }
}