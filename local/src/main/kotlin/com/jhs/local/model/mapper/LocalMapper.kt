package com.jhs.local.model.mapper

interface LocalMapper<DataModel> {
    fun toData(): DataModel
}

fun <LocalModel : LocalMapper<DataModel>, DataModel> List<LocalModel>.toData(): List<DataModel> {
    return map { it.toData() }
}