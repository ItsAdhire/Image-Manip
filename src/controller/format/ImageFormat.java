package controller.format;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Image;

/**
 * This interface represents a set of operations to load and save
 * an image using file paths. Another operation is allowing the user
 * to retrieve the type of file path.
 */
public interface ImageFormat {

  /**
   * Attempts to load an image from a particular format.
   * @param file the path of the file (given as a String).
   * @return an Image object representing the contents of the file.
   * @throws FileNotFoundException if the file path does not exist.
   * @throws IllegalArgumentException if the file is not of the correct format.
   */
  Image load(String file) throws IllegalArgumentException;

  /**
   * Saves the given image to the given path.
   * If the path already exists, it will be overwritten.
   * @param file the path of the file (given as a String).
   * @param image the Image object to be saved in the file.
   * @throws IOException if the given file path is an invalid path.
   */
  void save(String file, Image image) throws IOException;

}
