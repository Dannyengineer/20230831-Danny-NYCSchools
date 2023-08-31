package com.example.nyc.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyc.databinding.ItemSchoolBinding
import com.example.nyc.domain.models.SchoolModel
import com.example.nyc.presentation.view_models.SchoolViewModel

class SchoolAdapter(private val schoolViewModel: SchoolViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSchoolBinding.inflate(inflater, parent, false)
        return AppsViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as AppsViewHolder).bind(
            schoolViewModel.viewStateFlow.value.schoolsList[position]
        )
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return schoolViewModel.viewStateFlow.value.schoolsList.size
    }

    inner class AppsViewHolder(private val binding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schoolModel: SchoolModel?) {
            binding.schoolName.text = schoolModel?.schoolName
            binding.neighborhood.text = schoolModel?.neighborhood
            binding.root.setOnClickListener {
                schoolViewModel.onSchoolItemClicked(adapterPosition)
            }
        }
    }
}