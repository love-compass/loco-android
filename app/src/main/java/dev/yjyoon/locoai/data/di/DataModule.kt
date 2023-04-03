package dev.yjyoon.locoai.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.yjyoon.locoai.BuildConfig
import dev.yjyoon.locoai.data.source.ApiService
import dev.yjyoon.locoai.data.source.DateCourseDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "http://locoai.site/"

    @Provides
    @Singleton
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DateCourseDatabase = Room
        .databaseBuilder(context, DateCourseDatabase::class.java, "date_course")
        .build()

    @Singleton
    @Provides
    fun provideBookDao(database: DateCourseDatabase): DateCourseDao = database.dateCourseDao()
}
