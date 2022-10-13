package controller.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Image;
import model.PixelUtils;

/**
 * This class represents a set of operations to load and save
 * an image using file paths.
 */
public class PPMFormat extends AbstractImageFormat {

  @Override
  public Image parse(String content) throws IllegalArgumentException {
    Scanner sc = new Scanner(content);

    if (!sc.hasNext() || !sc.next().equals("P3")) {
      throw new IllegalArgumentException("File type is not PPM");
    }

    try {
      int width = sc.nextInt();
      int height = sc.nextInt();
      int[] pixels = new int[width * height];
      sc.nextInt();

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();

          pixels[i * width + j] = PixelUtils.compactRGBA(new int[]{r, g, b, 255});
        }
      }

      return new Image(pixels, width, height);
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("File type is not properly formatted");
    }
  }

  @Override
  public void save(String file, Image image) throws IOException {
    if (image == null) {
      throw new IllegalArgumentException("Null image given");
    }
    int width = image.getWidth();
    int height = image.getHeight();
    StringBuilder builder = new StringBuilder("P3" + System.lineSeparator());
    builder.append(width + " " + height + " 255" + System.lineSeparator());

    int[] pixels = image.getPixelArray();

    for (int i : pixels) {
      int[] colors = PixelUtils.toRGBA(i);
      builder.append(colors[0] + " " + colors[1] + " " + colors[2] + System.lineSeparator());
    }

    String out = builder.toString();

    Path f = Paths.get(file);
    try {
      Files.writeString(f, out);
    } catch (IOException e) {
      throw new IOException("Given invalid path to file");
    }
  }
}
