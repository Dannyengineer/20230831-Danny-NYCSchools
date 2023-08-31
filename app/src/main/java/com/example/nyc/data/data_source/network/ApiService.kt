package com.example.nyc.data.data_source.network

import com.example.nyc.domain.models.SchoolMarkModel
import com.example.nyc.domain.models.SchoolModel
import retrofit2.http.*

interface ApiService {

    @GET(APILinks.FETCH_SCHOOLS_LIST)
    suspend fun fetchSchoolsList(): ArrayList<SchoolModel>

    @GET(APILinks.FETCH_SCHOOLS_MARKS_LIST)
    suspend fun fetchSchoolsMarksList(): ArrayList<SchoolMarkModel>
}
