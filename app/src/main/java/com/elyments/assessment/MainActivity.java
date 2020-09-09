package com.elyments.assessment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static com.elyments.assessment.Constants.EMPLOYEE_DETAILS;
import static com.elyments.assessment.Constants.EMPLOYEE_LIST;
import static com.elyments.assessment.Constants.EMPLOYEE_DETAIL;
import static com.elyments.assessment.Constants.EMPLOYEE_ID;
import static com.elyments.assessment.Constants.EMPLOYEE_NAME;

public class MainActivity extends AppCompatActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				findViewById(R.id.fab).setOnClickListener(view -> {
						new AlertDialog.Builder(this)
										.setMessage(getString(R.string.instructions))
										.setPositiveButton("OK", null)
										.create()
										.show();
				});
			performFragmentTransaction(EmployeeListFragment.class,null,EMPLOYEE_LIST);
		}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(intent!=null){
			String id=intent.getStringExtra(EMPLOYEE_ID);
			String name=intent.getStringExtra(EMPLOYEE_NAME);
			String details=intent.getStringExtra(EMPLOYEE_DETAIL);
			Bundle bundle = new Bundle();
			bundle.putString(EMPLOYEE_ID,id);
			bundle.putString(EMPLOYEE_NAME,name);
			bundle.putString(EMPLOYEE_DETAIL,details);
			Fragment fragment=getSupportFragmentManager().findFragmentByTag(EMPLOYEE_DETAILS);
			if(fragment==null){
				performFragmentTransaction(EmployeeDetailFragment.class,bundle,EMPLOYEE_DETAILS);
			}
			else{
				if(fragment.isVisible()){
					((EmployeeDetailFragment)fragment).setDataReceived(bundle);
				}
			}
		}

	}

	/**
	 * This method is used to perform fragment transaction
	 * @param fragmentClass
	 * @param arguments
	 * @param tag
	 */
	public void performFragmentTransaction(Class fragmentClass,Bundle arguments,String tag){
			getSupportFragmentManager().beginTransaction()
					                   .replace(R.id.fragment_container_view,fragmentClass,arguments)
					                   .addToBackStack(tag)
					                   .commit();
	}


	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().getBackStackEntryCount()>1){
			getSupportFragmentManager().popBackStack();
		}
		else{
			finish();
		}
	}
}