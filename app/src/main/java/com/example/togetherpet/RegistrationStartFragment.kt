package com.example.togetherpet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.togetherpet.databinding.FragmentInfoRegistrationImageBinding
import com.example.togetherpet.databinding.FragmentInfoRegistrationStartBinding


class RegistrationStartFragment : Fragment() {
    private lateinit var binding : FragmentInfoRegistrationStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoRegistrationStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            startWhiteButton.setOnClickListener { goToNextScreen() }
            startPinkButton.setOnClickListener { goToFindScreen() }
        }
    }

    private fun goToNextScreen(){
        Toast.makeText(activity, "next", Toast.LENGTH_SHORT).show()
    }

    private fun goToFindScreen(){
        Toast.makeText(activity, "find", Toast.LENGTH_SHORT).show()
    }

}