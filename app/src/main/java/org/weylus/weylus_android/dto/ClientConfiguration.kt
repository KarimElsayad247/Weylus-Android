package org.weylus.weylus_android.dto

import kotlinx.serialization.*

@Serializable
data class ClientConfiguration(

    @SerialName("uinput_support")
    var uinputSupport: Boolean,

    @SerialName("capturable_id")
    var capturableId: Int,

    @SerialName("capture_cursor")
    var captureCursor: Boolean,

    @SerialName("max_width")
    var maxWidth: Int,

    @SerialName("max_height")
    var maxHeight: Int,

    @SerialName("client_name")
    var clientName: String?,

    @SerialName("frame_rate")
    var frameRate: Double,
)