package com.example.togetherpet.fragment

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.togetherpet.OnClickWalkingRecordListener
import com.example.togetherpet.R
import com.example.togetherpet.WalkingRecordAdapter
import com.example.togetherpet.databinding.FragmentWalkingPetRecordBinding
import com.example.togetherpet.testData.entity.WalkingRecord

class WalkingPetRecordFragment : Fragment(), OnClickWalkingRecordListener {

    private var _binding : FragmentWalkingPetRecordBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : WalkingPetViewModel by activityViewModels()

    lateinit var walkingRecyclerViewAdapter: WalkingRecordAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalkingPetRecordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initRecyclerView()
    }

    fun initListener(){
        binding.walkingPageButton.setOnClickListener{
            navigateToWalkingPage()
        }
    }

    fun initRecyclerView(){
        walkingRecyclerViewAdapter = WalkingRecordAdapter(this)
        binding.walkingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.walkingRecyclerview.adapter = walkingRecyclerViewAdapter

        val array = arrayListOf<WalkingRecord>(WalkingRecord(10,System.currentTimeMillis(),System.currentTimeMillis(),SystemClock.elapsedRealtime(), 10), WalkingRecord(10,System.currentTimeMillis(),System.currentTimeMillis(),SystemClock.elapsedRealtime(), 10), WalkingRecord(10,System.currentTimeMillis(),System.currentTimeMillis(),SystemClock.elapsedRealtime(), 10))
        walkingRecyclerViewAdapter.submitList(array)
    }

    fun navigateToWalkingPage(){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_frameLayout, WalkingPetFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun navigateToDetailRecordPage(){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_frameLayout, WalkingPetFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}