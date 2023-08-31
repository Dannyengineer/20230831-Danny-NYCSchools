package com.example.nyc.data.repositories_impl

import com.example.nyc.data.data_source.SchoolsAPICalling
import com.example.nyc.domain.models.SchoolMarkModel
import com.example.nyc.domain.models.SchoolModel
import com.example.nyc.domain.repositories.SchoolsRepository
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val schoolsAPICalling: SchoolsAPICalling
) : SchoolsRepository {
    override suspend fun fetchSchoolList(): ArrayList<SchoolModel> {
        return schoolsAPICalling.fetchSchoolsList()
    }

    override suspend fun fetchSchoolMarkList(): ArrayList<SchoolMarkModel> {
        return schoolsAPICalling.fetchSchoolsMarkList()
    }
}