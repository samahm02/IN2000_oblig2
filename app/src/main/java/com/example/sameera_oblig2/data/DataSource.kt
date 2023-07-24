package com.example.sameera_oblig2.data


import com.example.sameera_oblig2.model.PartiList
import com.example.sameera_oblig2.model.Stemmer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString

private const val BASE_URL =
    "https://in2000-proxy.ifi.uio.no/"

private const val BASE_URLXML =
    "https://in2000-proxy.ifi.uio.no/alpacaapi/district3"


@OptIn(ExperimentalSerializationApi::class)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("alpacaapi/alpacaparties".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AlpacaPartyApiService {
    @GET("alpacaapi/alpacaparties")
    suspend fun getInfo(): PartiList

    @GET("alpacaapi/district1")
    suspend fun getDist1(): List<Stemmer>

    @GET("alpacaapi/district2")
    suspend fun getDist2(): List<Stemmer>

}


object AlpacaPartyApi {
    val retrofitService: AlpacaPartyApiService by lazy {
        retrofit.create(AlpacaPartyApiService::class.java)
    }
}

suspend fun hentXml(): IntArray {
    val xml = Fuel.get(BASE_URLXML).awaitString()
    val inputStream = xml.byteInputStream()
    val parties = AlpacaXmlParser().parse(inputStream)

    val sortedArray = IntArray(5)
    var total = 0
    for (parti in parties) {
        val id = parti.id?.toIntOrNull()
        val notNullableIntVotes: Int = parti.votes!!
        val notNullableIntId: Int = id!!
        sortedArray[notNullableIntId-1] = notNullableIntVotes
        total += notNullableIntVotes
    }
    sortedArray[4] = total

    return sortedArray

}
