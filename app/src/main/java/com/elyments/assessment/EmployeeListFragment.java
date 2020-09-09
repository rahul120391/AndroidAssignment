package com.elyments.assessment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;

import static com.elyments.assessment.Constants.EMPLOYEE_DETAILS;

public class EmployeeListFragment extends Fragment implements EmployeeRecyclerViewAdapter.IViewAdapter, SwipeRefreshLayout.OnRefreshListener {

		public EmployeeListFragment() {
		}
        private SwipeRefreshLayout swipeToRefresh;
		private EmployeeRecyclerViewAdapter adapter;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
				View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
				RecyclerView recyclerView = view.findViewById(R.id.list);
			    swipeToRefresh=view.findViewById(R.id.swipeToRefresh);
			    swipeToRefresh.setOnRefreshListener(this);
				// Set the adapter
			    adapter=new EmployeeRecyclerViewAdapter(this);
				recyclerView.setAdapter(adapter);
				return view;
		}

	    @Override
	    public void onRowClick(Bundle bundle) {
			((MainActivity) requireActivity()).performFragmentTransaction(EmployeeDetailFragment.class,bundle,EMPLOYEE_DETAILS);
	    }


	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override public void run() {
				swipeToRefresh.setRefreshing(false);
				adapter.updateResult();
			}
		}, 1000);
	}
}