package com.example.nyc.data.data_source

import com.example.nyc.data.data_source.network.ApiService
import com.example.nyc.domain.models.SchoolMarkModel
import com.example.nyc.domain.models.SchoolModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SchoolsAPICalling @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchSchoolsList(): ArrayList<SchoolModel> {
        return apiService.fetchSchoolsList()
    }

    suspend fun fetchSchoolsMarkList(): ArrayList<SchoolMarkModel> {
        return apiService.fetchSchoolsMarksList()
    }
}