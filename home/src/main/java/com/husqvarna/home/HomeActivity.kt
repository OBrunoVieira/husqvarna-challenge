package com.husqvarna.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husqvarna.home.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var activityHomeBinding: ActivityHomeBinding? = null
    private val binding by lazy { activityHomeBinding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityHomeBinding
                .inflate(layoutInflater)
                .also { activityHomeBinding = it }
                .root)
    }
}
