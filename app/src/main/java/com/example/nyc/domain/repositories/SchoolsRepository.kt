package com.example.nyc.domain.repositories

import com.example.nyc.domain.models.SchoolMarkModel
import com.example.nyc.domain.models.SchoolModel

interface SchoolsRepository {
    suspend fun fetchSchoolList(): ArrayList<SchoolModel>
    suspend fun fetchSchoolMarkList(): ArrayList<SchoolMarkModel>
}