package com.example.projectsem4.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectsem4.adapters.CenterInfoAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class CenterSlotsRepository {

    private var slotsLiveData: MutableLiveData<String> = MutableLiveData<String>()
    private var errorLiveData:MutableLiveData<String> = MutableLiveData<String>()
    private var centerProfileUrl : MutableLiveData<String> = MutableLiveData<String>()
    private var uploadImageError : MutableLiveData<String> = MutableLiveData<String>()
    private var centerInfo : MutableLiveData<CenterInfoAdapter> = MutableLiveData<CenterInfoAdapter>()
    private val dbref by lazy {
        Firebase.database
    }

    private fun createCenter( uid : String ,
                              centerName : String ,
                              centerLocation :String
                              , centerSlots : String,
                                cost : String){

        // add db here in realtime db
        val centerInfoAdapter = CenterInfoAdapter(centerName , centerLocation , null , centerSlots,cost)
        dbref.reference.child("centers").child(uid).setValue(centerInfoAdapter)
            .addOnSuccessListener {
                errorLiveData.postValue(null)
                Log.d("center repo", "createCenter: successfully created center in realtime ")
            }
            .addOnFailureListener {
                errorLiveData.postValue(it.toString())
            }
    }

    private fun updateSlots(uid: String , slots : String , cost : String)
    {

        val childUpdates = hashMapOf<String, Any>(
            "/centers/$uid/centerSlots" to slots,
            "/centers/$cost" to cost
        )

        dbref.reference.updateChildren(childUpdates)
            .addOnSuccessListener {
                errorLiveData.postValue(null)
            }
            .addOnFailureListener {
                errorLiveData.postValue(it.toString())
            }
    }

    fun uploadImage(filepath : Uri, uid : String, usertype: String ){
        uploadImageError.postValue("uploading")
        val file = FirebaseStorage.getInstance().reference.child(usertype + "_images/profiles/" + uid + ".jpg")
        file.putFile(filepath)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    errorLiveData.postValue(null)
                    file.downloadUrl.addOnSuccessListener {
                            url->
                        centerProfileUrl.postValue(url.toString())
                        uploadImageError.postValue("done")
                    }
                }
            }
            .addOnFailureListener{
                errorLiveData.postValue("error while uploading : $it")
                uploadImageError.postValue("failed")
            }
    }

    fun getCenterInfo( uid: String )
    {
        dbref.reference.child(uid).get()
            .addOnSuccessListener {
                val center : CenterInfoAdapter
            }
    }

    fun getErrorLiveData() : MutableLiveData<String> {
        return errorLiveData
    }

    fun getUploadImageError() : MutableLiveData<String> {
        return uploadImageError
    }

    fun getCenterProfileUrl() : MutableLiveData<String>{
        return centerProfileUrl
    }

}