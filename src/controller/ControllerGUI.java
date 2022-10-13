package controller;

import model.operations.ImageOp;
import view.ImageGUIView;

/**
 * This interface represents the operations provided by the image model controller (specifically
 * relating to the GUI).
 */
public interface ControllerGUI {
  /**
   * Sets this ControllerGUI object's view to the given view and adds a Features object to it.
   * @param view an ImageGUIView object.
   */
  void setView(ImageGUIView view);

  /**
   * Performs the given macro on an image model.
   * @param op an ImageOp object.
   */
  void operate(ImageOp op);

  /**
   * Attempts to load this file from the given file path.
   * If there is an invalid file path given or if the type of file path
   * cannot be accommodated, displays a message stating so. Throws an
   * IllegalStateException if the message could not be rendered. Throws a
   * NoSuchElementException if the scanner could not read the inputs or
   * ran out of inputs to read.
   * @param filePath a String representing the path to load an image from.
   */
  void load(String filePath);

  /**
   * Attempts to save this file under the given name
   * based on the type of file path. If there is an invalid file path given
   * or if the type of file path cannot be accommodated, displays a message
   * stating so. Throws an IllegalStateException if the message could
   * not be rendered. Throws a NoSuchElementException if the scanner could
   * not read the inputs or ran out of inputs to read.
   * @param filePath a String representing the path to save an image to.
   */
  void save(String filePath);
}

