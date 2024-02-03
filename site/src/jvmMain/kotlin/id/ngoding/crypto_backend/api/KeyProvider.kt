package id.ngoding.crypto_backend.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import id.ngoding.crypto_backend.model.Keys
import id.ngoding.crypto_backend.util.DataEncryption
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Api("provide")
suspend fun keyProvider(context: ApiContext) {
    val firstKey = "12345"
    val secondKey = "67890"

    try {
        val publicKey = context.req.body?.decodeToString()

        if (publicKey != null) {
             val encryptedData = DataEncryption.encrypt(
                 context = context,
                 data = Json.encodeToString(
                     Keys(
                         firstKey = firstKey,
                         secondKey = secondKey
                     )
                 ),
                 generatedPublicKey = publicKey
             )

            if (encryptedData != null) {
                context.logger.info("Keys successfully encrypted")
                context.res.status = 200
                context.res.setBodyText(Json.encodeToString(encryptedData))
            } else {
                context.logger.error("Failed to encrypt data")
                context.res.status = 400
            }
        } else {
            context.logger.error("Public Key is null")
            context.res.status = 400
        }
    } catch (e: Exception) {
        context.logger.error(e.message.toString())
        context.res.status = 400
    }
}