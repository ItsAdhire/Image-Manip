package model;

import java.util.HashMap;

/**
 * This class represents a set of operations that can be used to
 * both update and monitor the state of an image model (which consists
 * of a map of String name—Image object key-value paris).
 */
public class SimpleImageModel implements ImageModel {
  private HashMap<String, Image> map;

  /**
   * Creates a new SimpleImageModel object.
   */
  public SimpleImageModel() {
    map = new HashMap<String, Image>();
  }

  @Override
  public Image retrieve(String imageName) {
    if (imageName == null) {
      throw new IllegalArgumentException("Given null inputs");
    }
    return map.get(imageName);

  }

  @Override
  public void add(String imageName, Image image) {
    if (imageName == null || image == null) {
      throw new IllegalArgumentException("Given null inputs");
    }

    map.put(imageName, image);

  }

}
