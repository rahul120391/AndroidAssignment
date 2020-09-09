package com.elyments.assessment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

import static com.elyments.assessment.Constants.EMPLOYEE_DETAIL;
import static com.elyments.assessment.Constants.EMPLOYEE_ID;
import static com.elyments.assessment.Constants.EMPLOYEE_NAME;


public class EmployeeDetailFragment extends Fragment implements View.OnClickListener{

		public EmployeeDetailFragment() {
				// Required empty public constructor
		}

        private String details="";
		private String name="";
		private String id="";
		private EditText etEmployeeId,etEmployeeName,etEmployeeDetail;

	@Override
		public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				if (getArguments() != null) {
						details=getArguments().getString(EMPLOYEE_DETAIL);
						name=getArguments().getString(EMPLOYEE_NAME);
						id=getArguments().getString(EMPLOYEE_ID);
				}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			    View view =inflater.inflate(R.layout.fragment_employee_detail, container, false);
			    etEmployeeId=view.findViewById(R.id.etEmployeeId);
			    etEmployeeName=view.findViewById(R.id.etEmployeeName);
			    etEmployeeDetail=view.findViewById(R.id.etEmployeeDetail);
			    Button btnSave = view.findViewById(R.id.btnSave);
			    setData();
			    btnSave.setOnClickListener(this);
				return view;
		}

	/**
	 * This method is used to set the data received when the fragment opens on the fields.
 	 */
    private void setData(){
			etEmployeeId.setText(id);
	    	etEmployeeName.setText(name);
	    	etEmployeeDetail.setText(details);
	}

	/**
	 * This method is used for View Click Events
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		    Realm realmInstance = Realm.getDefaultInstance();
		    String name=etEmployeeName.getText().toString();
		    String details=etEmployeeDetail.getText().toString();
		    realmInstance.executeTransactionAsync(new Realm.Transaction() {
				@Override
				public void execute(Realm realm) {
					Employee employee=realm.where(Employee.class).equalTo(EMPLOYEE_ID,Integer.parseInt(id)).findFirst();
					if(employee!=null){
						Employee updatedEmployee = new Employee(Integer.parseInt(id),name,details);
						realm.copyToRealmOrUpdate(updatedEmployee);
					}
				}
			}, new Realm.Transaction.OnSuccess() {
				@Override
				public void onSuccess() {
					Toast.makeText(requireContext(),getString(R.string.update_success),Toast.LENGTH_SHORT).show();
				}
			}, new Realm.Transaction.OnError() {
				@Override
				public void onError(Throwable error) {
					Toast.makeText(requireContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	/**
	 * This method will be called to set data on text fields when the fragment is visible
	 * @param bundle
	 */
	public void setDataReceived(Bundle bundle){
		details=bundle.getString(EMPLOYEE_DETAIL);
		name=bundle.getString(EMPLOYEE_NAME);
		id=bundle.getString(EMPLOYEE_ID);
		setData();
	}
}