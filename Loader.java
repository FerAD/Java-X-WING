/*
 * PROGRAMA QUE MODELA UNA FIGURA 3D DE X-WING
 *
 
 *Fernando Arey Duran A00397411
*/

import javax.swing.JFrame;
import javax.swing.JPanel;

class Loader{
      public static void main(String[] args) {

            WireframeJApplet applet = new WireframeJApplet();
            applet.init();
            final JFrame frame = new JFrame("Wireframe Viewer");
            frame.setContentPane(applet.getContentPane());
            frame.setJMenuBar(applet.getJMenuBar());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocation(100, 100);
            frame.setVisible(true);
            applet.start();
      }
}