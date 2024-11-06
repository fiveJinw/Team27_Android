package com.example.togetherpet.Registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.togetherpet.R
import com.example.togetherpet.databinding.FragmentInfoRegistrationResidenceBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationResidenceFragment : Fragment() {
    private var _binding : FragmentInfoRegistrationResidenceBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoRegistrationResidenceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            nextButton.setOnClickListener { goToNextScreen() }
        }
    }

    private fun checkResidenceAndFeature() : InputState{
        return if(binding.residenceInputField.text.isEmpty()){
            binding.residenceInputField.requestFocus()
            InputState.NOT_EXIST_RESIDENCE
        }
        else if(binding.featureInputField.text.isEmpty()) {
            binding.featureInputField.requestFocus()
            InputState.NOT_EXIST_FEATURE
        }
        else return InputState.EXIST_RESIDENCE_AND_FEATURE
    }

    private fun existResidenceAndFeature(): Boolean{
        return when(checkResidenceAndFeature()){
            InputState.EXIST_RESIDENCE_AND_FEATURE -> true
            else -> false
        }
    }

    private fun goToNextScreen(){
        if(existResidenceAndFeature()) {
            sharedViewModel.setPetFeature(binding.featureInputField.text.toString())
            findNavController().navigate(R.id.action_registrationResidenceFragment_to_registrationImageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    enum class InputState {
        EXIST_RESIDENCE_AND_FEATURE,
        NOT_EXIST_RESIDENCE,
        NOT_EXIST_FEATURE
    }
}