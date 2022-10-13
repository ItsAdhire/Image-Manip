import controller.ControllerGUI;
import controller.GUIControllerImpl;
import model.ImageModel;
import model.SimpleImageModel;
import view.ImageGUIView;
import view.ImageGUIViewImpl;

/**
 * Sets up the variables that the controller needs. Passes these variables
 * to the controller and starts the program.
 */
public class ImageManipulationAndEnhancement {
  /**
   * Initializes the correct variables.
   * Passes the variables to the controller and starts the game.
   *
   * @param args the given String array of arguments.
   */
  public static void main(String[] args) {
    ImageModel model = new SimpleImageModel();
    ControllerGUI controller = new GUIControllerImpl(model);
    ImageGUIView v = new ImageGUIViewImpl();
    controller.setView(v);
    /*ImageModel model = new SimpleImageModel();
    ImageView view = new ImageViewImpl();

    if (args.length > 1 && args[0].equals("-file")) {
      try {
        Readable rd = new InputStreamReader(new FileInputStream(args[1]));
        Controller controller = new TextControllerImpl(model, view, rd);
        controller.run();
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Given file not found!");
      }
    }
    else{
        Controller controller = new TextControllerImpl(model, view);
        controller.run();
    }*/
  }
}
