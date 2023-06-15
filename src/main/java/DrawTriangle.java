import javax.swing.*;
import java.awt.*;

public class DrawTriangle extends JPanel {
    private boolean cleared = false;

    protected void paintComponent(Graphics g) {
        if (cleared){
            return;
        }
        int [] x = {250,450,0};
        int [] y = {0,250,250};
        g.drawPolygon(x, y, 3);

    }

    public void cleanTriangle(){
        cleared = true;
        Graphics2D g = (Graphics2D) getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }
}
