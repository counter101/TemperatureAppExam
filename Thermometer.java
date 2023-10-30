// Thermometer.java
import javax.swing.JSlider;

class Thermometer extends JSlider {
    private String scaleName;
    private int min;
    private int max;

    public Thermometer(String scaleName, int min, int max) {
        super(min, max, min);
        this.scaleName = scaleName;
        this.min = min;
        this.max = max;
    }

   
}