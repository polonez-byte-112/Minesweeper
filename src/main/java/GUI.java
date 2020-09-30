import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Random;

public class GUI extends JFrame {

    public boolean resetter = false;
    public int spacing = 5;
    public int neighs = 0;
    public int mouseX = -100;
    public int mouseY = -100;
    public int messageX = 20;
    public int messageY = -10;
    public int flagX = 375;
    public int flagY = 15;
    public int flagCenterX = flagX + 30;
    public int flagCenterY = flagY + 55;
    public int[] triangleX = {410, 382, 410};
    public int[] triangleY = {flagY + 2, flagY + 25, flagY + 25};
    public boolean flagger = false;
    public int smileyX = 605;
    public int smileyY = 5;
    public int smileyCenterX = smileyX + 40;
    public int smileyCenterY = smileyY + 65;
    public boolean happinees = true;
    public boolean victory = false;
    public boolean defeat = false;
    public int timerX = 1120;
    public int timerY = 5;
    public int secs = 0;
    Date startDate = new Date();
    Random random = new Random();

    int[][] mines = new int[16][9];
    int[][] neighbours = new int[16][9];
    boolean[][] revealed = new boolean[16][9];
    boolean[][] flagged = new boolean[16][9];
    Timer timer = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            messageY = messageY + 1;
        }
    });

    public GUI() {
        this.setTitle("Mineswapper");
        this.setSize(1286, 829);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setFocusable(true);


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(100) < 20) {

                    mines[i][j] = 1;

                } else {
                    mines[i][j] = 0;
                }

                revealed[i][j] = false;
            }
        }


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {

                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true) {
                                neighs++;
                            }
                        }
                    }
                }
                neighbours[i][j] = neighs;
            }

        }

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public void checkVictoryStatus() {


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {

                if (revealed[i][j] == true && mines[i][j] == 1) {
                    defeat = true;
                    happinees = false;

                }
            }
        }

        if (totalBoxesRevealed() == 144 - totalMines()) {
            victory = true;

        }
    }

    public int totalMines() {
        int total = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {

                if (mines[i][j] == 1) {
                    total++;
                }
            }
        }

        return total;
    }

    public int totalBoxesRevealed() {

        int total = 0;

        for (int i = 0; i < 16; i++) {


            for (int j = 0; j < 9; j++) {

                if (revealed[i][j] == true) {
                    total++;
                }
            }
        }
        return total;
    }

    public void resetAll() {

        flagger = false;
        resetter = true;
        timer.stop();
        messageY = -10;
        startDate = new Date();
        happinees = true;
        victory = false;
        defeat = false;


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(100) < 20) {

                    mines[i][j] = 1;

                } else {
                    mines[i][j] = 0;
                }

                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {

                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true) {
                                neighs++;
                            }
                        }
                    }
                }
                neighbours[i][j] = neighs;
            }

        }

        resetter = false;
    }

    public boolean isInFlag() {
        int dif = (int) Math.sqrt(Math.abs(mouseX - flagCenterX) * Math.abs(mouseX - flagCenterX) + Math.abs(mouseY - flagCenterY) * Math.abs(mouseY - flagCenterY));

        if (dif < 25) {

            return true;
        }
        return false;
    }

    public boolean inSmiley() {

        int dif = (int) Math.sqrt(Math.abs(mouseX - smileyCenterX) * Math.abs(mouseX - smileyCenterX) + Math.abs(mouseY - smileyCenterY) * Math.abs(mouseY - smileyCenterY));

        if (dif < 35) {

            return true;
        }

        return false;
    }

    public int inBoxX() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {


                if (mouseX >= spacing + i * 80 + 10 && mouseX < spacing + i * 80 + 80 + 10 - 2 * spacing && mouseY >= spacing + j * 80 + 80 + 26 && mouseY < spacing + j * 80 + 80 + 26 + 80 - 2 * spacing) {

                    return i;
                }


            }
        }


        return -1;
    }

    public int inBoxY() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {


                if (mouseX >= spacing + i * 80 + 10 && mouseX < spacing + i * 80 + 80 + 10 - 2 * spacing && mouseY >= spacing + j * 80 + 80 + 26 && mouseY < spacing + j * 80 + 80 + 26 + 80 - 2 * spacing) {

                    return j;
                }


            }
        }


        return -1;
    }

    public boolean isN(int mX, int mY, int cX, int cY) {

        if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
            return true;
        }


        return false;
    }

    public class Board extends JPanel {

        public void paintComponent(Graphics g) {
        

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1280, 800);


            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.gray);


                    if(flagged[i][j]){
                        g.setColor(new Color(171, 171, 172));
                    }

                    if (revealed[i][j] == true && flagged[i][j] == false) {
                        g.setColor(new Color(171, 171, 172));


                        if (mines[i][j] == 1) {
                            g.setColor(new Color(146, 23, 23));
                        }
                    }


                    if (mouseX >= spacing + i * 80 + 10 && mouseX < spacing + i * 80 + 80 + 10 - 2 * spacing && mouseY >= spacing + j * 80 + 80 + 26 && mouseY < spacing + j * 80 + 80 + 26 + 80 - 2 * spacing) {
                        g.setColor(Color.lightGray);
                    }

                    g.fillRect(spacing + i * 80, spacing + j * 80 + 80, 80 - 2 * spacing, 80 - 2 * spacing);

                    if (revealed[i][j] == true && flagged[i][j] == false) {

                        if (mines[i][j] != 1) {


                            switch (neighbours[i][j]) {

                                case 1:
                                    g.setColor(new Color(54, 101, 120));
                                    break;
                                case 2:
                                    g.setColor(new Color(55, 144, 62));
                                    break;
                                case 3:
                                    g.setColor(new Color(183, 29, 29));
                                    break;
                                case 4:
                                    g.setColor(new Color(82, 7, 64));
                                    break;
                                case 5:
                                    g.setColor(new Color(204, 101, 101));
                                    break;
                                case 6:
                                    g.setColor(new Color(50, 50, 102));
                                    break;
                                case 7:
                                    g.setColor(new Color(125, 169, 0));
                                    break;
                                case 8:
                                    g.setColor(Color.darkGray);
                                    break;


                            }

                            g.setFont(new Font("Tahoma", Font.BOLD, 40));
                            g.drawString(Integer.toString(neighbours[i][j]), i * 80 + 27, j * 80 + 80 + 55);
                        } else {
                            g.setColor(new Color(39, 41, 41));
                            g.fillOval(i * 80 + 19, j * 80 + 100, 40, 40);
                            //    g.drawImage(image, 40,40,null);

                            //dodac tutaj draw image

                        }
                    }

                    //flags painting

                    if (flagged[i][j] == true) {


                        int[] universalTriangleX = {i * 80 + 25 + 15, i * 80 + 15, i * 80 + 25 + 15};
                        int[] universalTriangleY = {j * 80 + 80 + 10, j * 80 + 25 + 80 + 10, j * 80 + 25 + 80 + 10};


                        g.setColor(Color.black);

                        g.drawLine(i * 80 + 45, j * 80 + 90, i * 80 + 45, j * 80 + 60 + 90);
                        g.drawLine(i * 80 + 46, j * 80 + 90, i * 80 + 46, j * 80 + 60 + 90);

                        g.setColor(Color.red);
                        g.fillPolygon(universalTriangleX, universalTriangleY, 3);

                        g.setColor(new Color(255, 215, 50));
                        g.fillRoundRect(i * 80 + 10, j * 80 + 140, 60, 15, 10, 10);

                    }
                }
            }


            //Smiley

            g.setColor(Color.yellow);
            g.fillOval(smileyX, smileyY, 70, 70);
            g.setColor(Color.black);
            g.fillOval(smileyX + 20, smileyY + 15, 5, 5);
            g.fillOval(smileyX + 45, smileyY + 15, 5, 5);


            if (happinees != false) {

                g.drawArc(smileyX + 20, smileyY + 30, 30, 20, 0, -180);
            } else {
                g.drawArc(smileyX + 20, smileyY + 40, 30, 20, 0, 180);
            }


            //Time counter painting


            g.setColor(new Color(102, 102, 102));

            g.fillRect(timerX - 65 - 2 * spacing, timerY + 2, 210, 70);

            if (defeat == false && victory == false) {
                secs = (int) (new Date().getTime() - startDate.getTime()) / 1000;
                g.setColor(new Color(241, 239, 94));

            }
            if (secs > 9999) {
                secs = 9999;
            }

            if (messageY >= 50) {
                messageY = 50;
            }

            if (victory == true) {
                g.setColor(new Color(78, 188, 120));
                timer.start();
            }

            if (defeat == true) {
                g.setColor(new Color(146, 23, 23));
                timer.start();
            }


            g.setFont(new Font("Tahoma", Font.BOLD, 80));
            if (secs < 10) {
                g.drawString("000" + Integer.toString(secs), timerX - 65 - 2 * spacing, timerY + 2 + 65);

            } else if (secs < 100) {
                g.drawString("00" + Integer.toString(secs), timerX - 65 - 2 * spacing, timerY + 2 + 65);

            } else if (secs < 1000) {
                g.drawString("0" + Integer.toString(secs), timerX - 65 - 2 * spacing, timerY + 2 + 65);
            } else if (secs < 10000) {
                g.drawString(Integer.toString(secs), timerX - 65 - 2 * spacing, timerY + 2 + 65);
            } else {
            }


            // lose/win message


            if (victory == true) {
                g.setColor(new Color(77, 255, 99));
                g.setFont(new Font("Tahoma", Font.BOLD, 40));
                g.drawString("You Win!", messageX, messageY);
            }

            if (defeat == true) {
                g.setColor(new Color(255, 99, 77));
                g.setFont(new Font("Tahoma", Font.BOLD, 40));
                g.drawString("You Lost!", messageX, messageY);
            }


            g.setColor(Color.black);
            g.fillOval(374, 14, 52, 52);
            if (flagger == false) {
                g.setColor(Color.gray);
            } else if (flagger == true) {
                g.setColor(Color.orange);
            }
            g.fillOval(flagX, flagY, 50, 50); //głowne kolko

            g.setColor(Color.black);
            g.drawLine(flagX + 34, flagY + 47, flagX + 34, flagY + 4);
            g.drawLine(flagX + 35, flagY + 47, flagX + 35, flagY + 4);
            g.drawLine(flagX + 36, flagY + 47, flagX + 36, flagY + 4);

            g.setColor(Color.red);
            g.fillPolygon(triangleX, triangleY, 3);


        }

    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();


        }
    }

    public class Click implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();


            if (inSmiley() == true) {
                resetAll();

            }

            if (isInFlag() == true) {
                if (flagger == false) {
                    flagger = true;
                } else {
                    flagger = false;
                }
            }


            if (inBoxX() != -1 && inBoxY() != -1) {
                if (flagger == true && revealed[inBoxX()][inBoxY()] == false) {
                    if (flagged[inBoxX()][inBoxY()] == false) {
                        flagged[inBoxX()][inBoxY()] = true;
                    } else {
                        flagged[inBoxX()][inBoxY()] = false;
                    }
                } else {
                    revealed[inBoxX()][inBoxY()] = true;
                }

            }


        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
