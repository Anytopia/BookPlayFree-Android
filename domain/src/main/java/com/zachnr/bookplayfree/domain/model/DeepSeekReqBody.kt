package com.zachnr.bookplayfree.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeepSeekReqBody(
    @SerialName("max_tokens") val maxTokens: Int = 100,
    @SerialName("messages") val messages: List<DeepSeekMessageReqBody> = listOf(),
    @SerialName("model") val model: String = "deepseek-chat",
    @SerialName("stream") val stream: Boolean = false
)

@Serializable
data class DeepSeekMessageReqBody(
    @SerialName("content") val content: String = "",
    @SerialName("role") val role: String = "assistant"
)
