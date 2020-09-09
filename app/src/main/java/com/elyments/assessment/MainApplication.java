package com.elyments.assessment;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * The type Main application.
 */
public class MainApplication extends Application {

		/**
		 * The constant TAG.
		 */
		private static final String TAG = MainApplication.class.getSimpleName();

		@Override
		public void onCreate() {
				super.onCreate();
				init(this);
		}

		/**
		 * Init.
		 *
		 * @param context the context
		 */
		private void init(Context context) {
				printFirebaseToken(context);
				initDB(context);
		}

		/**
		 * Print firebase token.
		 */
		private void printFirebaseToken(final Context context) {
				FirebaseInstanceId.getInstance().getInstanceId()
								.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
										@Override
										public void onComplete(@NonNull Task<InstanceIdResult> task) {
												if (!task.isSuccessful()) {
														Log.w(TAG, "getInstanceId failed", task.getException());
														return;
												}
												// Check the task result is null or not
												if (task.getResult() != null) {
														// Get new Instance ID token
														String token = task.getResult().getToken();

														// Prepare the log message
														String msg = getString(R.string.firebase_token, token);

														// Log the message in Logcat
														Log.d(TAG, msg);

														// Show the token via Toast
														Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
												}
										}
								});
		}

		/**
		 * Init db.
		 *
		 * @param context the context
		 */
		private void initDB(Context context) {
				Realm.init(context);
				checkAndAddData();
		}

		/**
		 * Add dummy data.
		 */
		private void checkAndAddData() {
				Realm realm = Realm.getDefaultInstance();

				// Check the database already have sample data or not
				if (realm.where(Employee.class).findFirst() == null) {
						// Insert the Employee List in DB
						RealmList<Employee> employeeList = new RealmList<>();
						employeeList.addAll(EmployeeList.ITEMS);
						realm.beginTransaction();
						realm.insert(employeeList);
						realm.commitTransaction();
				}

				realm.close();
		}

}
