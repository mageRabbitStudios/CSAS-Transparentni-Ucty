package com.kinzlstanislav.csasucty

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kinzlstanislav.csasucty.architecture.network.api.CSASApiService
import com.kinzlstanislav.csasucty.architecture.repository.AccountsRepository
import com.kinzlstanislav.csasucty.architecture.repository.mapper.AccountsMapper
import com.kinzlstanislav.csasucty.base.imageloading.GlideImageLoader
import com.kinzlstanislav.csasucty.list.viewmodel.ListViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class CSASApp : Application() {

    private companion object {
        const val CSAS_BASE_URL = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v3/"
        const val CSAS_API_KEY = "7ca45559-32c7-4818-875e-b6653f7155b5"
        const val CSAS_WEB_API_KEY_HEADER = "web-api-key"
        val APP_MODULE = module {
            // view models
            factory { ListViewModel(get()) }

            // repository
            factory {
                AccountsRepository(get(), get())
            }

            // mappers
            factory { AccountsMapper() }

            // network
            factory {
                Retrofit.Builder()
                    .baseUrl(CSAS_BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(
                        OkHttpClient.Builder().apply {
                            // logging interceptor
                            if (BuildConfig.DEBUG) {
                                addNetworkInterceptor(
                                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                                )
                            }
                            // api key header
                            addInterceptor { chain ->
                                chain.proceed(
                                    chain.request()
                                        .newBuilder()
                                        .addHeader(CSAS_WEB_API_KEY_HEADER, CSAS_API_KEY)
                                        .build()
                                )
                            }
                        }.build()
                    ).build()
                    .create(CSASApiService::class.java)
            }

            // other
            single { GlideImageLoader() }
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(APP_MODULE).androidContext(this@CSASApp)
        }
    }

}