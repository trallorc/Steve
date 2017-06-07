import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Map();
            }
        });
    }

    //constants and global variables
    final static Color COLOURBACK = Color.WHITE;
    final static Color COLOURCELL = Color.ORANGE;
    final static Color COLOURGRID = Color.BLACK;
    final static Color COLOURONE = Color.GREEN;
    final static Color COLOURONETXT = Color.GREEN;
    final static Color COLOURTWO = Color.PINK;
    final static Color COLOURTWOTXT = Color.PINK;
    final static int EMPTY = 0;
    final static int BSIZE = 14; //board size.
    final static int HEXSIZE = 52;    //hex size in pixels
    final static int BORDERS = 15;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; //screen size (vertical dimension).
    DrawingPanel me;
    JFrame frame;
    int[][] board = new int[BSIZE][BSIZE];
    BasicObject[][] boardSprite = new BasicObject[BSIZE][BSIZE];
    Units kek1 = new Units();
    Units kek2 = new Units();
    BasicObject temp1, temp2;
    int unitSelected = 1;
    boolean check = true;
    boolean attackTurn = false;
    boolean wait=false;
    Point last;
    int id;
    boolean turn;
    Client client;
    boolean over=false;


    private Map() {
        initGame();
        createAndShowGUI();

    }

    void initGame() {

        hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.

        hexmech.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
        hexmech.setBorders(BORDERS);

        for (int i = 0; i < BSIZE; i++) {
            for (int j = 0; j < BSIZE; j++) {
                board[i][j] = EMPTY;
            }
        }


       /* for (int i = 0; i < 14; i += 2) {
            Point k = new Point(hexmech.pxtoHex2(i, 0));
            Type1 chok = new Type1("picts/bad1.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2,1);
            kek1.add(chok);
            int temp = kek1.searchIndex(chok);
            boardSprite[i][0] = kek1.objects[temp];

        }


        for (int i = 1; i < 14; i += 2) {
            Point k = new Point(hexmech.pxtoHex2(i, 13));
            Type2 chok = new Type2("picts/Minifish.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2,2);
            kek2.add(chok);
            int temp = kek2.searchIndex(chok);
            boardSprite[i][13] = kek2.objects[temp];
        }*/
        Point k = new Point(hexmech.pxtoHex2(8, 7));
        Type1 ele = new Type1("picts/bad1.png", k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2,1);
        kek1.add(ele);
        int temp = kek1.searchIndex(ele);
        boardSprite[8][7] = kek1.objects[temp];

        Point c = new Point(hexmech.pxtoHex2(9, 5));
        Type3 oren = new Type3("picts/gachi.png", c.x + HEXSIZE / 2 + 3, c.y + HEXSIZE / 2,2);
        kek2.add(oren);
        int temp1 = kek2.searchIndex(oren);
        boardSprite[9][5] = kek2.objects[temp1];

    }

    private void createAndShowGUI() {
        DrawingPanel panel = new DrawingPanel();
        //JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("THE GAME");
        client = new Client(this);
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

        public DrawingPanel() {
            setBackground(COLOURBACK);
            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
            me=this;
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
            kek1.draw(g);
            kek2.draw(g);
        }

        class MyMouseListener extends MouseAdapter {
            public void mouseClicked(MouseEvent e) {
                if(!turn )
                    return;
                Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
                //System.out.println("p: " + p.x + " " +  p.y);
                Point k = new Point(hexmech.pxtoHex2(p.x, p.y));
                //System.out.println("k: " + k.x + " " +  k.y);

                if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;

                //System.out.println("First:" + p + "Last:" + last);

                if (e.getButton() == 1) {
                    //System.out.println(unitSelected);
                    if (attackTurn) {
                        check = false;
                        if (board[p.x][p.y] == 1) {
                            if (boardSprite[p.x][p.y] != null) {

                                attack(last, p);
                                clearBoard();
                                attackTurn = false;
                                wait=true;
                                turn = !turn;
                            } else
                                System.out.println("Must attack target");
                        } else
                            System.out.println("Target outside of range");
                    }

                    if (!attackTurn && !wait) {
                        if (unitSelected % 2 != 0) {
                            try {
                                showSpeed(p);
                                temp1 = boardSprite[p.x][p.y];
                                try {

                                    kek1.remove(temp1);
                                }
                                catch (Exception e1){};
                                try {

                                    kek2.remove(temp1);
                                }
                                catch (Exception e1){};
                                boardSprite[p.x][p.y] = null;
                                check = true;

                            } catch (Exception e1) {
                                System.out.println("No unit selected");
                                check = false;
                            }
                        } else {
                            check = false;
                        }


                        if (unitSelected % 2 == 0) {
                            if (boardSprite[p.x][p.y] == null) {
                                if (board[p.x][p.y] == -1) {
                                    temp1.update(k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
                                    try {

                                        kek1.add(temp1);
                                        int temp = kek1.searchIndex(temp1);
                                        boardSprite[p.x][p.y] = kek1.objects[temp];

                                    }
                                    catch (Exception e1){};
                                    try {

                                        kek2.add(temp1);
                                        int temp = kek2.searchIndex(temp1);
                                        boardSprite[p.x][p.y] = kek2.objects[temp];

                                    }
                                    catch (Exception e1){};

                                    temp1 = null;
                                    check = true;
                                    clearBoard();
                                    if (checkRange(p)) {
                                        attackTurn = true;
                                        showRange(p);
                                    }
                                    else
                                        turn=!turn;





                                } else {
                                    System.out.println("Location unreachable");
                                    check = false;
                                }
                            } else {
                                System.out.println("Location is occupied");
                                check = false;
                            }
                        }
                    }
                    if (check)
                        unitSelected++;
                    wait=false;
                    //System.out.println(check);
                    //System.out.println(attackTurn);



                   /* if (click % 2 != 0) {
                        temp1 = boardSprite[p.x][p.y];

                        kek.remove(temp1);
                        boardSprite[p.x][p.y] = null;

                    }
                    if (click % 2 == 0) {
                        temp1.update(k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
                        kek.add(temp1);
                        int temp = kek.searchIndex(temp1);
                        boardSprite[p.x][p.y] = kek.objects[temp];
                        temp1 = null;

                    }
                    System.out.println(click);
                    click++;*/


                    repaint();
                }


                if (e.getButton() == 3) {
                    try {
                        System.out.println(boardSprite[p.x][p.y].toString());
                    } catch (Exception e1) {
                        System.out.println("No unit selected");
                    }
                }
                /*if (e.getButton() == 3) {
                    //System.out.println(offsetDistance(a, p));
                    showRange(p);

                }*/
                last = p;
                //over=end();
                client.send("turn##"+id+"##"+e.getX()+"##"+e.getY()+"##"+e.getButton());
               /* if(over)
                    client.send("end##");*/

            }

        }

        public int[] offsetToCube(int row, int col) {
            int z, x, y;
            x = col - (row - row % 2) / 2;
            z = row;
            y = -x - z;
            return new int[]{x, y, z};
        }

        public int cubeDistance(int[] p1, int[] p2) {
            int a = Math.abs(p1[0] - p2[0]);
            int b = Math.abs(p1[1] - p2[1]);
            int c = Math.abs(p1[2] - p2[2]);
            return Math.max(a, Math.max(b, c));

        }

        public int offsetDistance(Point one, Point two) {
            int[] ac = offsetToCube(one.x, one.y);
            int[] bc = offsetToCube(two.x, two.y);
            return (cubeDistance(ac, bc));
        }

        public void showSpeed(Point one) {
            int distance;
            Point check;
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    check = new Point(i, j);
                    distance = offsetDistance(check, one);
                    if (distance <= boardSprite[one.x][one.y].getSpeed()) {
                        board[i][j] = -1;
                    }
                }
            }
            repaint();
        }

        public void showRange(Point one) {
            int distance;
            Point check;
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    check = new Point(i, j);
                    distance = offsetDistance(check, one);
                    if (distance <= boardSprite[one.x][one.y].getRange())
                        if(one.x != check.x || one.y != check.y)
                            board[i][j] = 1;

                }
            }
            repaint();
        }

        public boolean checkRange(Point one) {
            Point check;
            int distance;
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    check = new Point(i, j);
                    distance = offsetDistance(check, one);
                    if (!((i == one.x) && (j == one.y)))
                        if (distance <= boardSprite[one.x][one.y].getRange())
                            if (boardSprite[check.x][check.y] != null)
                                return true;
                }
            }
            return false;
        }

        public void clearBoard() {
            for (int i = 0; i < BSIZE; i++) {
                for (int j = 0; j < BSIZE; j++) {
                    board[i][j] = 0;
                }
            }
        }

        public void attack(Point p, Point t) {
            int damage = 0, health = 0;

            if (boardSprite[p.x][p.y] instanceof Type1)
                damage = ((Type1) boardSprite[p.x][p.y]).getDamage();
            if (boardSprite[p.x][p.y] instanceof Type2)
                damage = ((Type2) boardSprite[p.x][p.y]).getDamage();
            if (boardSprite[p.x][p.y] instanceof Type3)
                damage = ((Type3) boardSprite[p.x][p.y]).getDamage();
            if (boardSprite[t.x][t.y] instanceof Type1)
                health = ((Type1) boardSprite[t.x][t.y]).getHealth();
            if (boardSprite[t.x][t.y] instanceof Type2)
                health = ((Type2) boardSprite[t.x][t.y]).getHealth();
            if (boardSprite[t.x][t.y] instanceof Type3)
                health = ((Type3) boardSprite[t.x][t.y]).getHealth();
            System.out.println("Damage inflicted:" + damage + ", Health before:" + health);
            health -= damage;
            boardSprite[t.x][t.y].setHealth(health);
            System.out.println("Health remaining:" + boardSprite[t.x][t.y].getHealth());
            if (boardSprite[t.x][t.y].getHealth() <= 0) {
                try {
                    kek1.remove(boardSprite[t.x][t.y]);
                }
                catch(Exception e1){};
                try {
                    kek2.remove(boardSprite[t.x][t.y]);
                }
                catch(Exception e1){};
                boardSprite[t.x][t.y] = null;
            }

        }

    public void update(int x, int y, int button) {
        {
            Point p = new Point(hexmech.pxtoHex(x, y));
            //System.out.println("p: " + p.x + " " +  p.y);
            Point k = new Point(hexmech.pxtoHex2(p.x, p.y));
            //System.out.println("k: " + k.x + " " +  k.y);

            if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;

            //System.out.println("First:" + p + "Last:" + last);

            if (button == 1) {
                //System.out.println(unitSelected);
                if (attackTurn) {
                    check = false;
                    if (board[p.x][p.y] == 1) {
                        if (boardSprite[p.x][p.y] != null) {

                            attack(last, p);
                            clearBoard();
                            attackTurn = false;
                            wait = true;
                            turn = !turn;
                        } else
                            System.out.println("Must attack target");
                    } else
                        System.out.println("Target outside of range");
                }

                if (!attackTurn && !wait) {
                    if (unitSelected % 2 != 0) {
                        try {
                            showSpeed(p);
                            temp1 = boardSprite[p.x][p.y];
                            try {

                                kek1.remove(temp1);

                            }
                            catch (Exception e1){};
                            try {

                                kek2.remove(temp1);

                            }
                            catch (Exception e1){};
                            boardSprite[p.x][p.y] = null;
                            check = true;

                        } catch (Exception e1) {
                            System.out.println("No unit selected");
                            check = false;
                        }
                    } else {
                        check = false;
                    }


                    if (unitSelected % 2 == 0) {
                        if (boardSprite[p.x][p.y] == null) {
                            if (board[p.x][p.y] == -1) {
                                temp1.update(k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
                                try {

                                    kek1.add(temp1);
                                    int temp = kek1.searchIndex(temp1);
                                    boardSprite[p.x][p.y] = kek1.objects[temp];

                                }
                                catch (Exception e1){};
                                try {

                                    kek2.add(temp1);
                                    int temp = kek2.searchIndex(temp1);
                                    boardSprite[p.x][p.y] = kek2.objects[temp];

                                }
                                catch (Exception e1){};

                                temp1 = null;
                                check = true;
                                clearBoard();
                                if (checkRange(p)) {
                                    attackTurn = true;
                                    showRange(p);
                                }
                                else
                                    turn=!turn;





                            } else {
                                System.out.println("Location unreachable");
                                check = false;
                            }
                        } else {
                            System.out.println("Location is occupied");
                            check = false;
                        }
                    }
                }
                if (check)
                    unitSelected++;
                wait = false;
                //System.out.println(check);
                //System.out.println(attackTurn);



                   /* if (click % 2 != 0) {
                        temp1 = boardSprite[p.x][p.y];

                        kek.remove(temp1);
                        boardSprite[p.x][p.y] = null;

                    }
                    if (click % 2 == 0) {
                        temp1.update(k.x + HEXSIZE / 2 + 3, k.y + HEXSIZE / 2);
                        kek.add(temp1);
                        int temp = kek.searchIndex(temp1);
                        boardSprite[p.x][p.y] = kek.objects[temp];
                        temp1 = null;

                    }
                    System.out.println(click);
                    click++;*/


                repaint();
            }


            if (button == 3) {
                try {
                    System.out.println(boardSprite[p.x][p.y].toString());
                } catch (Exception e1) {
                    System.out.println("No unit selected");
                }
            }
                /*if (e.getButton() == 3) {
                    //System.out.println(offsetDistance(a, p));
                    showRange(p);

                }*/
            last = p;
            /*over=end();
            if(over)
                client.send("end##");*/

        }
    }
   /* public boolean end(){
        if(kek1.num==0){
            System.out.println("Player 2 wins");
            return true;
        }
        if(kek2.num==0){
            System.out.println("Player 1 wins");
            return true;
        }
        return false;
    }*/
    }
}