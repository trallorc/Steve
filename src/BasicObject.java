import javax.swing.*;
import java.awt.*;

public abstract  class BasicObject {
	double x,y;
	ImageIcon im;
	int health, damage, speed, range;
    int team;
	 
	public BasicObject(String img, int x, int y, int team){
		 this.x=x;
		 this.y=y;
         this.team=team;
		 im = new ImageIcon(img);
		 this.health=0;
		 this.damage=0;
		 this.speed=0;
		 this.range=0;
	}


	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public abstract void update(int x, int y);
	 public abstract void draw (Graphics g);
	
}
