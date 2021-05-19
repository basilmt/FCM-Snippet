# FCM-Snippet
Firebase Cloud Messaging snippet
Using HTTP V1 api
without using server

## Things to get started
* goto this [url](https://console.firebase.google.com/u/0/project/_/settings/serviceaccounts/adminsdk)
* Select firebase admin sdk and then click on generate new private key. Rename the downloaded file into service_account.json
* Copy that file into res/raw directory

In MainActivity.java  
Find function getTokenForNotification.  
Replace the token value with the token of the person whom you want to sent the notification.


### Important : In module level build.gradle file
```gradle
android{
packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
    }
    .
    .
    .
    
    implementation 'com.android.volley:volley:1.2.0'
    implementation 'com.google.auth:google-auth-library-oauth2-http:0.25.3'
}
```
