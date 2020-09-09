package com.elyments.assessment;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Employee extends RealmObject implements Parcelable {

	    @PrimaryKey
		public int id;
		public String name;
		public String details;

		public Employee() {
		}

		public Employee(int id, String name, String details) {
				this.id = id;
				this.name = name;
				this.details = details;
		}

		@Override
		public String toString() {
				return name;
		}

		@Override
		public int describeContents() {
				return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
				dest.writeInt(this.id);
				dest.writeString(this.name);
				dest.writeString(this.details);
		}

		protected Employee(Parcel in) {
				this.id = in.readInt();
				this.name = in.readString();
				this.details = in.readString();
		}

		public static final Creator<Employee> CREATOR = new Creator<Employee>() {
				@Override
				public Employee createFromParcel(Parcel source) {
						return new Employee(source);
				}

				@Override
				public Employee[] newArray(int size) {
						return new Employee[size];
				}
		};
}
