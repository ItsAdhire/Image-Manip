package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import controller.format.BMPFormat;
import controller.format.JPEGFormat;
import controller.format.PNGFormat;
import controller.format.PPMFormat;
import model.operations.BlueGreyScale;
import model.operations.Blur;
import model.operations.Brighten;
import model.operations.GreenGreyScale;
import model.operations.Greyscale;
import model.operations.HorizontalFlip;
import model.operations.ImageOp;
import model.operations.IntensityGreyScale;
import model.operations.LumaGreyScale;
import model.operations.RedGreyScale;
import model.operations.Sepia;
import model.operations.Sharpen;
import model.operations.ValueGreyScale;
import model.operations.VerticalFlip;
import model.ImageModel;
import view.ImageView;

/**
 * This class represents a set of operations that can be used to control the
 * interaction between an ImageModel object (the model) and an ImageView
 * object (the view). This class also uses a Readable to take in user input
 * and output data accordingly.
 */
public class TextControllerImpl implements Controller {
  private ImageModel model;
  private ImageView view;
  private Readable toReadFrom;

  private Map<String, Function<Scanner, ImageOp>> commands;

  /**
   * Creates a new TextControllerImpl object using the given model, view,
   * and toReadFrom objects. Also adds the possible commands a user can input
   * to execute a certain command (represented by the classes in the operations
   * package) to the commands map. It adds these commands as String name—function
   * key-value pairs.
   * @param model an ImageModel object.
   * @param view an ImageView object.
   * @param toReadFrom a Readable object.
   * @throws IllegalArgumentException if the model, view, or toReadFrom is null.
   */
  public TextControllerImpl(ImageModel model, ImageView view, Readable toReadFrom)
          throws IllegalArgumentException {
    if (model == null || view == null || toReadFrom == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
    this.model = model;
    this.view = view;
    this.toReadFrom = toReadFrom;
    this.commands = new HashMap<>();

    commands.put("brighten", s -> new Brighten(s.next(), s.next(), s.nextInt()));
    commands.put("red-component", s -> new RedGreyScale(s.next(), s.next()));
    commands.put("green-component", s -> new GreenGreyScale(s.next(), s.next()));
    commands.put("blue-component", s -> new BlueGreyScale(s.next(), s.next()));
    commands.put("value-component", s -> new ValueGreyScale(s.next(), s.next()));
    commands.put("intensity-component", s -> new IntensityGreyScale(s.next(), s.next()));
    commands.put("luma-component", s -> new LumaGreyScale(s.next(), s.next()));
    commands.put("horizontal-flip", s -> new HorizontalFlip(s.next(), s.next()));
    commands.put("vertical-flip", s -> new VerticalFlip(s.next(), s.next()));
    commands.put("greyscale", s -> new Greyscale(s.next(), s.next()));
    commands.put("sepia", s -> new Sepia(s.next(), s.next()));
    commands.put("blur", s -> new Blur(s.next(), s.next()));
    commands.put("sharpen", s -> new Sharpen(s.next(), s.next()));
  }

  /**
   * Creates a new TextControllerImpl object using the given model and view objects.
   * @param model an ImageModel object.
   * @param view an ImageView object.
   * @throws IllegalArgumentException if the model or view is null.
   */
  public TextControllerImpl(ImageModel model, ImageView view)
          throws IllegalArgumentException {
    this(model, view, new InputStreamReader(System.in));
  }

  @Override
  public void run() throws IllegalStateException {
    Scanner scanner = new Scanner(this.toReadFrom);

    while (scanner.hasNext()) {
      String s = scanner.next();
      switch (s) {
        case "q":
        case "Q":
          displayMessage("Quitting Program!" + System.lineSeparator());
          return;
        case "load":
          try {
            this.loadHelp(scanner);
          } catch (NoSuchElementException e) {
            this.displayMessage("Invalid Arguments for command given. "
                    + "Please enter a command with valid arguments.");
          }
          break;
        case "save":
          try {
            this.saveHelp(scanner);
          } catch (NoSuchElementException e) {
            this.displayMessage("Invalid Arguments for command given. "
                    + "Please enter a command with valid arguments.");
          }
          break;
        default:
          Function<Scanner, ImageOp> func;
          func = commands.getOrDefault(s, null);
          if (func == null) {
            this.displayMessage("Invalid command \"" + s + "\"! Please enter a valid command."
                    + System.lineSeparator());
          } else {
            try {
              func.apply(scanner).execute(model);
            } catch (NoSuchElementException | IllegalStateException e) {
              this.displayMessage("Invalid Arguments for command given. "
                      + "Please enter a command with valid arguments." + System.lineSeparator());
            }
          }
      }


    }
  }

  // Uses the given Scanner to read the user's input for the file path
  // and image name. Attempts to load this file from the given file path.
  // If there is an invalid file path given or if the type of file path
  // cannot be accommodated, displays a message stating so. Throws an
  // IllegalStateException if the message could not be rendered. Throws a
  // NoSuchElementException if the scanner could not read the inputs or
  // ran out of inputs to read.
  private void loadHelp(Scanner scan) throws NoSuchElementException, IllegalStateException {
    String filePath = scan.next();
    String imageName = scan.next();

    try {
      new FileInputStream(filePath);
    } catch (FileNotFoundException e) {
      this.displayMessage("File was not found." + System.lineSeparator());
      return;
    }

    try {
      if (filePath.endsWith(".ppm")) {
        model.add(imageName, new PPMFormat().load(filePath));
      }
      else if (filePath.endsWith(".png")) {
        model.add(imageName, new PNGFormat().load(filePath));
      }
      else if (filePath.endsWith(".bmp")) {
        model.add(imageName, new BMPFormat().load(filePath));
      }
      else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
        model.add(imageName, new JPEGFormat().load(filePath));
      }
      else {
        this.displayMessage("Unsupported file format was given." + System.lineSeparator());
      }
    } catch (IllegalArgumentException e) {
      displayMessage("Given improperly formatted image file." + System.lineSeparator());
    }
  }

  // Uses the given Scanner to read the user's input for the file path
  // and image name. Attempts to save this file under the given name
  // based on the type of file path. If there is an invalid file path given
  // // or if the type of file path cannot be accommodated, displays a message
  // stating so. Throws an IllegalStateException if the message could
  // not be rendered. Throws a NoSuchElementException if the scanner could
  // not read the inputs or ran out of inputs to read.
  private void saveHelp(Scanner scan) throws NoSuchElementException, IllegalStateException {
    String filePath = scan.next();
    String imageName = scan.next();

    try {
      if (filePath.endsWith(".ppm")) {
        new PPMFormat().save(filePath, this.model.retrieve(imageName));
      } else if (filePath.endsWith(".png")) {
        new PNGFormat().save(filePath, this.model.retrieve(imageName));
      } else if (filePath.endsWith(".bmp")) {
        new BMPFormat().save(filePath, this.model.retrieve(imageName));
      } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
        new JPEGFormat().save(filePath, this.model.retrieve(imageName));
      } else {
        displayMessage("Entered file type cannot be saved to." + System.lineSeparator());
      }
    } catch (IOException e) {
      this.displayMessage("Invalid file path was given." + System.lineSeparator());
    } catch (IllegalArgumentException ex) {
      this.displayMessage("Could not find image with that name" + System.lineSeparator());
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
}
