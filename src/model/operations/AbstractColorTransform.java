package model.operations;

/**
 * Represents an abstraction for macros that perform a color transform on every pixel
 * of a particular image in the model.
 */
public abstract class AbstractColorTransform extends AbstractUnitImageOp {
  // INVARIANT: matrix is always a 3x3 array
  protected double[][] matrix;

  /**
   * Creates a new AbstractColorTransform object using the given in and out
   * image names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public AbstractColorTransform(String in, String out) {
    super(in, out);
    this.matrix = this.initializeMatrix();
  }

  /**
   * Creates a new AbstractColorTransform object using the given in
   * image name.
   * @param in the String name of the input image.
   */
  public AbstractColorTransform(String in) {
    this(in, in);
  }

  @Override
  protected int[] modifyPixel(int[] color) {
    int[] transformedColor = new int[color.length];
    transformedColor[0] = (int) Math.round(this.matrix[0][0] * color[0]
            + this.matrix[0][1] * color[1]
            + this.matrix[0][2] * color[2]);
    transformedColor[1] = (int) Math.round(this.matrix[1][0] * color[0]
            + this.matrix[1][1] * color[1]
            + this.matrix[1][2] * color[2]);
    transformedColor[2] = (int) Math.round(this.matrix[2][0] * color[0]
            + this.matrix[2][1] * color[1]
            + this.matrix[2][2] * color[2]);
    transformedColor[3] = color[3];
    return transformedColor;
  }

  // Creates a 3x3 matrix with values depending on the type of transform operation
  protected abstract double[][] initializeMatrix();
}
