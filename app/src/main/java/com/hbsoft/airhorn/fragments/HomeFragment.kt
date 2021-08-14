package com.hbsoft.airhorn.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.hbsoft.airhorn.MainActivity
import com.hbsoft.airhorn.R
import com.hbsoft.airhorn.fragments.viewModel.HomeViewModel

const val TAG = "button"
const val VIBRATIONTIME = 30  //seconds

class HomeFragment : Fragment(), View.OnTouchListener {
    lateinit var button: ImageButton
    lateinit var vibrator: Vibrator

    val mHomeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        shouldVibrate = sharedPreferences.getBoolean("vibrate", true)
        Log.i("vibrate", "onCreate: $shouldVibrate")
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
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
                button.setImageDrawable(getDrawable(requireContext(), R.drawable.pressed))
                return false
            }
            MotionEvent.ACTION_UP ->{
                Log.i(TAG, "onTouch: action up")
                Toast.makeText(requireContext(), "up", Toast.LENGTH_SHORT).show()
                stopVibrate()
                mHomeViewModel.stopAudio()
                button.setImageDrawable(getDrawable(requireContext(), R.drawable.unpressed))
                return false
            }
            else -> return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settingsFragment_menu ->  findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            R.id.aboutFragment_menu -> Toast.makeText(requireContext(), "wait bro", Toast.LENGTH_SHORT)
                .show()
                
        }

       
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    companion object{
        var shouldVibrate = true
    }
}