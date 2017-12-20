package com.bridgeit.ToDoApp.email;

public class EmailProperties {
		     String email;
			 String password;
			 String emailAddress;
			public String getEmailAddress() {
				return emailAddress;
			}
			public void setEmailAddress(String emailAddress) {
				this.emailAddress = emailAddress;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			@Override
			public String toString() {
				return "EmailProperties [email=" + email + ", password=" + password + ", emailAddress=" + emailAddress
						+ "]";
			}
			
}
