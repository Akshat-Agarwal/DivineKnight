package com.game.divineknight;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HeroStats extends Activity{

	TextView TVHeroStatsTitle;
	TextView TVHeroStatsHitPoints;
	TextView TVHeroStatsLevel;
	TextView TVHeroStatsAttack;
	TextView TVHeroStatsDefence;
	TextView TVHeroStatsGold;
	ProgressDialog mProgressDialog;
	ParseObject HeroResource = new ParseObject("Hero");
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.hero_stats_page);
		
		TextView TVHeroStatsTitle = (TextView) findViewById(R.id.TVHeroStatsTitle);
		TextView TVHeroStatsHitPoints = (TextView) findViewById(R.id.TVHeroStatsHitPoints);
		TextView TVHeroStatsLevel = (TextView) findViewById(R.id.TVHeroStatsLevel);
		TextView TVHeroStatsAttack = (TextView) findViewById(R.id.TVHeroStatsAttack);
		TextView TVHeroStatsDefence = (TextView) findViewById(R.id.TVHeroStatsDefence);
		TextView TVHeroStatsGold = (TextView) findViewById(R.id.TVHeroStatsGold);
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Hero");
		query.whereEqualTo("hasUser", ParseUser.getCurrentUser());
		try {
			HeroResource = query.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TVHeroStatsTitle.setText(HeroResource.getString("HeroName") + "s' stats");
		TVHeroStatsHitPoints.setText("Hitpoints: " + (int)HeroResource.getDouble("Hitpoints"));
		TVHeroStatsLevel.setText("Level: " + (int)HeroResource.getDouble("Level"));
		TVHeroStatsAttack.setText("Attack: " + (int)HeroResource.getDouble("Attack"));
		TVHeroStatsDefence.setText("Defence: " + (int)HeroResource.getDouble("Defence"));
		TVHeroStatsGold.setText("Gold: " + (int)HeroResource.getDouble("Gold"));
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        return true;
	}
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	 super.onOptionsItemSelected(item);
	 switch(item.getItemId())
	 {	 
	 
	 case android.R.id.home:
	 HeroStats.this.onBackPressed(); 
    break;	
    
	 case R.id.monsterList:
	 Intent intent = new Intent(HeroStats.this, MonsterListPage.class);
	 startActivity(intent);
	 break;
	 
	 case R.id.HeroStats:
	 Intent intent2 = new Intent(HeroStats.this, HeroStats.class);
	 startActivity(intent2);
	 break;
	 	 
	 case R.id.logOut:
	 ParseUser.logOut();
	 Intent intent3 = new Intent(HeroStats.this, LoginSignupActivity.class);
	 intent3.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
	 startActivity(intent3);
	 break;
	 }
	 return true;	
	 }
}
