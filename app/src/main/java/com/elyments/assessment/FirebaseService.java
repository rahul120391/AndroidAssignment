package com.elyments.assessment;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.realm.Realm;

import static com.elyments.assessment.Constants.EMPLOYEE_DETAIL;
import static com.elyments.assessment.Constants.EMPLOYEE_NAME;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Assuming that the data will come from remoteMessage, get the map and find the key id in the map.
        Map<String,String> data=remoteMessage.getData();
        String ID_KEY = "id";
        String CHANNEL_ID="PUSH_NOTIFICATION";
        if(!data.isEmpty() && data.containsKey(ID_KEY) && data.get(ID_KEY)!=null){
            String id=data.get(ID_KEY);
            Realm realm=Realm.getDefaultInstance();
            Employee employee=realm.where(Employee.class).equalTo(ID_KEY,Integer.parseInt(id)).findFirst();
            realm.close();
            if(employee!=null){
                String details=employee.details;
                String name = employee.name;
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(ID_KEY,id);
                intent.putExtra(EMPLOYEE_NAME,name);
                intent.putExtra(EMPLOYEE_DETAIL,details);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(id)
                        .setContentText(name)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify((int)System.currentTimeMillis(), builder.build());
            }
        }
    }
}
