package model;

/**
 * This interface provides operations for the behavior of an image model
 * when its state is unchanging.
 */
public interface ImageModelState {
  /**
   * Retrieves the image with the given name from this ImageModelState object's
   * map of String name—Image object key-value pairs.
   * @param imageName the String name of the image to be retrieved.
   * @return an Image object (which can be null if the image was not found).
   */
  Image retrieve(String imageName);
}
