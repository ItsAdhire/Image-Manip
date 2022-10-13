package model.operations;

import model.Image;


/**
 * This class represents a macro that flips an image horizontally.
 */
public class HorizontalFlip extends AbstractMovePixelsOp {
  /**
   * Creates a new HorizontalFlip object using the given input and output image
   * names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public HorizontalFlip(String in, String out) {
    super(in, out);
  }

  /**
   * Creates a new HorizontalFlip object using the given input image name.
   * @param in the String name of the input image.
   */
  public HorizontalFlip(String in) {
    super(in);
  }

  @Override
  protected Image modifyImage(Image image) {
    return traverseImage(image, 0, image.getWidth() - 1,
            image.getHeight() - 1, 0);
  }
}
