package com.hariofspades.dagger2advanced.model

import com.google.gson.annotations.SerializedName

data class PersonalDetails(

	@field:SerializedName("DateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("Address")
	val address: Address? = null,

	@field:SerializedName("FamilyName")
	val familyName: String? = null,

	@field:SerializedName("Phone")
	val phone: Phone? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("GivenName")
	val givenName: String? = null,

	@field:SerializedName("EmailAddress")
	val emailAddress: String? = null
)