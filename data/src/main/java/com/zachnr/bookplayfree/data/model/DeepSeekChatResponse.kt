package com.zachnr.bookplayfree.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeepSeekChatResponse(
    @SerialName("choices") val choices: List<DeepSeekChatChoiceResponse?>? = null,
    @SerialName("created") val created: Int? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("model") val model: String? = null,
    @SerialName("object") val objectX: String? = null,
    @SerialName("system_fingerprint") val systemFingerprint: String? = null,
    @SerialName("usage") val usage: DeepSeekChatUsageResponse? = null
)

@Serializable
data class DeepSeekChatChoiceResponse(
    @SerialName("finish_reason") val finishReason: String? = null,
    @SerialName("index") val index: Int? = null,
    @SerialName("message") val message: DeepSeekChatMessageResponse? = null
)

@Serializable
data class DeepSeekChatMessageResponse(
    @SerialName("content") val content: String? = null,
    @SerialName("role") val role: String? = null
)

@Serializable
data class DeepSeekChatUsageResponse(
    @SerialName("completion_tokens") val completionTokens: Int? = null,
    @SerialName("prompt_cache_hit_tokens") val promptCacheHitTokens: Int? = null,
    @SerialName("prompt_cache_miss_tokens") val promptCacheMissTokens: Int? = null,
    @SerialName("prompt_tokens") val promptTokens: Int? = null,
    @SerialName("prompt_tokens_details")
    val promptTokensDetails: DeepSeekPromptTokensDetailsResponse? = null,
    @SerialName("total_tokens") val totalTokens: Int? = null
)

@Serializable
data class DeepSeekPromptTokensDetailsResponse(
    @SerialName("cached_tokens") val cachedTokens: Int? = null
)
