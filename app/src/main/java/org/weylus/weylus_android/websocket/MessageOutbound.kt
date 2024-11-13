package org.weylus.weylus_android.websocket

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Messages going out from the app to the server
 */
interface MessageOutbound {

    fun message(): String

    class PointerEvent: MessageOutbound {
        override fun message(): String {
            TODO("Not yet implemented")
        }

    }

    class WheelEvent: MessageOutbound {
        override fun message(): String {
            TODO("Not yet implemented")
        }

    }

    class KeyboardEvent: MessageOutbound {
        override fun message(): String {
            TODO("Not yet implemented")
        }

    }

    class GetCapturableList: MessageOutbound {
        override fun message(): String {
            return "\"GetCapturableList\""
        }

    }

    class Config(private val clientConfiguration: ClientConfiguration): MessageOutbound {
        override fun message(): String {
            val configJson = Json.encodeToString(this.clientConfiguration)
            return "{\"Config\": $configJson}"
        }

    }

    class PauseVideo: MessageOutbound {
        override fun message(): String {
            return "\"PauseVideo\""
        }

    }

    class ResumeVideo: MessageOutbound {
        override fun message(): String {
            return "\"ResumeVideo\""
        }

    }

    class ChooseCustomInputAreas: MessageOutbound {
        override fun message(): String {
            return "\"ChooseCustomInputAreas\""
        }

    }

}