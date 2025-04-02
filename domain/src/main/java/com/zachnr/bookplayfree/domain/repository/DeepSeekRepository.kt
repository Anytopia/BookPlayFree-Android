package com.zachnr.bookplayfree.domain.repository

import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekReqBody
import com.zachnr.bookplayfree.domain.model.DomainWrapper

interface DeepSeekRepository {
    suspend fun generateDeepSeekPrompt(body: DeepSeekReqBody): DomainWrapper<DeepSeekChatDomain>
}
