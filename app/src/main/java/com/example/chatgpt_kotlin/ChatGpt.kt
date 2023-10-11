package com.example.chatgpt_kotlin
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Scanner

fun generarTextoConChatGPT(prompt: String): String {

    val OPENAI_API_KEY = "Your_APIKEY_HERE"
    val apiKey = OPENAI_API_KEY
 

    if (apiKey.isNullOrEmpty()) {
        return "No se ha configurado la clave de API de OpenAI."
    }

   val apiUrl = "https://api.openai.com/v1/chat/completions"


    val client = OkHttpClient()

    val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    val requestBody = """
        {
            "model": "gpt-3.5-turbo",
            "messages": [{"role": "user", "content": "$prompt"}],
            "temperature": 0.7
        }
    """.trimIndent().toRequestBody(jsonMediaType)

    val request = Request.Builder()
        .url(apiUrl)
        .header("Authorization", "Bearer $apiKey")
        .header("Content-Type", "application/json")
        .post(requestBody)
        .build()

    val response = client.newCall(request).execute()

    if (response.isSuccessful) {
        val responseBody = response.body?.string()
        return responseBody ?: "No se pudo obtener una respuesta del servidor."
    } else {
        return "Error en la solicitud: ${response.code} ${response.message}"
    }
}