package com.example.food_delivery_app

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun openMail(ctx: Context, email:String){
    val subject = "Subject"
    val message = "Body of email."
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        //putExtra(Intent.EXTRA_SUBJECT, subject)
        //putExtra(Intent.EXTRA_TEXT, message)
    }
    ctx.startActivity(intent)

}
fun mapShow(ctx:Context,geo:String){
    val data= Uri.parse(geo)
    val intent= Intent(Intent.ACTION_VIEW,data)
    ctx.startActivity(intent)
}
fun mymapShow(ctx: Context, latitude: Float, longitude: Float) {
    val uri = Uri.parse("geo:$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    ctx.startActivity(intent)
}


fun sendEmail(ctx:Context,email:String){
    val data= Uri.parse("mailto:$email")
    val intent= Intent(Intent.ACTION_SENDTO,data)
    ctx.startActivity(Intent.createChooser(intent,"SendEmail"))
}
fun openPage(ctx: Context, url: String, url2: String, packages:List<String>) {
    if(isAppInstalled(ctx,packages)){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(intent)
    }else{
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url2))
        ctx.startActivity(intent)
    }
}
fun isAppInstalled(c: Context,packages:List<String>): Boolean{
    var result= false
    for (p in packages){
        if(isPackageInstalled(c,p)){
            result= true;
        }
    }
    return result
}

fun isPackageInstalled(c: Context, targetPackage: String): Boolean {
    val pm = c.packageManager
    try {
        pm.getPackageInfo(targetPackage,0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
}

fun openPhone(ctx: Context, phone:String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")

    }
    ctx.startActivity(intent)

}