import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Temperature Converter");

        // Create Celsius and Fahrenheit sliders
        JSlider celsiusSlider = createSlider(-30, 200, JSlider.VERTICAL);
        JSlider fahrenheitSlider = createSlider(-22, 392, JSlider.VERTICAL);

        // Create Celsius and Fahrenheit text fields
        JTextField celsiusField = createColoredTextField("0.0", Color.BLUE);
        JTextField fahrenheitField = createColoredTextField("32.0", Color.BLUE);

        // Create conversion buttons
        JButton celsiusToFahrenheitButton = new JButton("Convert to Fahrenheit");
        JButton fahrenheitToCelsiusButton = new JButton("Convert to Celsius");

        // Set the preferred size for the text fields
        celsiusField.setPreferredSize(new Dimension(100, 30));
        fahrenheitField.setPreferredSize(new Dimension(100, 30));

        // Create a panel for the Celsius slider with a label
        JPanel celsiusPanel = createSliderPanel(celsiusSlider, "Celsius");

        // Create a panel for the Fahrenheit slider with a label
        JPanel fahrenheitPanel = createSliderPanel(fahrenheitSlider, "Fahrenheit");

        // Create the HorizontalThermometer with a larger preferred size
        HorizontalThermometer thermometer = new HorizontalThermometer(25);
        thermometer.setPreferredSize(new Dimension(100, 300)); // Set preferred size to 200x100

        // Create a panel for the sliders and another for the text fields and buttons
        JPanel sliderPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        sliderPanel.setLayout(new GridBagLayout());
        inputPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(2, 1)); 

        // Position the components in the middle of the slider panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        sliderPanel.add(celsiusPanel, gbc);

        gbc.gridx = 1;
        sliderPanel.add(thermometer, gbc);

        gbc.gridx = 2;
        sliderPanel.add(fahrenheitPanel, gbc);

        // Position the text fields in the middle of the input panel
        buttonPanel.add(celsiusField);
        buttonPanel.add(fahrenheitField);

        // Add buttons to the button panel
        buttonPanel.add(celsiusToFahrenheitButton);
        buttonPanel.add(fahrenheitToCelsiusButton);
        
      

        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(sliderPanel, BorderLayout.CENTER);
        contentPanel.add(inputPanel, BorderLayout.SOUTH);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Adjust frame size accordingly
        frame.setVisible(true);

        // Add a change listener to the Celsius slider
        celsiusSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double celsiusValue = celsiusSlider.getValue();
                double fahrenheitValue = celsiusToFahrenheit(celsiusValue);
                updateColoredTextField(celsiusField, celsiusValue);
                updateColoredTextField(fahrenheitField, fahrenheitValue);
                fahrenheitSlider.setValue((int) fahrenheitValue);
                thermometer.setTemperature((int) celsiusValue);
            }
        });

        // Add a change listener to the Fahrenheit slider
        fahrenheitSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double fahrenheitValue = fahrenheitSlider.getValue();
                double celsiusValue = fahrenheitToCelsius(fahrenheitValue);
                updateColoredTextField(celsiusField, celsiusValue);
                updateColoredTextField(fahrenheitField, fahrenheitValue);
                celsiusSlider.setValue((int) celsiusValue);
                thermometer.setTemperature((int) celsiusValue);
            }
        });

        // Add an action listener to the "Celsius to Fahrenheit" button
        celsiusToFahrenheitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double celsiusValue = Double.parseDouble(celsiusField.getText());

                    // Check if the input is within the valid Celsius temperature range
                    if (celsiusValue < -30 || celsiusValue > 200) {
                        JOptionPane.showMessageDialog(frame, "Celsius temperature must be between -30 and 200 degrees.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return; // Stop further processing
                    }

                    double fahrenheitValue = celsiusToFahrenheit(celsiusValue);
                    fahrenheitField.setText(String.valueOf(fahrenheitValue));

                    // Update the Fahrenheit slider
                    fahrenheitSlider.setValue((int) fahrenheitValue);

                    // Update the HorizontalThermometer
                    thermometer.setTemperature((int) celsiusValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add an action listener to the "Fahrenheit to Celsius" button
        fahrenheitToCelsiusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double fahrenheitValue = Double.parseDouble(fahrenheitField.getText());

                    // Check if the input is within the valid Fahrenheit temperature range
                    if (fahrenheitValue < -22 || fahrenheitValue > 392) {
                        JOptionPane.showMessageDialog(frame, "Fahrenheit temperature must be between -22 and 392 degrees.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return; // Stop further processing
                    }

                    double celsiusValue = fahrenheitToCelsius(fahrenheitValue);
                    celsiusField.setText(String.valueOf(celsiusValue));

                    // Update the Celsius slider
                    celsiusSlider.setValue((int) celsiusValue);

                    // Update the HorizontalThermometer
                    thermometer.setTemperature((int) celsiusValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private static JSlider createSlider(int min, int max, int orientation) {
        JSlider slider = new JSlider(orientation, min, max, min);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private static JPanel createSliderPanel(JSlider slider, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel sliderLabel = new JLabel(label);
        sliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(sliderLabel, BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    private static JTextField createColoredTextField(String text, Color color) {
        JTextField textField = new JTextField(text);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(color);
        return textField;
    }

    private static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    private static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    private static void updateColoredTextField(JTextField textField, double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textField.setText(decimalFormat.format(value));
        Color textColor = (value <= 0) ? Color.BLUE : (value >= 27) ? Color.RED : Color.BLUE;
        textField.setForeground(textColor);
    }
}
