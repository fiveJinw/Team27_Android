package com.example.togetherpet

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.togetherpet.databinding.WalkingRecordItemBinding
import com.example.togetherpet.testData.entity.WalkingRecord
import com.kakao.vectormap.GestureType
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
import java.util.Locale

class WalkingRecordAdapter(
    val listener: OnClickWalkingRecordListener
) : ListAdapter<WalkingRecord, WalkingRecordViewHolder>(WalkingRecordDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkingRecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WalkingRecordItemBinding.inflate(inflater, parent, false)
        return WalkingRecordViewHolder(binding, listener)

    }

    override fun onBindViewHolder(holder: WalkingRecordViewHolder, position: Int) {
        val walkingRecord = getItem(position)
        holder.bind(walkingRecord)
    }

}

class WalkingRecordDiffCallBack : DiffUtil.ItemCallback<WalkingRecord>() {
    override fun areItemsTheSame(oldItem: WalkingRecord, newItem: WalkingRecord): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: WalkingRecord, newItem: WalkingRecord): Boolean {
        return oldItem.date == newItem.date && oldItem.time == newItem.time
    }
}

class WalkingRecordViewHolder(
    private val binding: WalkingRecordItemBinding,
    listener: OnClickWalkingRecordListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            listener.navigateToDetailRecordPage(bindingAdapterPosition)
        }
    }

    fun bind(walkingRecord: WalkingRecord) {
        binding.distanceValueItem.text = "총 ${walkingRecord.distance}m 산책했어요!"
        val time = formattingLongToTime(walkingRecord.time)
        binding.timeValueItem.text = time
        binding.timeResultRedText.text =
            "${formattingLongToTime(walkingRecord.baseTime)} ~ ${formattingLongToTime(walkingRecord.baseTime + walkingRecord.time)}"
        initMap(walkingRecord)
    }

    fun initMap(walkingRecord: WalkingRecord) {
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
                kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(walkingRecord.route.first()))
                val layer = kakaoMap.routeLineManager?.layer
                val lineStyle = RouteLineStyle.from(16f, Color.RED)
                lineStyle.strokeColor = Color.BLACK
                val stylesSet = RouteLineStylesSet.from(
                    RouteLineStyles.from(lineStyle)
                )
                val segment = RouteLineSegment.from(
                    walkingRecord.route
                ).setStyles(stylesSet.getStyles(0))

                val options = RouteLineOptions.from(segment)
                    .setStylesSet(stylesSet)

                val routeLine = layer?.addRouteLine(options)
                kakaoMap.setGestureEnable(GestureType.Zoom, false)
                kakaoMap.setGestureEnable(GestureType.TwoFingerSingleTap, false)
                kakaoMap.setGestureEnable(GestureType.OneFingerDoubleTap, false)
                kakaoMap.setGestureEnable(GestureType.OneFingerZoom, false)
                kakaoMap.setGestureEnable(GestureType.Pan, false)
                kakaoMap.setGestureEnable(GestureType.Tilt, false)
                kakaoMap.setGestureEnable(GestureType.Rotate, false)

            }

        })

    }

    fun formattingLongToTime(time: Long): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.KOREAN)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(time)
    }
}

interface OnClickWalkingRecordListener {
    fun navigateToDetailRecordPage(position: Int)
}