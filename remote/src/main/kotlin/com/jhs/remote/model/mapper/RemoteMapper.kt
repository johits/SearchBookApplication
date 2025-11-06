package com.jhs.remote.model.mapper

interface RemoteMapper<DataModel> {
    fun toData(): DataModel
}