package com.zachnr.bookplayfree.data.mapper

import com.zachnr.bookplayfree.domain.model.TranslationDomain

internal class TranslationMapper {
    suspend fun mapTextToDomain(result: String): TranslationDomain {
        return TranslationDomain(text = result)
    }
}
