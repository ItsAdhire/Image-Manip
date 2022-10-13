package controller;

import model.operations.BlueGreyScale;
import model.operations.Blur;
import model.operations.Brighten;
import model.operations.GreenGreyScale;
import model.operations.Greyscale;
import model.operations.HorizontalFlip;
import model.operations.IntensityGreyScale;
import model.operations.LumaGreyScale;
import model.operations.RedGreyScale;
import model.operations.Sepia;
import model.operations.Sharpen;
import model.operations.ValueGreyScale;
import model.operations.VerticalFlip;

/**
 * This class represents a set of operations that allows the view to communicate which operation
 * button was pressed to the controller.
 */
public class SimpleFeatures implements Features {
  private ControllerGUI controller;

  /**
   * Creates a new SimpleFeatures object using the given controller.
   * @param controller a ControllerGUI object.
   */
  public SimpleFeatures(ControllerGUI controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Given null controller");
    }
    this.controller = controller;
  }

  @Override
  public void load(String path) {
    controller.load(path);
  }

  @Override
  public void save(String path) {
    controller.save(path);
  }

  @Override
  public void brighten() {
    controller.operate(new Brighten("image", 10));
  }

  @Override
  public void darken() {
    controller.operate(new Brighten("image", -10));
  }

  @Override
  public void flipHor() {
    controller.operate(new HorizontalFlip("image"));
  }

  @Override
  public void flipVer() {
    controller.operate(new VerticalFlip("image"));
  }

  @Override
  public void redGrey() {
    controller.operate(new RedGreyScale("image"));
  }

  @Override
  public void greenGrey() {
    controller.operate(new GreenGreyScale("image"));
  }

  @Override
  public void blueGrey() {
    controller.operate(new BlueGreyScale("image"));
  }

  @Override
  public void valueGrey() {
    controller.operate(new ValueGreyScale("image"));
  }

  @Override
  public void intensityGrey() {
    controller.operate(new IntensityGreyScale("image"));
  }

  @Override
  public void lumaGrey() {
    controller.operate(new LumaGreyScale("image"));
  }

  @Override
  public void greyscale() {
    controller.operate(new Greyscale("image"));
  }

  @Override
  public void sepia() {
    controller.operate(new Sepia("image"));
  }

  @Override
  public void blur() {
    controller.operate(new Blur("image"));
  }

  @Override
  public void sharpen() {
    controller.operate(new Sharpen("image"));
  }
}
