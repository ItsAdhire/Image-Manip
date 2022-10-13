package controller;

/**
 * This interface represents a set of operations that allows the view to communicate which
 * operation button was pressed to the controller.
 */
public interface Features {
  /**
   * Tells the controller that the load operation should be executed.
   * @param path a String representing the file path to load from.
   */
  void load(String path);

  /**
   * Tells the controller that the save operation should be executed.
   * @param path a String representing the file path to save to.
   */
  void save(String path);

  /**
   * Tells the controller that the Brighten macro should be executed.
   */
  void brighten();

  /**
   * Tells the controller that the Brighten macro should be executed (to darken the image).
   */
  void darken();

  /**
   * Tells the controller that the HorizontalFlip macro should be executed.
   */
  void flipHor();

  /**
   * Tells the controller that the VerticalFlip macro should be executed.
   */
  void flipVer();

  /**
   * Tells the controller that the RedGreyScale macro should be executed.
   */
  void redGrey();

  /**
   * Tells the controller that the GreenGreyScale macro should be executed.
   */
  void greenGrey();

  /**
   * Tells the controller that the BlueGreyScale macro should be executed.
   */
  void blueGrey();

  /**
   * Tells the controller that the ValueGreyScale macro should be executed.
   */
  void valueGrey();

  /**
   * Tells the controller that the IntensityGreyScale macro should be executed.
   */
  void intensityGrey();

  /**
   * Tells the controller that the LumaGreyScale macro should be executed.
   */
  void lumaGrey();

  /**
   * Tells the controller that the Greyscale macro should be executed.
   */
  void greyscale();

  /**
   * Tells the controller that the Sepia macro should be executed.
   */
  void sepia();

  /**
   * Tells the controller that the Blur macro should be executed.
   */
  void blur();

  /**
   * Tells the controller that the Sharpen macro should be executed.
   */
  void sharpen();
}
