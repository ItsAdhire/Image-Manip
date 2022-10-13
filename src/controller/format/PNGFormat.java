package controller.format;

import java.awt.image.BufferedImage;

/**
 * This class represents the ability to load and save a PNG file.
 */
public class PNGFormat extends AbstractImageIOFormat {
  @Override
  protected String getFormatName() {
    return "png";
  }

  @Override
  protected int getImageType() {
    return BufferedImage.TYPE_INT_ARGB;
  }
}
