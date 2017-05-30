import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class map {
    private map() {
        initGame();
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new map();
            }
        });
    }

    //constants and global variables
    final static Color COLOURBACK = Color.WHITE;
    final static Color COLOURCELL = Color.ORANGE;
    final static Color COLOURGRID = Color.BLACK;
    final static Color COLOURONE = new Color(255, 255, 255, 200);
    final static Color COLOURONETXT = Color.BLUE;
    final static Color COLOURTWO = new Color(0, 0, 0, 200);
    final static Color COLOURTWOTXT = new Color(255, 100, 255);
    final static int EMPTY = 0;
    final static int BSIZE = 14; //board size.
    final static int HEXSIZE = 52;    //hex size in pixels
    final static int BORDERS = 15;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; //screen size (vertical dimension).

    int[][] board = new int[BSIZE][BSIZE];
    BasicObject[][] boardSprite = new BasicObject[BSIZE][BSIZE];
    Units kek = new Units();
    BasicObject tempe;
    int click = 1;

    void initGame() {

        hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.

        hexmech.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
        hexmech.setBorders(BORDERS);

        for (int i = 0; i < BSIZE; i++) {
            for (int j = 0; j < BSIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        //set up board here
        for (int i = 0; i < 14; i += 2) {
            Point k = new Point(hexmech.pxtoHex2(i, 0));
            Type1 chok = new Type1("picts/bad1.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
            kek.add(chok);
            int temp = kek.searchIndex(chok);
            boardSprite[i][0] = kek.objects[temp];

        }

        for (int i = 1; i < 14; i += 2) {
            Point k = new Point(hexmech.pxtoHex2(i, 13));
            Type2 chok = new Type2("picts/Minifish.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
            kek.add(chok);
            int temp = kek.searchIndex(chok);
            boardSprite[i][13] = kek.objects[temp];
        }
        Point k = new Point(hexmech.pxtoHex2(8, 7));
        Type1 ele = new Type1("picts/bad1.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
        kek.add(ele);
        int temp = kek.searchIndex(ele);
        boardSprite[8][7] = kek.objects[temp];

    }

    private void createAndShowGUI() {
        DrawingPanel panel = new DrawingPanel();
        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("THE GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.add(panel);
        //this.add(panel);  -- cannot be done in a static context
        //for hexes in the FLAT orientation, the height of a 10x10 grid is 1.1764 * the width. (from h / (s+t))
        frame.setSize((int) (SCRSIZE / 1.23), SCRSIZE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    class DrawingPanel extends JPanel {
        //mouse variables here
        //Point mPt = new Point(0,0);
        //Sprite currentSprite;

        public DrawingPanel() {
            setBackground(COLOURBACK);
            //currentSprite = new Sprite("picts/bad1.png", 4000,4000);
            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);

            //draw grid
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    hexmech.drawHex(i, j, g2);
                }
            }
            //fill in hexes
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    //if (board[i][j] < 0) hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
                    //if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO, board[i][j],g2);
                    hexmech.fillHex(i, j, board[i][j], g2);
                }
            }

            //g.setColor(Color.RED);
            //g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
            //currentSprite.draw(g);
            kek.draw(g);
        }

        class MyMouseListener extends MouseAdapter {
            public void mouseClicked(MouseEvent e) {
                Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
                // System.out.println("p: " + p.x + " " +  p.y);
                Point k = new Point(hexmech.pxtoHex2(p.x, p.y));
                // System.out.println("k: " + k.x + " " +  k.y);
                Point a = new Point(hexmech.pxtoHex2(0, 0));

                if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;

                //DEBUG: colour in the hex which is supposedly the one clicked on
                //clear the whole screen first.
                /* for (int i=0;i<BSIZE;i++) {
                    for (int j=0;j<BSIZE;j++) {
						board[i][j]=EMPTY;
					}
				} */
                if (e.getButton() == 1) {


                    if (click % 2 != 0) {
                        tempe = boardSprite[p.x][p.y];

                        kek.remove(tempe);
                        boardSprite[p.x][p.y] = null;

                    }
                    if (click % 2 == 0) {
                        tempe.update(k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
                        kek.add(tempe);
                        int temp = kek.searchIndex(tempe);
                        boardSprite[p.x][p.y] = kek.objects[temp];
                        tempe = null;

                    }
                    System.out.println(click);
                    click++;

                /*}
                if (e.getButton() == 3) {
                    try {
                        System.out.println(boardSprite[p.x][p.y].toString());
                    } catch (Exception e1) {
                    }
                }*/




                    repaint();
                }
                if (e.getButton() == 3) {
                    offsetDistance(a, p);

                }
            } //end of MyMouseListener class
        } // end of DrawingPanel class

        public int[] offsetToCube(int row, int col){
            int z,x,y;
            x = col - (row - row % 2) /2;
            z = row;
            y = - x - z;
            return new int[] {x, y, z};
        }

        public int cubeDistance(int[] p1, int[] p2){
            int a = Math.abs(p1[0] - p2[0]);
            int b = Math.abs(p1[1] - p2[1]);
            int c = Math.abs(p1[2] - p2[2]);
            return Math.max(a, Math.max(b, c));

        }

        public void offsetDistance(Point one, Point two) {
            int[] ac = offsetToCube(one.x,one.y);
            int[] bc = offsetToCube(two.x, two.y);
            System.out.println(cubeDistance(ac, bc));
        }

// lulbkb



    }
}