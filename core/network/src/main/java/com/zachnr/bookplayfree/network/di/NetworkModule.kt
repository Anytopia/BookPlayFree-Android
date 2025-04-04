package com.zachnr.bookplayfree.network.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.zachnr.bookplayfree.network.BuildConfig
import com.zachnr.bookplayfree.utils.utils.NetworkConst
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val deepseekClientModule  = module {
    single(named(NetworkConst.DEEPSEEK_CLIENT_QUALIFIER)) {
        val jsonConfig = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        HttpClient(get()) {
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.DEEPSEEK_API_KEY}")
                url("https://api.deepseek.com/")
            }
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    }
}

val mockoonClientModule  = module {
    single(named(NetworkConst.MOCKOON_CLIENT_QUALIFIER)) {
        val jsonConfig = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        HttpClient(get()) {
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                url("http://127.0.0.1:3002/api/")
            }
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    }
}

val okhttpClientEngineModule = module {
    single {
        OkHttp.create {
            addInterceptor(ChuckerInterceptor(androidContext()))
        }
    }
}
