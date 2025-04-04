package com.zachnr.bookplayfree.ailocal.mlkit

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.zachnr.bookplayfree.ailocal.OfflineTranslator
import com.zachnr.bookplayfree.ailocal.model.AiLocalWrapper
import com.zachnr.bookplayfree.shared.utils.TranslationConst
import kotlinx.coroutines.suspendCancellableCoroutine

internal class MLKitTranslator: OfflineTranslator {
    override suspend fun translate(text: String): AiLocalWrapper<String> {
        var result: AiLocalWrapper<String> = AiLocalWrapper.Exception(
            Throwable(message = TranslationConst.ERROR_TRANSLATION_MESSAGE)
        )
        // Identify the language automatically
        val identifiedLang = identifyLanguage(text)

        if (identifiedLang != TranslationConst.UNDEFINED_ERROR_CODE) {
            result = processTextTranslation(text, identifiedLang)
        }
        return result
    }

    private suspend fun identifyLanguage(text: String): String {
        return suspendCancellableCoroutine { continuation ->
            val languageIdentifier = LanguageIdentification.getClient()
            languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener { langCode ->
                    if (langCode.isNullOrEmpty() || langCode == TranslationConst.UNDEFINED_ERROR_CODE) {
                        continuation.resume(TranslationConst.UNDEFINED_ERROR_CODE) { _, _, _ -> }
                    } else {
                        continuation.resume(langCode) { _, _, _ -> }
                    }
                }
                .addOnFailureListener {
                    continuation.resume(TranslationConst.UNDEFINED_ERROR_CODE) { _, _, _ -> }
                }
        }
    }

    private suspend fun processTextTranslation(
        text: String,
        sourceLang: String
    ): AiLocalWrapper<String> {
        var result: AiLocalWrapper<String> = AiLocalWrapper.Exception(
            Throwable(message = TranslationConst.ERROR_TRANSLATION_MESSAGE)
        )

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            // TODO: Add preference target data. Not hardcoded to indonesia
            .setTargetLanguage(TranslateLanguage.INDONESIAN)
            .build()
        val translator = Translation.getClient(options)

        val isModelReady = isModelReady(translator)

        if (isModelReady) {
            result = getTranslationResult(translator, text)
        }
        return result
    }

    private suspend fun isModelReady(translator: Translator): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val conditions = DownloadConditions.Builder().build()
            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    continuation.resume(true) { _, _, _ -> }
                }
                .addOnFailureListener { _ ->
                    continuation.resume(false) { _, _, _ -> }
                }
        }
    }

    private suspend fun getTranslationResult(
        translator: Translator,
        text: String
    ): AiLocalWrapper<String> {
        return suspendCancellableCoroutine { continuation ->
            translator.translate(text)
                .addOnSuccessListener { translatedText ->
                    continuation.resume(AiLocalWrapper.Success(translatedText)) { _, _, _ -> }                }
                .addOnFailureListener { exception ->
                    continuation.resume(AiLocalWrapper.Exception(exception)) { _, _, _ -> }
                }
        }
    }
}
