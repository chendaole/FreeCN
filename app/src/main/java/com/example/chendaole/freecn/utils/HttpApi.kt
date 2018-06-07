package com.example.chendaole.freecn.utils

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object HttpApi {
    private  fun httpGet(strUrl: String, params: Map<String, String>): String {
        var strUrlPath = strUrl
        var result: String ?= null

        val append_url = getRequestData(params)

        strUrlPath = strUrl + "?" + append_url.toString()

        try {
            val url = URL(strUrlPath)
            val urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 5000
            urlConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val _in = InputStreamReader(urlConnect.inputStream)

            val buffer = BufferedReader(_in)

            var inputLine: String ?= null

            result = ""

            while (buffer.readLine().apply { inputLine = this } != null) {
                result += inputLine!! + "\n"
            }

            _in.close()
            urlConnect.disconnect()


        } catch (e : Exception) {
            e.printStackTrace()
        } catch (ioe: IOException) {
            ioe.printStackTrace();
            return  "error:" + ioe.message.toString()
        }

        return result!!
    }

    private fun httpPost(strUrl: String, params: Map<String, String>): String {
        val data = getRequestData(params).toString().toByteArray()

        try {
            val url = URL(strUrl)
            val urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 5000
            urlConnect.doInput = true
            urlConnect.doOutput = true
            urlConnect.requestMethod = "POST"
            urlConnect.useCaches = false

            urlConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            urlConnect.setRequestProperty("Content-Type", data.size.toString())

            val _outStream = urlConnect.outputStream
            _outStream.write(data)

            val response = urlConnect.responseCode

            if (response == HttpURLConnection.HTTP_OK) {
                val _inputStream = urlConnect.inputStream
               return dealResponseData(_inputStream)

            }

        } catch (ioe : IOException) {
            ioe.printStackTrace()
            return "error" + ioe.message.toString()
        }

        return "-1"
    }

    private fun getRequestData(params: Map<String, String>): StringBuffer {
        val stringBuffer = StringBuffer()  //请求信息

        try {

            for ((key, value) in params) {
                stringBuffer.append(key)
                        .append('=')
                        .append(value)
                        .append('&')
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuffer
    }

    private fun dealResponseData(_inputStream: InputStream) : String {
        var resultData : String ? = null
        val byteArrayOutPutStream = ByteArrayOutputStream()
        val data = ByteArray(1024)
        var len = 0
        try {
            while (_inputStream.read(data).apply { len =  this} != -1) {
                byteArrayOutPutStream.write(data, 0, len)
            }

        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        resultData = String(byteArrayOutPutStream.toByteArray())

        return  resultData
    }
}

