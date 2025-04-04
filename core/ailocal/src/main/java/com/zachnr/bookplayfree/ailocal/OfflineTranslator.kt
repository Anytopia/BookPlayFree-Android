package com.zachnr.bookplayfree.ailocal

import com.zachnr.bookplayfree.ailocal.model.AiLocalWrapper

interface OfflineTranslator {
    suspend fun translate(text: String): AiLocalWrapper<String>
}
