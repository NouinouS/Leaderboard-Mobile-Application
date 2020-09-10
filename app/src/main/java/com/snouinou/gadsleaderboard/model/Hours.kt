package com.snouinou.gadsleaderboard.model

data class Hours(
                 var name: String = "",
                 var hours: Int = 0,
                 var badge: String = "",
                 var country: String = ""
) : Score {
    override fun getTitle(): String {
        return name
    }

    override fun getDesc(): String {
        return "$hours learning hours, $country"
    }

    override fun getImage(): String {
        return badge
    }
}