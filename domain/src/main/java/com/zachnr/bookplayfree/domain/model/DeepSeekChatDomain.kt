package com.zachnr.bookplayfree.domain.model

data class DeepSeekChatDomain(
    val choices: List<DeepSeekChatChoiceDomain> = emptyList(),
    val created: Int = 0,
    val id: String = "",
    val model: String = "",
    val `object`: String = "",
    val systemFingerprint: String = "",
    val usage: DeepSeekChatUsageDomain = DeepSeekChatUsageDomain()
)

data class DeepSeekChatChoiceDomain(
    val finishReason: String = "",
    val index: Int = 0,
    val message: DeepSeekChatMessageDomain = DeepSeekChatMessageDomain()
)

data class DeepSeekChatMessageDomain(
    val content: String = "",
    val role: String = ""
)

data class DeepSeekChatUsageDomain(
    val completionTokens: Int = 0,
    val promptCacheHitTokens: Int = 0,
    val promptCacheMissTokens: Int = 0,
    val promptTokens: Int = 0,
    val promptTokensDetails: DeepSeekChatPromptTokensDetailsDomain = DeepSeekChatPromptTokensDetailsDomain(),
    val totalTokens: Int =0
)

data class DeepSeekChatPromptTokensDetailsDomain(
    val cachedTokens: Int = 0
)
