package com.example.sameera_oblig2.model

import kotlinx.serialization.Serializable

@Serializable
data class PartiList(val parties: List<Partier>)

@Serializable
data class Partier(
    val id: String,
    val name: String,
    val leader: String,
    val img: String,
    val color: String
)



@Serializable
data class Stemmer(
    val id: String
)

@Serializable
data class PartyDist3(val id : String?, val votes : Int?)