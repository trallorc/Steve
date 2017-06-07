import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Type1 extends BasicObject {

	Image [][] picts =new Image[4][3];
	int row;
	int rows=4;
	int col;
	int cols=3;
	int width;
	int height;
    int health, damage, speed, range;


	public Type1(String img, int x, int y, int t){
		super(img, x,y,t);
        health= 5;
        damage= 3;
        speed= 3;
		range=1;
		init();
	}

	public void init()
	{
		ImageIcon ic = super.im;
	    int imageHeight = ic.getIconHeight();
	    int imageWidth  = ic.getIconWidth();
	    //System.out.println("imageHeight="+imageHeight);
	    //System.out.println("imageWidth="+imageWidth);
		BufferedImage bimg = new BufferedImage(imageWidth ,imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics gr = bimg.getGraphics();
		gr.drawImage(ic.getImage(),0,0,null);
		int px=0,py=0;
		width=imageWidth/cols;
		height=imageHeight/rows;
		for(int i=0;i<rows;i++){
			px=0;
			//System.out.println("px="+px+" py="+py);
			for(int j=0;j<cols;j++){

				picts[i][j]=bimg.getSubimage(px, py, width, height);
				px+=width;

			}
			py+=height;
		}
	}



	@Override
	public void update(int x, int y) {
		this.x=x;
		this.y=y;
		//System.out.println("in update");
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(picts[row][col], (int)x, (int)y, width, height, null);

	}

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
		return speed;
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
    public String toString() {
        return "Sprite{" +
                "health=" + health +
                ", damage=" + damage +
                ", speed=" + speed +
                '}';
    }
}
