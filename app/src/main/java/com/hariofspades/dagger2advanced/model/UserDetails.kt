package com.hariofspades.dagger2advanced.model

import com.google.gson.annotations.SerializedName

data class UserDetails(

	@field:SerializedName("ProgramRSN")
	val programRSN: String? = null,

	@field:SerializedName("PersonalDetails")
	val personalDetails: PersonalDetails? = null
)