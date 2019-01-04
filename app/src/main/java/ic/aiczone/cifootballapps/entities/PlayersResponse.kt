package ic.aiczone.cifootballapps.entities

import com.google.gson.annotations.SerializedName

data class PlayersResponse(
        @SerializedName("player") val players: List<Player>
)