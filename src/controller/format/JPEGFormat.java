package controller.format;

/**
 * This class represents the ability to load and save a JPEG file.
 */
public class JPEGFormat extends AbstractImageIOFormat {
  @Override
  protected String getFormatName() {
    return "jpg";
  }
}
