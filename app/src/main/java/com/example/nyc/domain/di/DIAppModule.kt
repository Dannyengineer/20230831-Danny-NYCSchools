package com.example.nyc.domain.di

import android.content.Context
import com.example.nyc.data.data_source.SchoolsAPICalling
import com.example.nyc.data.data_source.network.APILinks
import com.example.nyc.data.data_source.network.ApiService
import com.example.nyc.data.repositories_impl.SchoolsRepositoryImpl
import com.example.nyc.domain.repositories.SchoolsRepository
import com.example.nyc.domain.use_cases.FetchSchoolsUseCase
import com.example.nyc.presentation.customViews.ProgressDialogue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIAppModule {

    /**
     * Core Data Source
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        }

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APILinks.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolsAPICalling(apiService: ApiService): SchoolsAPICalling {
        return SchoolsAPICalling(apiService)
    }

    @Provides
    @Singleton
    fun provideSchoolsRepository(schoolsAPICalling: SchoolsAPICalling): SchoolsRepository {
        return SchoolsRepositoryImpl(schoolsAPICalling)
    }

    @Provides
    @Singleton
    fun provideFetchSchoolsUseCase(schoolsRepository: SchoolsRepository): FetchSchoolsUseCase {
        return FetchSchoolsUseCase(schoolsRepository)
    }

    @Provides
    @Singleton
    fun provideProgressDialogue(@ApplicationContext context: Context): ProgressDialogue {
        return ProgressDialogue(context)
    }
}