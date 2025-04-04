package com.zachnr.bookplayfree.data.mapper

import com.zachnr.bookplayfree.data.model.DeepSeekChatChoiceResponse
import com.zachnr.bookplayfree.data.model.DeepSeekChatResponse
import com.zachnr.bookplayfree.data.model.DeepSeekChatUsageResponse
import com.zachnr.bookplayfree.domain.model.DeepSeekChatChoiceDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatMessageDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatPromptTokensDetailsDomain
import com.zachnr.bookplayfree.domain.model.DeepSeekChatUsageDomain
import com.zachnr.bookplayfree.utils.ext.orZero
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.withContext

internal class DeepSeekDataMapper(
    private val dispatcher: DispatcherProvider
) {
    suspend fun mapChatResponseToDomain(
        response: DeepSeekChatResponse?
    ): DeepSeekChatDomain = withContext(dispatcher.io) {
        response?.run {
            DeepSeekChatDomain(
                choices = choices?.map { it.toChoiceDomain() }.orEmpty(),
                created = created.orZero(),
                id = id.orEmpty(),
                model = model.orEmpty(),
                `object` = objectX.orEmpty(),
                systemFingerprint = systemFingerprint.orEmpty(),
                usage = usage.toUsageDomain()
            )
        } ?: DeepSeekChatDomain()
    }

    private fun DeepSeekChatChoiceResponse?.toChoiceDomain(): DeepSeekChatChoiceDomain {
        return DeepSeekChatChoiceDomain(
            finishReason = this?.finishReason.orEmpty(),
            index = this?.index.orZero(),
            message = DeepSeekChatMessageDomain(
                content = this?.message?.content.orEmpty(),
                role = this?.message?.role.orEmpty()
            )
        )
    }

    private fun DeepSeekChatUsageResponse?.toUsageDomain(): DeepSeekChatUsageDomain {
        return DeepSeekChatUsageDomain(
            completionTokens = this?.completionTokens.orZero(),
            promptCacheHitTokens = this?.promptCacheHitTokens.orZero(),
            promptCacheMissTokens = this?.promptCacheMissTokens.orZero(),
            promptTokens = this?.promptTokens.orZero(),
            promptTokensDetails = DeepSeekChatPromptTokensDetailsDomain(
                cachedTokens = this?.promptTokensDetails?.cachedTokens.orZero()
            ),
            totalTokens = this?.totalTokens.orZero()

        )
    }
}
