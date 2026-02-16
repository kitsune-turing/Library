package com.kitsune.tech.library.data.api.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class DescriptionAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Description? {
        return when (reader.peek()) {
            JsonReader.Token.STRING -> {
                Description.TextDescription(reader.nextString())
            }
            JsonReader.Token.BEGIN_OBJECT -> {
                reader.beginObject()
                var value: String? = null
                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        "value" -> value = reader.nextString()
                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                value?.let { Description.ObjectDescription(it) }
            }
            else -> {
                reader.skipValue()
                null
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Description?) {
        when (value) {
            is Description.TextDescription -> writer.value(value.value)
            is Description.ObjectDescription -> {
                writer.beginObject()
                writer.name("value").value(value.value)
                writer.endObject()
            }
            null -> writer.nullValue()
        }
    }
}
