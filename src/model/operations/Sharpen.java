package model.operations;

/**
 * This class represents a macro that sharpens an image.
 */
public class Sharpen extends AbstractFilter {
  /**
   * Creates a new Sharpen object using the given in and out image names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public Sharpen(String in, String out) {
    super(in, out);
    this.matrix = this.initializeMatrix();
  }

  /**
   * Creates a new Sharpen object using the given in image name.
   * @param in the String name of the input image.
   */
  public Sharpen(String in) {
    this(in, in);
  }

  @Override
  protected double[][] initializeMatrix() {
    return new double[][] {
            {- 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0},
            {- 1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, - 1.0 / 8.0},
            {- 1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, - 1.0 / 8.0},
            {- 1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, - 1.0 / 8.0},
            {- 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0, - 1.0 / 8.0}
    };
  }
}
