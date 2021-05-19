package com.revolt.fcmtest.constants;

public interface AllConstants {

    String CHANNEL_ID = "1000";

    String NOTIFICATION_URL = "https://fcm.googleapis.com/v1/projects/<project_id>/messages:send" ;

    String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    String[] SCOPES = { MESSAGING_SCOPE };


}
