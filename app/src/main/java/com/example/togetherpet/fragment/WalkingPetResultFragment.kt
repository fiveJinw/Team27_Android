package com.example.togetherpet.fragment

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.togetherpet.databinding.FragmentWalkingPetResultBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale


class WalkingPetResultFragment : Fragment() {

    private var _binding : FragmentWalkingPetResultBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel : WalkingPetViewModel by activityViewModels()

    private lateinit var kakaoMap: KakaoMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalkingPetResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    fun initMap(){
        var loc = LatLng.from(35.180837, 126.904849)
        val map = binding.walkingMapView
        map.start(object : MapLifeCycleCallback() {

            override fun onMapDestroy() {
                Log.d("testt", "MapDestroy")
            }

            override fun onMapError(error: Exception) {
                Log.d("testt", error.message.toString())
                //에러 처리
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                Log.d("testt", "MapReady")
                kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(loc))
                this@WalkingPetResultFragment.kakaoMap = kakaoMap
                initListener()
            }
        })
    }

    fun drawLine(arrayList: ArrayList<LatLng>){
        val layer = kakaoMap.routeLineManager?.layer
        val lineStyle = RouteLineStyle.from(16f, Color.RED)
        lineStyle.strokeColor = Color.BLACK
        val stylesSet = RouteLineStylesSet.from(
            RouteLineStyles.from(lineStyle)
        )
        val segment = RouteLineSegment.from(
            arrayList
        ).setStyles(stylesSet.getStyles(0))

        val options = RouteLineOptions.from(segment)
            .setStylesSet(stylesSet)

        val routeLine = layer?.addRouteLine(options)
    }

    fun initListener(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.arrayLoc.collect{
                    Log.d("testt", "listener")
                    drawLine(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.distance.collect {
                    binding.distanceResultText.text = "총 ${it}m 산책했어요"
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.time.collect {
                    Log.d("testt", "time : ${it}")
                    val format = SimpleDateFormat("HH:mm:ss", Locale.KOREAN)
                    format.timeZone = TimeZone.getTimeZone("UTC")
                    binding.timeResultText.text = format.format(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.calories.collect {
                    binding.caloriesResultText.text = "꾸릉이가 총 ${it}kcal 만큼 소모했어요!"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}