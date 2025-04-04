package com.zachnr.bookplayfree.domain.usecase

import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekMessageReqBody
import com.zachnr.bookplayfree.domain.model.DeepSeekReqBody
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.DeepSeekRepository

class GetQuoteDeepSeekUseCase(
    private val deepSeekRepository: DeepSeekRepository
) {
    suspend operator fun invoke(): DomainWrapper<DeepSeekChatDomain> {
        val body = DeepSeekReqBody(
            messages = listOf(DeepSeekMessageReqBody(content = getPrompt()))
        )
        return deepSeekRepository.generateDeepSeekPrompt(body)
    }

    private fun getPrompt(): String {
        return "Gives me quote related to reading or book. Quote only without any desc from you!" +
               "Using this format: \"(Some quote)\" - (Author)"
    }
}
