package ru.modernsoft.chillonly.ui.player

import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class StreamParser {

    private lateinit var streamUrl: URL
    private var metadata: Map<String, String>? = null
    private var trackTitle = "Unknown"

    fun getTrackDetails(streamUrl: URL): String {
        setStreamUrl(streamUrl)
        try {
            metadata = executeToFetchData()
            if (metadata == null) {
                return trackTitle
            }

            val data = metadata
            if (data!!.containsKey("StreamTitle")) {
                trackTitle = data["StreamTitle"] as String
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return trackTitle
    }

    private fun setStreamUrl(streamUrl: URL) {
        this.metadata = null
        this.streamUrl = streamUrl
    }

    @Throws(IOException::class)
    private fun executeToFetchData(): Map<String, String>? {
        var stream: InputStream? = null
        try {
            val con = streamUrl.openConnection()

            con.setRequestProperty("Icy-MetaData", "1")
            con.connect()

            val metaDataOffset: Int
            val headers = con.headerFields
            stream = con.getInputStream()

            if (headers.containsKey("icy-metaint")) {
                val headerList = headers["icy-metaint"]
                if (headerList != null) {
                    if (headerList.size > 0) {
                        metaDataOffset = Integer.parseInt(headers["icy-metaint"]?.get(0)!!)
                    } else {
                        return null
                    }
                } else {
                    return null
                }
            } else {
                return null
            }

            // In case no data was sent
            if (metaDataOffset == 0) {
                return null
            }

            // Read metadata
            var b: Int = 0
            var count = 0
            var metaDataLength = 4080 // 4080 is the max length
            var inData: Boolean
            val metaData = StringBuilder()
            while (b != -1) {
                b = stream!!.read()
                count++
                if (count == metaDataOffset + 1) {
                    metaDataLength = b * 16
                }
                inData = count > metaDataOffset + 1 && count < metaDataOffset + metaDataLength
                if (inData) {
                    if (b != 0) {
                        metaData.append(b.toChar())
                    }
                }
                if (count > metaDataOffset + metaDataLength) {
                    break
                }

            }
            metadata = parsingMetadata(metaData.toString())
            stream!!.close()
        } catch (e: Exception) {
            Timber.e(e.message)
        } finally {
            stream?.close()
        }
        return metadata
    }

    private fun parsingMetadata(metaString: String): Map<String, String> {
        val metadata = HashMap<String, String>()
        val metaParts = metaString.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val p = Pattern.compile("^([a-zA-Z]+)=\\'([^\\']*)\\'$")
        var m: Matcher
        for (metaPart in metaParts) {
            m = p.matcher(metaPart)
            if (m.find()) {
                metadata.put(m.group(1), m.group(2))
            }
        }
        return metadata
    }
}
