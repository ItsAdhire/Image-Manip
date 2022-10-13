package controller.format;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Image;

/**
 * This class represents a set of operations to load and save an
 * image using file paths (across all types of file paths).
 */
public abstract class AbstractImageFormat implements ImageFormat {

  @Override
  public Image load(String file) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid File Path");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      if (!line.startsWith("#")) {
        builder.append(line + System.lineSeparator());
      }
    }

    sc.close();
    return parse(builder.toString());
  }

  // Obtains the pixels array, width, and height values of an image
  // from the given content, creates a new Image with these values,
  // and returns it
  protected abstract Image parse(String content);
}
