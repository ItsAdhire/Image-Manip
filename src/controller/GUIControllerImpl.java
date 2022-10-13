package controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.format.BMPFormat;
import controller.format.JPEGFormat;
import controller.format.PNGFormat;
import controller.format.PPMFormat;
import model.Image;
import model.ImageModel;
import model.PixelUtils;
import model.operations.ImageOp;
import view.ImageGUIView;

/**
 * This class represents a set of operations that can be used to control the
 * interaction between an ImageModel object (the model) and an ImageGUIView
 * object (the view). This class also sends a Features instance to the view.
 */
public class GUIControllerImpl implements ControllerGUI {
  private ImageModel model;
  private ImageGUIView view;
  private Features features;

  /**
   * Creates a new GUIControllerImpl using the given model.
   * @param model an ImageModel object.
   */
  public GUIControllerImpl(ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
    this.model = model;
    this.features = new SimpleFeatures(this);
  }

  @Override
  public void setView(ImageGUIView view) {
    if (view == null) {
      throw new IllegalArgumentException("given null view");
    }
    this.view = view;
    view.addFeatures(features);
  }

  @Override
  public void operate(ImageOp op) {
    try {
      op.execute(model);
      displayImage();
    }
    catch (IllegalStateException e) {
      displayMessage("No Image loaded. Use the Load operation first!");
    }
  }

@Override
  public void load(String filePath) {
    if (filePath == null) {
      return;
    }

    try {
      new FileInputStream(filePath);
    } catch (FileNotFoundException e) {
      this.displayMessage("File was not found.");
      return;
    }

    try {
      if (filePath.endsWith(".ppm")) {
        model.add("image", new PPMFormat().load(filePath));
      }
      else if (filePath.endsWith(".png")) {
        model.add("image", new PNGFormat().load(filePath));
      }
      else if (filePath.endsWith(".bmp")) {
        model.add("image", new BMPFormat().load(filePath));
      }
      else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
        model.add("image", new JPEGFormat().load(filePath));
      }
      else {
        this.displayMessage("Unsupported file format was given.");
      }
    } catch (IllegalArgumentException e) {
      displayMessage("Given improperly formatted image file.");
    }
    displayImage();
  }

  @Override
  public void save(String filePath) {
    if (filePath == null) {
      return;
    }

    try {
      if (filePath.endsWith(".ppm")) {
        new PPMFormat().save(filePath, this.model.retrieve("image"));
      } else if (filePath.endsWith(".png")) {
        new PNGFormat().save(filePath, this.model.retrieve("image"));
      } else if (filePath.endsWith(".bmp")) {
        new BMPFormat().save(filePath, this.model.retrieve("image"));
      } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
        new JPEGFormat().save(filePath, this.model.retrieve("image"));
      } else {
        displayMessage("Entered file type cannot be saved to.");
      }
    } catch (IOException e) {
      this.displayMessage("Invalid file path was given.");
    } catch (IllegalArgumentException ex) {
      this.displayMessage("No Image to save. Use the Load operation first!");
    }
  }

  // Displays the given message by passing it to this TextControllerImpl object's
  // renderMessage method. Throws an IllegalStateException if the message could
  // not be rendered.
  private void displayMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not render message.");
    }
  }

  private void displayImage() {
    Image image = model.retrieve("image");
    if (image == null) {
      return;
    }
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    bi.setRGB(0, 0, width, height, image.getPixelArray(),
            0, width);
    int[] pixels = image.getPixelArray();
    int[][] freq = frequencyFromPixels(pixels);

    int[][] test = new int[4][255];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 255; j++) {
        test[i][j] = j % 100;
      }
    }
    view.renderImage(bi, freq);
  }

  private int[][] frequencyFromPixels(int[] pixels) {
    int[][] output = new int[4][256];
    for(int pix : pixels) {
      int[] rgb = PixelUtils.toRGBA(pix);

      output[0][rgb[0]] += 1;
      output[1][rgb[1]] += 1;
      output[2][rgb[2]] += 1;
      int intensity = (rgb[0] + rgb[1] + rgb[2]) / 3;
      output[3][intensity] += 1;
    }

    return output;

  }


}
