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

        // FragmentManager 가져오기
        val fragmentManager = supportFragmentManager

        // FragmentTransaction 시작하기
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Fragment 인스턴스 생성하기
        val myFragment = FirstFragment()

        // 프래그먼트 추가하기
        fragmentTransaction.add(R.id.fragment_container, myFragment)

        // FragmentTransaction 완료하기
        fragmentTransaction.commit()

    }
}
