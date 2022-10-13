package model.operations;

import model.Image;
import model.ImageModel;

/**
 * Represents an abstraction for macros that only modify a particular image in the model.
 */
public abstract class AbstractImageOp implements ImageOp {
  private String in;
  private String out;

  /**
   * Sets the input and output names of the images to retrieve and add.
   *
   * @param in  name of the input image the macro will read from.
   * @param out the name of the image the changed version will output to.
   */
  public AbstractImageOp(String in, String out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Given a null argument");
    }

    this.in = in;
    this.out = out;
  }

  /**
   * Sets the input and output names of the images to be the same.
   * This means that the changed version will overwrite the previous image.
   *
   * @param in the name of the input image the macro will read from.
   */
  public AbstractImageOp(String in) {
    this(in, in);
  }

  @Override
  public void execute(ImageModel m) throws IllegalStateException {
    Image image = m.retrieve(in);
    if (image == null) {
      throw new IllegalStateException("No image with name " + in + " exists");
    }

    m.add(out, modifyImage(image));
  }

  /**
   * Uses an input image to produced an altered version of it.
   *
   * @param image the input image.
   * @return the altered version of the image.
   */
  protected abstract Image modifyImage(Image image);
}
