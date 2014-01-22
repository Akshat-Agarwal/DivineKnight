package com.game.divineknight;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
        Parse.initialize(this, "HhBEX6TDz1dKGqQe9EL3uKZp60wkJdYDaZsWO68G", "ALHH3M3T3ZldRdf9nO04HNcgIICk9JzUIf6TZnfj");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
