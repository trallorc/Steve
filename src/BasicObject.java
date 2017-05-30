import javax.swing.*;
import java.awt.*;

public abstract  class BasicObject {
	double x,y;
	ImageIcon im;
	 
	public BasicObject(String img, int x, int y){
		 this.x=x;
		 this.y=y;
		 im = new ImageIcon(img);
	}
	
	 public abstract void update(int x, int y);
	 public abstract void draw (Graphics g);
	
}
