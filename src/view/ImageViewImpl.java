package view;

import java.io.IOException;

/**
 * This class represents a set of operations that can be used to display text
 * using an Appendable object.
 */
public class ImageViewImpl implements ImageView {
  private Appendable destination;

  /**
   * Creates an ImageViewImpl object with standard output.
   */
  public ImageViewImpl() {
    this(System.out);
  }

  /**
   * Creates an ImageViewImpl object using the given Appendable object.
   * @param destination an Appendable object.
   * @throws IllegalArgumentException if the destination is null.
   */
  public ImageViewImpl(Appendable destination)
          throws IllegalArgumentException {
    if (destination == null) {
      throw new IllegalArgumentException("Given null input");
    }
    this.destination = destination;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }

}
