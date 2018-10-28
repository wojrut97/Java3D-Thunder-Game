import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Text extends JPanel{
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0 ,0, getWidth(), getHeight());

        //Font containing chess figures
        g.setFont(new Font("Arial", Font.PLAIN, 100));

        g.setColor(Color.BLACK);
        g.drawString("chujjj", 10, 100);
    }
}
