package com.zachnr.bookplayfree.domain.repository

import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.TranslationDomain

/**
 * Interface representing the translation process, locally or remotely
 */
interface TranslationRepository {
    /**
     * Retrieves the translation result.
     *
     * @param input: Text or sentence to be translated.
     * @return translation result.
     */
    suspend fun translate(input: String): DomainWrapper<TranslationDomain>
}
