package com.example.nyc.domain.models

import com.google.gson.annotations.SerializedName

data class SchoolModel(
    @SerializedName("dbn")
    val schoolId: String,

    @SerializedName("school_name")
    val schoolName: String,

    @SerializedName("neighborhood")
    val neighborhood: String
)