package com.example.nyc.presentation.activities

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nyc.databinding.ActivitySchoolDetailBinding
import com.example.nyc.presentation.customViews.ProgressDialogue
import com.example.nyc.presentation.view_models.SchoolDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull


@AndroidEntryPoint
class SchoolDetailActivity : AppCompatActivity() {
    private val schoolDetailViewModel: SchoolDetailViewModel by viewModels()
    private lateinit var binding: ActivitySchoolDetailBinding

    private val progressDialogue: ProgressDialogue by lazy { ProgressDialogue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        observeViewState()
        observeViewAction()
        progressDialogue.showLoader(true)
        schoolDetailViewModel.fetchSchoolsList(intent.extras?.getString("schoolId"))
    }

    private fun observeViewState() {
        lifecycleScope.launchWhenStarted {
            schoolDetailViewModel.viewStateFlow.collect { viewState ->
                if (viewState.schoolMarkModel != null) {
                    binding.schoolNotFound.visibility = View.GONE
                    binding.schoolGroup.visibility = View.VISIBLE

                    binding.schoolName.text =
                        "School Name: " + viewState.schoolMarkModel?.schoolName
                    binding.mathScore.text =
                        "Math Score: " + viewState.schoolMarkModel?.satMathAverageScore
                    binding.writingScore.text =
                        "Writing Score: " + viewState.schoolMarkModel?.sateWritingAverageScore
                    binding.readingScore.text =
                        "Reading Score: " + viewState.schoolMarkModel?.satCriticalReadingAvgerageScore
                }
            }
        }
    }

    private fun observeViewAction() {
        lifecycleScope.launchWhenStarted {
            schoolDetailViewModel.viewActionFlow.filterNotNull().collect { viewAction ->
                when (viewAction) {
                    is SchoolDetailViewModel.ViewAction.ShowLoader -> {
                        progressDialogue.showLoader(viewAction.isLoaderShowing)
                    }

                    SchoolDetailViewModel.ViewAction.SchoolNotFound -> {
                        binding.schoolNotFound.visibility = View.VISIBLE
                        binding.schoolGroup.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}