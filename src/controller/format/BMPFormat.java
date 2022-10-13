package controller.format;

/**
 * This class represents the ability to load and save a BMP file.
 */
public class BMPFormat extends AbstractImageIOFormat {
  @Override
  protected String getFormatName() {
    return "bmp";
  }
}
