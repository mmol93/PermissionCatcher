package com.easyo.permissioncatcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.easyo.permissioncatcher.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, FirstFragment())
            commit()
        }
    }
}
