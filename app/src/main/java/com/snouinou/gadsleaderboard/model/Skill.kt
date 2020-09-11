package com.snouinou.gadsleaderboard.model

data class Skill(
                 var name: String = "",
                 var score: Int = 0,
                 var badgeUrl: String = "",
                 var country: String = ""
) : Score {
    override fun getTitle(): String {
        return name
    }

    override fun getDesc(): String {
        return "$score Skill IQ score, $country"
    }

    override fun getImage(): String {
        return badgeUrl
    }
}