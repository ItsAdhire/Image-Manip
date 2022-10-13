package model.operations;

import model.Image;

/**
 * This class represents a macro that flips an image vertically.
 */
public class VerticalFlip extends AbstractMovePixelsOp {
  /**
   * Creates a new VerticalFlip object using the given input and output image
   * names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public VerticalFlip(String in, String out) {
    super(in, out);
  }

  /**
   * Creates a new VerticalFlip object using the given input image name.
   * @param in the String name of the input image.
   */
  public VerticalFlip(String in) {
    super(in);
  }

  @Override
  protected Image modifyImage(Image image) {
    return traverseImage(image, image.getWidth() - 1, 0,
            0, image.getHeight() - 1);
  }

}
