package com.zachnr.bookplayfree.datastore.utils

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.zachnr.bookplayfree.datastore.UserDataPrefProto
import java.io.InputStream
import java.io.OutputStream

object UserPrefProtoSerializer: Serializer<UserDataPrefProto> {
    override val defaultValue: UserDataPrefProto = UserDataPrefProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDataPrefProto {
        return try {
            UserDataPrefProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserDataPrefProto, output: OutputStream) {
        t.writeTo(output)
    }
}
