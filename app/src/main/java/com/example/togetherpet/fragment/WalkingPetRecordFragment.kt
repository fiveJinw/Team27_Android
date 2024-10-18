package com.example.togetherpet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.togetherpet.databinding.FragmentWalkingPetRecordBinding

class WalkingPetRecordFragment : Fragment() {

    private var _binding : FragmentWalkingPetRecordBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : WalkingPetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalkingPetRecordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun initListener(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}