package com.example.projectsem4.adapters

class UserAdapter(
    name: String?,
    email: String?,
    age: String?,
    profileUrl: String?,
    usertype: String?,
    mobile : String?
) {

    private var userName : String? = name
    private var userEmail : String? = email
    private var userAge : String? = age
    private var userProfileUrl : String? = profileUrl
    private var userType : String? = usertype
    private var userMobile : String? = mobile

    fun getUserName() : String? {
        return userName
    }

    fun getUserEmail() : String?{
        return userEmail
    }

    fun getUserAge() : String?{
        return userAge
    }

    fun getUserProfileUrl() : String?{
        return userProfileUrl
    }

    fun getUserMobile() : String?{
        return userMobile
    }

    fun setUserName(name : String?) {
        userName = name
    }

    fun setUserEmail(email : String?) {
        userEmail = email
    }

    fun setUserAge(age : String?) {
        userAge = age
    }

    fun setUserProfileUrl(profileUrl: String?){
        userProfileUrl = profileUrl
    }

    fun setUserMobile(mobile : String?) {
        userMobile = mobile
    }

}