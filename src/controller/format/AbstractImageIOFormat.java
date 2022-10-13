package controller.format;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Image;

/**
 * This class represents a set of operations to load and save an
 * image using file paths (across all types of file paths, not including PPM files).
 */
public abstract class AbstractImageIOFormat implements ImageFormat {
  @Override
  public Image load(String file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }

    BufferedImage bi = null;

    try {
      bi = ImageIO.read(new FileInputStream(file));
      if (bi == null) {
        throw new IOException();
      }
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid File Path");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("File type is not properly formatted");
    }

    int width = bi.getWidth();
    int height = bi.getHeight();
    int[] pixels = new int[width * height];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        pixels[r * width + c] = bi.getRGB(c, r);
      }
    }
    return new Image(pixels, width, height);
  }

  @Override
  public void save(String file, Image image) throws IOException {
    if (file == null || image == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }

    BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(),
            getImageType());
    bi.setRGB(0, 0, image.getWidth(), image.getHeight(), image.getPixelArray(),
            0, image.getWidth());

    try {
      ImageIO.write(bi, this.getFormatName(), new FileOutputStream(file));
    }
    catch (IOException e) {
      throw new IOException("Given invalid path to file");
    }
  }

  // Returns the String representing a file path's extension
  protected abstract String getFormatName();

  // Returns an integer representing a BufferedImage object's type (the default has no alpha value)
  protected int getImageType() {
    return BufferedImage.TYPE_INT_RGB;
  }
}
