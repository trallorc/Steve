import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Green extends BasicObject {

    Image [][] picts =new Image[4][3];
    int row;
    int rows=4;
    int col;
    int cols=3;
    int width;
    int height;



    public Green(String img, int x, int y){
        super(img, x,y);
        init();
    }

    public void init()
    {
        ImageIcon ic = super.im;
        int imageHeight = ic.getIconHeight()*3+25;
        int imageWidth  = ic.getIconWidth()*3;
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
    public String toString() {
        return "Sprite{}";
    }
}
