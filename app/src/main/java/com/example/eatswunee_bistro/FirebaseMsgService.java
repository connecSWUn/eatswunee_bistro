package com.example.eatswunee_bistro;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class FirebaseMsgService extends FirebaseMessagingService {
    private String title;
    private String body;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // TODO: Implement this method to send token to your app server.
        Log.d("MessageToken", "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //수신 된 메세지 처리
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();

            sendNotification(); // 알림 표시
            MainActivity.isNotificationRead = false;
            reloadActivity(); // 화면 새로고침
        }
    }

    public void sendNotification() {
        //알림 클릭시 실행될 액티비티 (PendingIntent)
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = getString(com.google.android.gms.base.R.string.common_google_play_services_notification_channel_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.eatswu_logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH);

        //노티 메니저로 알림 팝업 띄우기
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 오래오 버전 이상부터 channelId 값이 필수가 됨
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "EATSWU_BISTRO_CHANNEL",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public String getActivatedActivityName(Context mContext){ // 현재 사용 중인 최상위 액티비티명 확인

        // 초기 리턴 결과 반환 변수 선언
        String returnActivityName = "";
        try {
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

            String className = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                className = String.valueOf(manager.getAppTasks().get(0).getTaskInfo().topActivity.getClassName());
            }
            else {
                List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);
                ComponentName componentName= info.get(0).topActivity;
                className = componentName.getClassName();
            }

            returnActivityName = className; // 리턴 반환 데이터 삽입
        }
        catch (Exception e){
            //e.printStackTrace();
            Log.i("getNowUseActivity() exception","\n catch " + String.valueOf(e.getMessage()));
        }

        return returnActivityName; // 리턴 결과 반환
    }

    public void reloadActivity() { // 화면 새로고침
        /*if (getActivityActivated(this).equals("com.example.eatswunee_bistro.MainActivity")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/
        if (!getActivatedActivityName(this).equals("com.example.eatswunee_bistro.BistroOrderActivity")) {
            try {
                Class activatedClass = Class.forName(getActivatedActivityName(this));
                Intent intent = new Intent(getApplicationContext(), activatedClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                /*if (!getActivatedActivityName(this).equals("com.example.eatswunee_bistro.MainActivity")) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }*/
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
