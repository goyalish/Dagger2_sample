package com.hariofspades.dagger2advanced.model

import com.google.gson.annotations.SerializedName

data class Phone(

	@field:SerializedName("STD")
	val sTD: String? = null,

	@field:SerializedName("IDD")
	val iDD: String? = null,

	@field:SerializedName("Local")
	val local: String? = null
)