package com.jhs.presentation.model

enum class SortType(val displayName: String, val value: String) {
    ACCURACY("정확도순", "accuracy"),
    RECENCY("발간일순", "latest"),
    TITLE_ASC("제목 오름차순", "title_asc"),
    TITLE_DESC("제목 내림차순", "title_desc");
}
