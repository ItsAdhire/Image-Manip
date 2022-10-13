package model;

import model.operations.ImageOp;

/**
 * This interface provides operations for the behavior of an image model.
 * An image model can be operated on through objects of the {@link ImageOp}
 * class.
 */
public interface ImageModel extends ImageModelState {
  /**
   * Adds the given image with the given name to this ImageModel object's
   * map of String name—Image object key-value pairs.
   * @param imageName the String name of the image to be added.
   * @param image the Image object to be added.
   */
  void add(String imageName, Image image);
}
