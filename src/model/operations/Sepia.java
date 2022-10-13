package model.operations;

/**
 * This class represents a macro that sepia tones an image using a matrix with specific values.
 */
public class Sepia extends AbstractColorTransform {
  /**
   * Creates a new Sepia object using the given in and out image names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public Sepia(String in, String out) {
    super(in, out);
  }

  /**
   * Creates a new Sepia object using the given in image name.
   * @param in the String name of the input image.
   */
  public Sepia(String in) {
    super(in);
  }

  @Override
  protected double[][] initializeMatrix() {
    return new double[][] {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
  }
}
