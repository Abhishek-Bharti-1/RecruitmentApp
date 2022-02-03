package com.androrubin.recruitmentApp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Details(
    val post: String? =null,
    val company_name: String? = null,
    val salary_range: String? =null,
    val job_des: String? = null,
    val working_hrs: String? = null,
    val min_qual: String? = null,
    val experience: String? = null,
    val skills: String? = null,
    val address: String? = null) {

    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.

}