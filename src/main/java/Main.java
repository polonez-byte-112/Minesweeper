public class Main implements Runnable {


    GUI gui = new GUI();


    public static void main(String[] args){


        new Thread(new Main()).start();

    }

    public void run() {
while (true){

    gui.repaint();

    if(gui.resetter==false){
    gui.checkVictoryStatus();
    }
}
    }
}
