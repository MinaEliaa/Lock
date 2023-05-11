package com.example.lock.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.lock.R
import com.example.lock.ui.pages.decrypt.CaeserDecryptFragment
import com.example.lock.ui.pages.decrypt.PlayFairDecryptFragment
import com.example.lock.ui.pages.decrypt.VingenereDecryptFragment
import com.example.lock.ui.pages.encrypt.CaeserEncrpytFragment
import com.example.lock.ui.pages.encrypt.PlayFairEncryptFragment
import com.example.lock.ui.pages.encrypt.VingenereEncrpytFragment
import com.google.android.material.tabs.TabLayout


class DecryptFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_decrypt, container, false)

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tablayout: TabLayout = view.findViewById(R.id.tabLayout)

        val fragmentAdapter = FragmentAdapter(childFragmentManager)
        fragmentAdapter.addFragment(CaeserDecryptFragment(), "Caeser")
        fragmentAdapter.addFragment(VingenereDecryptFragment(), "Vingenere")
        fragmentAdapter.addFragment(PlayFairDecryptFragment(), "PlayFair")

        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)

        return view
    }


}