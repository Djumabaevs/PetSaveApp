package com.bignerdranch.android.petsaveapp.core.data.api

class ReportManager {

  private val serverAuthenticator = Authenticator()
  private var clientPublicKeyString = ""

  fun login(userIDString: String, publicKeyString: String) : String {
    clientPublicKeyString = publicKeyString
    return serverAuthenticator.publicKey()
  }

  fun sendReport(report: Map<String, Any>, callback: (Map<String, Any>) -> Unit) {
    GlobalScope.launch(Default) {
      delay(1000L)
      withContext(Main) {
        var result: Map<String, Any> = mapOf("success" to false)
        if (report.isNotEmpty()) {
          val applicationID = report["application_id"] as Long
          val reportID = report["report_id"] as String
          val reportString = report["report"] as String
          val stringToVerify = "$applicationID+$reportID+$reportString"
          val bytesToVerify = stringToVerify.toByteArray(Charsets.UTF_8)

          val signature = report["signature"] as String
          val signatureBytes = Base64.decode(signature, Base64.NO_WRAP)

          val success = serverAuthenticator.verify(signatureBytes, bytesToVerify,
              clientPublicKeyString)
          if (success) {
            //Process data
            val confirmationCode = UUID.randomUUID().toString()
            val bytesToSign = confirmationCode.toByteArray(Charsets.UTF_8) // 1
            val signedData = serverAuthenticator.sign(bytesToSign) // 2
            val requestSignature = Base64.encodeToString(signedData, Base64.NO_WRAP) // 3
            result = mapOf("success" to true,
                "confirmation_code" to confirmationCode,
                "signature" to requestSignature)
          }
        }
        callback(result)
      } //withContext(Main) {
    } //GlobalScope.launch(Default) {
  }
}
