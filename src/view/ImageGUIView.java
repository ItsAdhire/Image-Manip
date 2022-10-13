package view;

import java.awt.*;

import controller.Features;

/**
 * This interface represents operations that are offered by a view (specifically for the
 * GUI display) for an ImageModelState object.
 */
public interface ImageGUIView extends ImageView {
  /**
   * Adds an ActionListener object (which connects a Features object to the
   * specific operation method it should call) for each of the JButton objects
   * in the list of buttons.
   * @param features a Features object.
   */
  void addFeatures(Features features);

  void renderImage(Image image, int[][] frequency);
}
