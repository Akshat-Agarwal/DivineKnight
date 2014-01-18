package com.game.divineknight;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MonsterListPage extends Activity {
	// Declare Variables
	ListView listview;
	List<ParseObject> monsterObjectList;
	ProgressDialog mProgressDialog;
	MonsterListViewAdapter adapter;
	private List<MonsterListResources> MonsterList = null;
	private HeroResources heroResource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monster_list);
		new RemoteDataTask().execute();
		
		getOverflowMenu();
	}
	 
	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(MonsterListPage.this);
			mProgressDialog.setMessage("Getting monster list...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			MonsterList = new ArrayList<MonsterListResources>();
			try {
				// Locate the class table named "Monster" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Monster");
				query.orderByAscending("Level");
				monsterObjectList = query.find();
				for (ParseObject monster : monsterObjectList) {
					// Locate images in flag column
					ParseFile image = (ParseFile) monster.get("MonsterImage");

					MonsterListResources map = new MonsterListResources();
					map.setLevel(monster.getDouble("Level"));
					map.setName((String) monster.get("Name"));
					map.setGoldReward(monster.getDouble("GoldGranted"));
					map.setMonsterImage(image.getUrl());
					map.setMonsterLore(monster.getString("Lore"));
					map.setAttack(monster.getDouble("Attack"));
					map.setDefence(monster.getDouble("Defence"));
					map.setHitPoints(monster.getDouble("Hitpoints"));
					map.setExpGranted(monster.getDouble("ExpGranted"));
					MonsterList.add(map);
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			
			//To get hero into the battle field
			ParseQuery<ParseObject> heroQuery = new ParseQuery<ParseObject>("Hero");
			heroQuery.whereEqualTo("hasUser", ParseUser.getCurrentUser());
			ParseObject myHero =  new ParseObject("Hero");
			heroResource = new HeroResources();
			try {
				myHero = heroQuery.getFirst();
				heroResource.setAttack(myHero.getDouble("Attack"));
				heroResource.setDefence(myHero.getDouble("Defence"));
				heroResource.setExperience(myHero.getDouble("Experience"));
				heroResource.setGold(myHero.getDouble("Gold"));
				heroResource.setHeroName(myHero.getString("HeroName"));
				heroResource.setHeroObjectID(myHero.getObjectId());
				heroResource.setHitPoints(myHero.getDouble("Hitpoints"));
				heroResource.setLevel(myHero.getDouble("Level"));
				heroResource.setUserID(ParseUser.getCurrentUser().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new MonsterListViewAdapter(MonsterListPage.this, MonsterList, heroResource);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        setTitle("");
        return true;
	}
	 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	 super.onOptionsItemSelected(item);
	 switch(item.getItemId())
	 {	 
	 case R.id.logOut:
	 ParseUser.logOut();
	 Intent intent2 = new Intent(MonsterListPage.this, LoginSignupActivity.class);
	 //might give you a slight hiccup using cloud code
	 intent2.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
	 startActivity(intent2);
	 break;
	 
	 case R.id.HeroStats:
	 Intent intent = new Intent(MonsterListPage.this, HeroStats.class);
	 startActivity(intent);
	 break;
	 }
	 return true;	
	 }
	
	 private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}