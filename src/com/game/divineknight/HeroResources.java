package com.game.divineknight;

import java.io.Serializable;

public class HeroResources implements Serializable{

	private double attack;
	private double defence;
	private double experience;
	private double gold;
	private String heroName;
	private double hitPoints;
	private double Level;
	private String userID;
	private String heroObjectID;
	
	public double getAttack() {
		return attack;
	}
	public void setAttack(double attack) {
		this.attack = attack;
	}
	public double getDefence() {
		return defence;
	}
	public void setDefence(double defence) {
		this.defence = defence;
	}
	public double getExperience() {
		return experience;
	}
	public void setExperience(double experience) {
		this.experience = experience;
	}
	public double getGold() {
		return gold;
	}
	public void setGold(double gold) {
		this.gold = gold;
	}
	public String getHeroName() {
		return heroName;
	}
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	public double getHitPoints() {
		return hitPoints;
	}
	public void setHitPoints(double hitPoints) {
		this.hitPoints = hitPoints;
	}
	public double getLevel() {
		return Level;
	}
	public void setLevel(double level) {
		Level = level;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getHeroObjectID() {
		return heroObjectID;
	}
	public void setHeroObjectID(String heroObjectID) {
		this.heroObjectID = heroObjectID;
	}
	
}
