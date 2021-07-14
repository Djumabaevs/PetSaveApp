package com.bignerdranch.android.petsaveapp.core.domain.model.user


@Root(name = "user", strict = false)
data class User constructor(
    @field:Element(name = "name")
    @param:Element(name = "name")
    var name: String,

    @field:Element(name = "password")
    @param:Element(name = "password")
    var password: String,

    @field:Element(name = "id")
    @param:Element(name = "id")
    var id: String,

    @field:Element(name = "extra")
    @param:Element(name = "extra")
    var extra: String) : Serializable