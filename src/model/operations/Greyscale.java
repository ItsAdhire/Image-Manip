package model.operations;

/**
 * This class represents a macro that grey scales an image using a matrix with luma values.
 */
public class Greyscale extends AbstractColorTransform {
  /**
   * Creates a new Greyscale object using the given in and out image names.
   * @param in the String input image name.
   * @param out the String output image name.
   */
  public Greyscale(String in, String out) {
    super(in, out);
  }

  /**
   * Creates a new Greyscale object using the given in image name.
   * @param in the String input image name.
   */
  public Greyscale(String in) {
    super(in);
  }

  @Override
  protected double[][] initializeMatrix() {
    return new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
  }
}
