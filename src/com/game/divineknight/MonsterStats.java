package com.game.divineknight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MonsterStats extends Activity {
	// Declare Variables
	double monsterLevel;
	double goldGranted;
	String monsterName;
	String monsterImage;
	String monsterLore;
	private HeroResources heroResource;
	private MonsterListResources monsterObject;
	ImageLoader imageLoader = new ImageLoader(this);
	Button BtnMonsterStatsFight;
	Boolean keepFighting = true;
	Boolean monsterDied = false;
	Boolean heroDied = false;
	TextView TVMonsterStatsFooterMonsterName;
	TextView TVMonsterStatsFooterGoldGranted;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.monster_stats);
		
		Intent i = getIntent();
		heroResource = (HeroResources) i.getSerializableExtra("heroResource");
		monsterObject = (MonsterListResources) i.getSerializableExtra("monsterObject");
		
		// Locate the TextViews in monster_stats.xml
		TextView TVMonsterStatsName = (TextView) findViewById(R.id.TVMonsterStatsName);
		TextView TVMonsterStatsLevel = (TextView) findViewById(R.id.TVMonsterStatsLevel);
		TextView TVMonsterStatsGoldGranted = (TextView) findViewById(R.id.TVMonsterStatsGoldGranted);
		TextView TVMonsterStatsMonsterLore = (TextView) findViewById(R.id.TVMonsterStatsMonsterLore);
		TextView TVMonsterStatsFooterMonsterName = (TextView) findViewById(R.id.TVMonsterStatsFooterMonsterName);
		TextView TVMonsterStatsFooterGoldGranted = (TextView) findViewById(R.id.TVMonsterStatsFooterGoldGranted);
		ImageView TVMonsterStatsImage = (ImageView) findViewById(R.id.TVMonsterStatsImage);
		Button BtnMonsterStatsFight = (Button) findViewById(R.id.BtnMonsterStatsFight);
		
		monsterLevel = monsterObject.getLevel();
		goldGranted = monsterObject.getGoldReward();
		monsterName = monsterObject.getName();
		monsterImage = monsterObject.getMonsterImage();
		monsterLore = monsterObject.getMonsterLore();
		
		// Set results to the TextViews
		TVMonsterStatsName.setText("Level: " + (int)monsterLevel);
		TVMonsterStatsLevel.setText("Name: " + monsterName);
		TVMonsterStatsGoldGranted.setText("Gold Granted: " + (int)goldGranted);
		TVMonsterStatsMonsterLore.setText(monsterLore);
		TVMonsterStatsFooterMonsterName.setText(monsterName);
		TVMonsterStatsFooterGoldGranted.setText("Gold granted: " + (int)goldGranted);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(monsterImage, TVMonsterStatsImage);
		
		BtnMonsterStatsFight.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				double monsterAttack = monsterObject.getAttack();
				double monsterDefence = monsterObject.getDefence();
				double monsterHitPoints = monsterObject.getHitPoints();
				double heroAttack = heroResource.getAttack();
				double heroDefence = heroResource.getDefence();
				double heroHitPoints = heroResource.getHitPoints();
				double damageToMonster;
				double damageToHero;
				while(keepFighting){
					damageToMonster = heroAttack - monsterDefence;
					damageToHero = monsterAttack - heroDefence;
					
					if(damageToMonster > 0){
						monsterHitPoints -= damageToMonster;
					}
					if(damageToHero > 0){
						heroHitPoints -= damageToHero;
					}
					
					//Incase hero and monster both do 0 damage to each other, both lose 1 hp
					//Its like implementing "struggle" from pokemon when pokemon is out of move points
					//but in this case the hero/monster doesnt hurt itself in the process :)
					if(damageToHero <=0 && damageToMonster <= 0){
						heroHitPoints -= 1;
						monsterHitPoints -= 1;
					}
					
					if(monsterHitPoints <= 0){
						keepFighting = false;
						monsterDied = true;
					}else{
						if(heroHitPoints <= 0){
							keepFighting = false;
							heroDied = true;
						}
					}
				}
				
				if(monsterDied){
					showEndFightDialog(0);
					// code to update level
					heroResourceUpdater(heroResource, monsterObject);
					
				}else{
					showEndFightDialog(1);
				}
				
			}
			
		});
		
	}
	 
	private void heroResourceUpdater(HeroResources heroResource, MonsterListResources monsterObject) {

		double currentExp = heroResource.getExperience();
		double currentHeroLevel = heroResource.getLevel();
		double newHeroExp = currentExp + monsterObject.getExpGranted();
		double goldGranted = monsterObject.getGoldReward();
		double newHeroGold = goldGranted + heroResource.getGold();
		double newHeroLevel = Math.floor((Math.pow(newHeroExp, (1/1.5))/10));
		
		ParseObject parseHero = ParseObject.createWithoutData("Hero", heroResource.getHeroObjectID());
		
		if (newHeroLevel != currentHeroLevel){
			Toast.makeText(getApplicationContext(), "You just Levelled up!", Toast.LENGTH_LONG).show();
			parseHero.put("hasUnspentStats", true);
		}
		
		parseHero.put("Experience", newHeroExp);
		parseHero.put("Gold", newHeroGold);		
		parseHero.put("Level", newHeroLevel);
		try {
			parseHero.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays a dialog box showing user who died (monster-0 or hero-1) 
	 * and takes user to monsterList page afterwards.
	 * @param whoDied
	 */
	protected void showEndFightDialog(int whoDied) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MonsterStats.this);
		builder.setCancelable(false);
		if(whoDied == 0){
			builder.setMessage("Wow, You just killed a " + monsterObject.getName() + ", way to go!");
		}else{
			builder.setMessage("Whoa! " + monsterObject.getName() + " was too strong for you, consider killing lower level monsters to level up");
		}
		builder.setPositiveButton("Okay!", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent (MonsterStats.this, MonsterListPage.class);
				intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity(intent);
			}
			
		});
		builder.create();
		builder.show();
		
	}
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.action_bar, menu);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        setTitle("Monster Stats");
	        return true;
		}
		
		 
		 @Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		 super.onOptionsItemSelected(item);
		 switch(item.getItemId())
		 {	 
		 
		 case android.R.id.home:
		 MonsterStats.this.onBackPressed(); 
	     break;	
	     
		 case R.id.monsterList:
	 	 Intent intent = new Intent(MonsterStats.this, MonsterListPage.class);
	 	 startActivity(intent);
		 break;
		 
		 case R.id.HeroStats:
		 Intent intent2 = new Intent(MonsterStats.this, HeroStats.class);
		 startActivity(intent2);
		 break;
		 	 
		 case R.id.logOut:
		 ParseUser.logOut();
		 Intent intent3 = new Intent(MonsterStats.this, LoginSignupActivity.class);
		 intent3.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
		 startActivity(intent3);
		 break;
		 }
		 return true;	
		 }
		 
		 
}