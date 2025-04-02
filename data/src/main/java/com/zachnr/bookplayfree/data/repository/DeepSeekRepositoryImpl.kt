package com.zachnr.bookplayfree.data.repository

import com.zachnr.bookplayfree.data.mapper.DeepSeekDataMapper
import com.zachnr.bookplayfree.data.model.DeepSeekChatResponse
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekReqBody
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.DeepSeekRepository
import com.zachnr.bookplayfree.network.ext.safePostWrapped
import com.zachnr.bookplayfree.network.model.handleApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class DeepSeekRepositoryImpl(
    private val deepSeekClient: HttpClient,
    private val mapper: DeepSeekDataMapper
): DeepSeekRepository {
    override suspend fun generateDeepSeekPrompt(
        body: DeepSeekReqBody
    ): DomainWrapper<DeepSeekChatDomain> {
        val response = deepSeekClient.safePostWrapped<DeepSeekChatResponse>("v1/chat/completions") {
            setBody(body)
        }
        return handleApiResponse(response) {
            mapper.mapChatResponseToDomain(it)
        }
    }
}
