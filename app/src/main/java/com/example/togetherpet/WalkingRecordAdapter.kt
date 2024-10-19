package com.example.togetherpet

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.togetherpet.databinding.WalkingRecordItemBinding
import com.example.togetherpet.testData.entity.WalkingRecord
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

class WalkingRecordViewHolder(private val binding: WalkingRecordItemBinding, listener : OnClickWalkingRecordListener) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.root.setOnClickListener{
            listener.navigateToDetailRecordPage()
        }
    }

    fun bind(walkingRecord: WalkingRecord){
        binding.distanceValueItem.text = "총 ${walkingRecord.distance}m 산책했어요!"
        val time = formattingLongToTime(walkingRecord.time)
        binding.timeValueItem.text = time
        binding.timeResultRedText.text = "${formattingLongToTime(walkingRecord.baseTime)} ~ ${formattingLongToTime(walkingRecord.baseTime + walkingRecord.time)}"
    }

    fun formattingLongToTime(time : Long) : String{
        val format = SimpleDateFormat("HH:mm:ss", Locale.KOREAN)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(time)
    }
}

interface OnClickWalkingRecordListener {
    fun navigateToDetailRecordPage()
}