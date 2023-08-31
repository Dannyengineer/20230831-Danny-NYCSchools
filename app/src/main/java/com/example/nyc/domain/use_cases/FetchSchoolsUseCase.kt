package com.example.nyc.domain.use_cases

import com.example.nyc.domain.models.SchoolMarkModel
import com.example.nyc.domain.models.SchoolModel
import com.example.nyc.domain.repositories.SchoolsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchSchoolsUseCase @Inject constructor(
    private val schoolRepository: SchoolsRepository,
) {
    private val TAG = FetchSchoolsUseCase::class.java.simpleName

    suspend fun fetchSchoolsList(): List<SchoolModel> = withContext(Dispatchers.IO) {
        schoolRepository.fetchSchoolList()
    }

    suspend fun fetchSchoolsMarkList(schoolId: String): SchoolMarkModel? =
        withContext(Dispatchers.IO) {
            val schoolMarksList = schoolRepository.fetchSchoolMarkList()

            schoolMarksList.firstOrNull() {
                it.schoolId == schoolId
            }
        }
}
