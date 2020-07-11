package com.example.innofiedtest.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {

	@SerializedName("last_name")
	private String lastName;

	@PrimaryKey
	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return firstName+" "+lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"last_name = '" + lastName + '\'' + 
			",id = '" + id + '\'' + 
			",avatar = '" + avatar + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			",name = '" + firstName+" "+lastName + '\'' +
			"}";
		}

	public static DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
		@Override
		public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
			return oldItem.id==newItem.id;
		}

		@Override
		public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
			return oldItem.equals(newItem);
		}
	};
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		User user = (User) obj;
		return user.id==this.id;
	}

}