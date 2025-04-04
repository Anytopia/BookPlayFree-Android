package com.zachnr.bookplayfree.data.repository

import com.zachnr.bookplayfree.ailocal.OfflineTranslator
import com.zachnr.bookplayfree.data.mapper.TranslationMapper
import com.zachnr.bookplayfree.data.utils.handleAiLocalResult
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.TranslationDomain
import com.zachnr.bookplayfree.domain.repository.TranslationRepository

internal class TranslationRepositoryImpl(
    private val localTranslator: OfflineTranslator,
    private val mapper: TranslationMapper
): TranslationRepository {
    override suspend fun translate(input: String): DomainWrapper<TranslationDomain> {
        val result = localTranslator.translate(input)
        return handleAiLocalResult(result) {
            mapper.mapTextToDomain(it)
        }
    }
}
