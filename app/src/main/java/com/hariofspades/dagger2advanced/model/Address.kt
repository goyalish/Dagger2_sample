package com.hariofspades.dagger2advanced.model

import com.google.gson.annotations.SerializedName

data class Address(

	@field:SerializedName("Suburb")
	val suburb: String? = null,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("PostCode")
	val postCode: String? = null,

	@field:SerializedName("Line1")
	val line1: String? = null,

	@field:SerializedName("Line2")
	val line2: String? = null
)