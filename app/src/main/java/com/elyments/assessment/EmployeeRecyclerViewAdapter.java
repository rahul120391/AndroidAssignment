package com.elyments.assessment;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

import static com.elyments.assessment.Constants.EMPLOYEE_DETAIL;
import static com.elyments.assessment.Constants.EMPLOYEE_ID;
import static com.elyments.assessment.Constants.EMPLOYEE_NAME;

public class EmployeeRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ViewHolder> {

	    private static List<Employee> mValues;

	    public interface IViewAdapter{
		void onRowClick(Bundle bundle);
	     }

	    private static IViewAdapter iViewAdapter;

		public EmployeeRecyclerViewAdapter(IViewAdapter iViewAdapterr) {
			    iViewAdapter=iViewAdapterr;
				Realm realm = Realm.getDefaultInstance();
				mValues = realm.copyFromRealm(realm.where(Employee.class).findAll());
				realm.close();
		}

		public EmployeeRecyclerViewAdapter(List<Employee> items) {
				mValues = items;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext())
								.inflate(R.layout.employee_list_row, parent, false);
				return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
				holder.mItem = mValues.get(position);
				holder.mIdView.setText(String.valueOf(mValues.get(position).id));
				holder.mContentView.setText(mValues.get(position).name);
		}

		@Override
		public int getItemCount() {
				return mValues.size();
		}

		public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
				public final View mView;
				public final TextView mIdView;
				public final TextView mContentView;
				public Employee mItem;

				public ViewHolder(View view) {
						super(view);
						mView = view;
						mView.setOnClickListener(this);
						mIdView = (TextView) view.findViewById(R.id.item_number);
						mContentView = (TextView) view.findViewById(R.id.content);
				}

			@Override
			public void onClick(View v) {
				  if(getAdapterPosition()>-1){
					 int id= mValues.get(getAdapterPosition()).id;
					 String name=mValues.get(getAdapterPosition()).name;
					 String details=mValues.get(getAdapterPosition()).details;
					  Bundle bundle = new Bundle();
					  bundle.putString(EMPLOYEE_ID,String.valueOf(id));
					  bundle.putString(EMPLOYEE_NAME,name);
					  bundle.putString(EMPLOYEE_DETAIL,details);
					  iViewAdapter.onRowClick(bundle);
				  }
			}

			@Override
				public String toString() {
						return super.toString() + " '" + mContentView.getText() + "'";
				}
		}

		public void updateResult(){
			Realm realm = Realm.getDefaultInstance();
			mValues = realm.copyFromRealm(realm.where(Employee.class).findAll());
			realm.close();
			notifyDataSetChanged();
		}
}