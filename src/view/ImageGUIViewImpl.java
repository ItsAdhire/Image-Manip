package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * This class represents a set of operations that can be used to display
 * a GUI with various features (buttons, images, and graphs).
 */
public class ImageGUIViewImpl extends JFrame implements ImageGUIView {
  private List<JButton> buttons;
  private List<Consumer<Features>> actions;
  private JLabel imageLabel;
  private int[][] frequency;

  /**
   * Creates a new ImageGUIViewImpl object. Creates and adds the JButton objects
   * for all the operations that are necessary to the list of buttons. Creates
   * and adds the Consumer objects for all the operations that are necessary to
   * the list of actions. Arranges the features on the GUI in a visually appealing
   * way. Labels the different features based on their functions.
   */
  public ImageGUIViewImpl() {
    this.frequency = new int[4][256];
    buttons = new ArrayList<>();
    actions = new ArrayList<>();
    setTitle("ImageManip");

    this.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

    initButtons();
    buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    for (int i = 0; i < buttons.size(); i++) {
      buttonPanel.add(buttons.get(i));
    }

    this.add(buttonPanel, BorderLayout.WEST);

    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(600, 300));

    imagePanel.add(imageScrollPane);

    
    this.add(imagePanel, BorderLayout.CENTER);

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histograms"));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.PAGE_AXIS));

    JPanel[] smallHistPanels = new JPanel[4];
    JPanel[] drawingPanels = new JPanel[4];

    for (int i = 0; i < 4; i ++) {
      smallHistPanels[i] = new JPanel();
      smallHistPanels[i].setLayout(new BorderLayout());

      int finalI = i;
      drawingPanels[i] = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          normalize(frequency, this.getHeight() - 20);
          int width = 2;
          Color[] colors = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.GRAY};
          g.setColor(colors[finalI]);
            for (int j = 0; j < frequency[finalI].length; j++) {
              g.fillRect(j * width, getHeight() - frequency[finalI][j],
                      width, frequency[finalI][j] );
            }
          }
        };
      drawingPanels[i].setPreferredSize(new Dimension(530, 150));


      JLabel yAxis = new JLabel("Frequency");
      smallHistPanels[i].add(yAxis, BorderLayout.WEST);
      smallHistPanels[i].add(drawingPanels[i], BorderLayout.CENTER);

      histogramPanel.add(smallHistPanels[i]);
    }

    this.add(histogramPanel, BorderLayout.EAST);

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  // Adds the JButton objects for each of the operations that are provided by the image model.
  // Each button has a name, the name of its operation, and lambda object that represents the
  // action to be executed by this button.
  private void initButtons() {
    addButton("load", "load image", f -> f.load(getPath(true)));
    addButton("save", "save image", f -> f.save(getPath(false)));
    addButton("brighten", "brighten image", f -> f.brighten());
    addButton("darken", "darken image", f -> f.darken());
    addButton("flip horizontally", "hflip image", f -> f.flipHor());
    addButton("flip vertically", "vflip image", f -> f.flipVer());
    addButton("red component", "red image", f -> f.redGrey());
    addButton("green component", "green image", f -> f.greenGrey());
    addButton("blue component", "blue image", f -> f.blueGrey());
    addButton("value component", "value image", f -> f.valueGrey());
    addButton("intensity component", "inten image", f -> f.intensityGrey());
    addButton("luma component", "luma image", f -> f.lumaGrey());
    addButton("greyscale", "greyscale image", f -> f.greyscale());
    addButton("sepia", "sepia image", f -> f.sepia());
    addButton("blur", "blur image", f -> f.blur());
    addButton("sharpen", "sharpen image", f -> f.sharpen());
  }

  // Returns the file path (of the image) to be loaded from if shouldLoad is true.
  // Otherwise, returns the file path (of the image) to be saved to if shouldLoad is false.
  private String getPath(boolean shouldLoad) {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG, BMP, PPM Images", "jpg", "jpeg", "png", "bmp", "ppm");
    fileChooser.setFileFilter(filter);

    int statusCode = 0;
    if (shouldLoad) {
      statusCode = fileChooser.showOpenDialog(null);
    }
    else {
      statusCode = fileChooser.showSaveDialog(null);
    }

    if (statusCode == JFileChooser.CANCEL_OPTION) {
      return null;
    }
    else if (statusCode == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  // Adds a JButton object to this ImageGUIViewImpl's list of buttons. Creates
  // this button using the given name, name of the action that should be associated with
  // this button, and a lambda object that represents the action to be executed by
  // this button.
  private void addButton(String name, String actionName, Consumer<Features> func) {
    JButton button = new JButton(name);
    button.setActionCommand(actionName);
    buttons.add(button);
    actions.add(func);
  }

  @Override
  public void addFeatures(Features features) {
    for (int i = 0; i < buttons.size(); i++) {
      Consumer<Features> request = actions.get(i);
      buttons.get(i).addActionListener(evt -> request.accept(features));
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(null, message, "An error has occurred",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void renderImage(Image image, int[][] frequency) {
    if (frequency.length != 4 || frequency[0].length != 256) {
      throw new IllegalArgumentException("Invalid Frequency Lists given!");
    }
    this.frequency = frequency;
    imageLabel.setIcon(new ImageIcon(image));
    this.repaint();

  }
  private void normalize(int[][] freq, int max) {

    for (int i = 0; i < freq.length; i++) {
      int minTotal = Integer.MAX_VALUE;
      int maxTotal = 1;

      for (int j = 0; j < freq[0].length; j++) {
        minTotal = Math.min(minTotal, freq[i][j]);
        maxTotal = Math.max(maxTotal, freq[i][j]);
      }

      for (int j = 0; j < freq[0].length; j++) {
        freq[i][j] = (int) (((freq[i][j] - minTotal) / (double) (maxTotal - minTotal)) * max);
      }
    }
  }

}
