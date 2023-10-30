import javax.swing.*;
import java.awt.*;

public class HorizontalThermometer extends JPanel {
    private int temperature;

    public HorizontalThermometer(int initialTemperature) {
        this.temperature = initialTemperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        repaint(); // Redraw the thermometer when the temperature changes
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        Color thermometerColor = (temperature < 27) ? Color.BLUE : Color.RED;

        // Draw the thermometer outline
        g.setColor(Color.BLACK);
        g.fillRect(0, height / 3, width, height / 3);
        g.setColor(thermometerColor); // Use the color based on the temperature

        // Calculate the filled height based on the temperature
        int filledHeight = (int) (height / 3 + (height / 3) * (1 - (temperature / 100.0)));
        g.fillRect(0, filledHeight, width, height - filledHeight);

        // Display the temperature value
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        String tempString = temperature + "°C";
        int strWidth = g.getFontMetrics().stringWidth(tempString);
        g.drawString(tempString, (width - strWidth) / 2, height - 10);
    }
}
