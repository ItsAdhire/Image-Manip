package view;

import java.io.IOException;

/**
 * This interface represents operations that should be offered by
 * a view for an ImageModelState object.
 */
public interface ImageView {
  /**
   * Renders a specific message to the provided data destination.
   * @param message the String message to be transmitted.
   * @throws IOException if transmission of the message to the provided
   *      data destination fails.
   */
  void renderMessage(String message) throws IOException;
}
