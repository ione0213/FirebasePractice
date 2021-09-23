package com.yuchen.firebasephaseone.sign.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuchen.firebasephaseone.R
import com.yuchen.firebasephaseone.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

}