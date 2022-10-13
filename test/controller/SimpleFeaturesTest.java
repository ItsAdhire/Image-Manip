package controller;

import org.junit.Before;
import org.junit.Test;

import controller.format.BMPFormat;
import controller.format.JPEGFormat;
import controller.format.PNGFormat;
import controller.format.PPMFormat;
import model.Image;
import model.ImageModel;
import model.SimpleImageModel;
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

import java.io.IOException;

import static org.junit.Assert.*;

import view.ImageGUIView;

public class SimpleFeaturesTest {
  ImageModel model;
  ImageGUIView view;
  ControllerGUI controller;

  Features features;

  Appendable ap;

  @Before
  public void init() {
    ap = new StringBuilder();
    model = new SimpleImageModel();
    view = new MockGUIView(ap);
    controller = new GUIControllerImpl(model);
    features = new SimpleFeatures(controller);
    controller.setView(view);

    model.add("image", new PNGFormat().load("res/example.png"));
    model.add("test", new PNGFormat().load("res/example.png"));
  }

  @Test
  public void invalidOperations() {
    ControllerGUI controller = new GUIControllerImpl(new SimpleImageModel());
    Features features = new SimpleFeatures(controller);
    controller.setView(view);

    features.save("test.png");
    features.brighten();
    features.darken();
    features.flipHor();
    features.flipVer();
    features.redGrey();
    features.greenGrey();
    features.blueGrey();
    features.valueGrey();
    features.intensityGrey();
    features.lumaGrey();
    features.greyscale();
    features.sepia();
    features.blur();
    features.sharpen();

    String out =
            "No Image to save. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n" +
            "No Image loaded. Use the Load operation first!\n";

    assertEquals(out, ap.toString());
  }

  @Test
  public void invalidLoadandSave() {

    features.load("test");
    features.load("fake.png");
    features.load("src/model/Image.java");
    features.load("res/script.txt");
    features.load("");
    features.load("res/corrupt0.ppm");

    features.save("this is not a valid path haha");
    features.save("point");
    features.save("bad.java");
    features.save("fake/fake.png");

    String out = "File was not found.\n" +
            "File was not found.\n" +
            "Unsupported file format was given.\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "Unsupported file format was given.\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "File was not found.\n" +
            "Given improperly formatted image file.\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "Entered file type cannot be saved to.\n" +
            "Entered file type cannot be saved to.\n" +
            "Entered file type cannot be saved to.\n" +
            "Invalid file path was given.\n";

    assertEquals(out, ap.toString());
  }

  @Test
  public void testValidLoad() {
    features.load("res/example.bmp");
    Image i = model.retrieve("image");
    assertArrayEquals(new BMPFormat().load("res/example.bmp").getPixelArray(), i.getPixelArray());

    features.load("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(new PNGFormat().load("res/example.png").getPixelArray(), i.getPixelArray());

    features.load("res/example.jpg");
    i = model.retrieve("image");
    assertArrayEquals(new JPEGFormat().load("res/example.jpg").getPixelArray(), i.getPixelArray());

    features.load("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(new PPMFormat().load("res/example.ppm").getPixelArray(), i.getPixelArray());

    String out = "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 41\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n";

    assertEquals(out, ap.toString());

  }

  @Test
  public void testValidSave() {
    features.save("res/example.bmp");
    Image i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new BMPFormat().load("res/example.bmp").getPixelArray());

    features.save("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new PNGFormat().load("res/example.png").getPixelArray());

    features.save("res/example.jpg");
    i = model.retrieve("image");
    assertEquals(i.getWidth(), new JPEGFormat().load("res/example.jpg").getWidth());
    assertEquals(i.getHeight(), new JPEGFormat().load("res/example.jpg").getHeight());

    features.save("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new PPMFormat().load("res/example.ppm").getPixelArray());
  }

  @Test 
  public void testValidOperations() {
    features.brighten();
    new Brighten("test", 10).execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.darken();
    new Brighten("test", -10).execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.flipHor();
    new HorizontalFlip("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.flipVer();
    new VerticalFlip("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.redGrey();
    new RedGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.greenGrey();
    new GreenGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.blueGrey();
    new BlueGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.valueGrey();
    new ValueGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.intensityGrey();
    new IntensityGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.lumaGrey();
    new LumaGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.greyscale();
    new Greyscale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.sepia();
    new Sepia("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.blur();
    new Blur("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    features.sharpen();
    new Sharpen("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    String out = "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 0\n" +
            "image rendered!\n" +
            "Frequency of red with value 0: 554\n";

    assertEquals(out, ap.toString());
    
  }

  protected class MockGUIView implements ImageGUIView {
    Appendable output;

    protected MockGUIView(Appendable output) {
      this.output = output;
    }

    @Override
    public void addFeatures(Features features) {
    }

    @Override
    public void renderImage(java.awt.Image image, int[][] frequency) {
      try {
        output.append("image rendered!"  + System.lineSeparator());
        output.append("Frequency of red with value 0: " + frequency[0][0] + System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("Could not output that image rendered!");
      }
    }

    @Override
    public void renderMessage(String message) throws IOException {
      output.append(message + System.lineSeparator());
    }

  }
}
