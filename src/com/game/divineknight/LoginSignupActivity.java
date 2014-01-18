package com.game.divineknight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSignupActivity extends Activity {
	// Declare Variables
	private Button loginButton;
	private Button signup;
	private String passwordtxt;
	String newHeroNameString;	//to hold the string value in the sign up dialog
	String newUserPasswordString;	//to hold the string value in the sign up dialog
	String newUserEmailString;	//to hold the string value in the sign up dialog
	String usernametxt;
	EditText password;
	EditText username;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from main.xml
		setContentView(R.layout.loginsignup);
		// Locate EditTexts in main.xml
		username = (EditText) findViewById(R.id.ETLoginsignupEmail);
		password = (EditText) findViewById(R.id.ETLoginsignupPassword);

		// Locate Buttons in main.xml
		loginButton = (Button) findViewById(R.id.BtnLoginsignupLogin);
		signup = (Button) findViewById(R.id.BtnLoginsignupsignup);

		// Login Button Click Listener
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// If user exist and authenticated, send user to Welcome.class
									Intent intent = new Intent(
											LoginSignupActivity.this,
											MonsterListPage.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
									finish();
								} else {
									if (e.getCode()==100)	//parse exception code for no internet connection
										Toast.makeText(
												getApplicationContext(),
												"No internet connection can be found.",
												Toast.LENGTH_LONG).show();
									if(e.getCode()==101)	//parse exception code for invalid login credentials
										Toast.makeText(
											getApplicationContext(),
											"Invalid email and/or password.",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
		// Sign up Button Click Listener
		signup.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				openSignUpDialog(); 
			}
		});

	}
	
	private void openSignUpDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper(LoginSignupActivity.this, android.R.style.Theme_Holo_Dialog));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.loginsignup_signup, null)).setTitle("Sign up");
		//To inflate a custom view to the view
		View dialogView = inflater.inflate(R.layout.loginsignup_signup, null);
		//declaring to be used on the sign up dialog box
	    final EditText newUsersName = (EditText) dialogView.findViewById(R.id.newUsersName); 
	    final EditText newUserPassword = (EditText) dialogView.findViewById(R.id.newUserPassword);
	    final EditText newUserEmail = (EditText) dialogView.findViewById(R.id.newUserEmail);
	    //setting the custom view to the builder
	    builder.setView(dialogView);
		
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	 
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
              
				// Retrieve the text entered from the EditText
            	newHeroNameString = newUsersName.getText().toString();
				newUserPasswordString = newUserPassword.getText().toString();
				newUserEmailString = newUserEmail.getText().toString();
				
				
					// Force user to fill up the form
					if (newHeroNameString.equals("") || newUserPasswordString.equals("") || newUserEmailString.equals("")) {
						Toast.makeText(getApplicationContext(),
								"Please complete the sign up form",
								Toast.LENGTH_LONG).show();

					} else{
						Toast.makeText(getApplicationContext(),
								"Signing up",
								Toast.LENGTH_LONG).show();
						// Save new user data into Parse.com Data Storage
						ParseUser user = new ParseUser();
						user.setUsername(newUserEmailString);
						user.setPassword(newUserPasswordString);
						user.signUpInBackground(new SignUpCallback() {
							public void done(ParseException e) {
								if (e == null) {
									//User signed up, now log him in
									ParseUser.logInInBackground(newUserEmailString, newUserPasswordString,
											new LogInCallback() {
												public void done(ParseUser user, ParseException e) {
													if (user != null) {
														//create a hero for the user
														ParseObject hero = new ParseObject("Hero");
														hero.put("Attack", 10);
														hero.put("Defence", 10);
														hero.put("Experience", 0);
														hero.put("Gold", 0);
														hero.put("HeroName", newHeroNameString);
														hero.put("Hitpoints", 100);
														hero.put("Level", 1);
														hero.put("hasUser", ParseUser.getCurrentUser());
														try {
															hero.save();
														} catch (ParseException e1) {
															e1.printStackTrace();
														}
														
														// If user is created, send them to featured ads page
														goToMonsterList();
														Toast.makeText(getApplicationContext(),
																"Successfully signed up and logged in",
																Toast.LENGTH_LONG).show();
														finish();
													} else {
														Toast.makeText(
																getApplicationContext(),
																"Invalid email and/or password.",
																Toast.LENGTH_LONG).show(); 
													}
												}
											});
								} else {
									if(e.getCode()==202){ //parse exception code for existing email being there
									Toast.makeText(
											getApplicationContext(),
											"Email " + newUserEmailString + " is already taken" ,
											Toast.LENGTH_LONG).show();
									}
									if (e.getCode()==100){	//parse exception code for no internet connection
										Toast.makeText(
												getApplicationContext(),
												"No internet connection can be found.",
												Toast.LENGTH_LONG).show();
									}
								}
							}
						});
					} 
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        	 
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            	//Nothing happens, the dialog is simply closed
            }
        });
		builder.create();
		builder.show();				
	}
	
	private void goToMonsterList() {
	Intent intent = new Intent(this, MonsterListPage.class);
	startActivity(intent);
	}
	
}
