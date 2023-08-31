package com.example.nyc.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.nyc.databinding.ActivitySchoolBinding
import com.example.nyc.presentation.adapters.SchoolAdapter
import com.example.nyc.presentation.customViews.ProgressDialogue
import com.example.nyc.presentation.view_models.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull


@AndroidEntryPoint
class SchoolActivity : AppCompatActivity() {

    private val schoolViewModel: SchoolViewModel by viewModels()
    private lateinit var binding: ActivitySchoolBinding
    private lateinit var schoolAdapter: SchoolAdapter

    private val progressDialogue: ProgressDialogue by lazy { ProgressDialogue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        observeViewState()
        observeViewAction()
        setupSchoolAdapter()
        progressDialogue.showLoader(true)
        schoolViewModel.fetchSchoolsList()
    }

    private fun setupSchoolAdapter() {
        schoolAdapter = SchoolAdapter(schoolViewModel)
        binding.schoolRV.adapter = schoolAdapter
        binding.schoolRV.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun observeViewState() {
        lifecycleScope.launchWhenStarted {
            schoolViewModel.viewStateFlow.collect { viewState ->
                notifySchoolAdapter()
            }
        }
    }

    private fun observeViewAction() {
        lifecycleScope.launchWhenStarted {
            schoolViewModel.viewActionFlow.filterNotNull().collect { viewAction ->
                when (viewAction) {
                    is SchoolViewModel.ViewAction.ShowLoader -> {
                        progressDialogue.showLoader(viewAction.isLoaderShowing)
                    }

                    is SchoolViewModel.ViewAction.MoveToDetailActivity -> {
                        startActivity(
                            Intent(
                                this@SchoolActivity,
                                SchoolDetailActivity::class.java
                            ).putExtra(
                                "schoolId", viewAction.schoolId
                            )
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifySchoolAdapter() {
        binding.schoolRV.adapter?.notifyDataSetChanged()
    }
}