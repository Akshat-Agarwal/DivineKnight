package com.game.divineknight;

import java.io.Serializable;

public class MonsterListResources implements Serializable{
	/**
	 * sdsas
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private double level;
	private double GoldReward;
	private String monsterImage;
	private String monsterLore;
	private double attack;
	private double defence;
	private double hitPoints;
	private double expGranted;
	
	public double getLevel() {
		return level;
	}

	public void setLevel(double i) {
		this.level = i;
	}

	public double getGoldReward() {
		return GoldReward;
	}

	public void setGoldReward(double i) {
		this.GoldReward = i;
	}

	public String getMonsterImage() {
		return monsterImage;
	}

	public void setMonsterImage(String flag) {
		this.monsterImage = flag;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMonsterLore() {
		return monsterLore;
	}

	public void setMonsterLore(String monsterLore) {
		this.monsterLore = monsterLore;
	}

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

	public double getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(double hitPoints) {
		this.hitPoints = hitPoints;
	}

	public double getExpGranted() {
		return expGranted;
	}

	public void setExpGranted(double expGranted) {
		this.expGranted = expGranted;
	}
}