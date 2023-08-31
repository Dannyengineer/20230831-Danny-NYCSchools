package com.example.nyc.domain.models

import com.google.gson.annotations.SerializedName

data class SchoolMarkModel(
    @SerializedName("dbn")
    val schoolId: String,

    @SerializedName("school_name")
    val schoolName: String,

    @SerializedName("num_of_sat_test_takers")
    val satStudentCount: String,

    @SerializedName("sat_critical_reading_avg_score")
    val satCriticalReadingAvgerageScore: String,

    @SerializedName("sat_math_avg_score")
    val satMathAverageScore: String,

    @SerializedName("sat_writing_avg_score")
    val sateWritingAverageScore: String
)