package com.zachnr.bookplayfree.data.utils

import com.zachnr.bookplayfree.ailocal.model.AiLocalWrapper
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.network.model.ResponseWrapper

internal suspend fun<I, O> handleApiResponse(
    response: ResponseWrapper<I>,
    dataMapper: suspend (I) -> O
 ): DomainWrapper<O> {
    return when (response) {
        is ResponseWrapper.Success -> DomainWrapper.Success(data = dataMapper(response.data))
        is ResponseWrapper.Error -> responseErrorMapper(response)
        is ResponseWrapper.Exception -> throwableMapper(response)
    }
}

private fun<I, O> responseErrorMapper(
    error: ResponseWrapper.Error<I>
): DomainWrapper.Error<O> {
    return DomainWrapper.Error(
        code = error.code,
        message = error.message
    )
}

private fun<I, O> throwableMapper(
    throwable: ResponseWrapper.Exception<I>
): DomainWrapper.Error<O> {
    return DomainWrapper.Error(
        code = null,
        message = throwable.throwable.message.toString()
    )
}

internal suspend fun<I, O> handleAiLocalResult(
    response: AiLocalWrapper<I>,
    dataMapper: suspend (I) -> O
): DomainWrapper<O> {
    return when (response) {
        is AiLocalWrapper.Success -> DomainWrapper.Success(data = dataMapper(response.data))
        is AiLocalWrapper.Exception -> throwableAiLocalMapper(response)
    }
}

private fun<I, O> throwableAiLocalMapper(
    throwable: AiLocalWrapper.Exception<I>
): DomainWrapper.Error<O> {
    return DomainWrapper.Error(
        code = null,
        message = throwable.throwable.message.toString()
    )
}
