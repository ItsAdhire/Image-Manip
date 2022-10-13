package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
import view.ImageGUIView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GUIControllerImplTest {
  ImageModel model;
  ImageGUIView view;
  ControllerGUI controller;

  Appendable ap;

  @Before
  public void init() {
    ap = new StringBuilder();
    model = new SimpleImageModel();
    view = new MockGUIView(ap);
    controller = new GUIControllerImpl(model);
    controller.setView(view);

    model.add("image", new PNGFormat().load("res/example.png"));
    model.add("test", new PNGFormat().load("res/example.png"));
  }

  @Test
  public void invalidOperations() {
    ControllerGUI controller = new GUIControllerImpl(new SimpleImageModel());
    controller.setView(view);

    controller.save("test.png");
    controller.operate(new Brighten("image", 10));
    controller.operate(new Brighten("image", -10));
    controller.operate(new HorizontalFlip("image"));
    controller.operate(new VerticalFlip("no exist"));
    controller.operate(new RedGreyScale("image"));
    controller.operate(new GreenGreyScale("image"));
    controller.operate(new BlueGreyScale("image"));
    controller.operate(new ValueGreyScale("image"));
    controller.operate(new IntensityGreyScale("image"));
    controller.operate(new LumaGreyScale("image"));
    controller.operate(new Greyscale("does not exist"));
    controller.operate(new Sepia("fake image"));
    controller.operate(new Blur("image"));
    controller.operate(new Sharpen("image"));

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

    controller.load("test");
    controller.load("fake.png");
    controller.load("src/model/Image.java");
    controller.load("res/script.txt");
    controller.load("");
    controller.load("res/corrupt0.ppm");

    controller.save("this is not a valid path haha");
    controller.save("point");
    controller.save("bad.java");
    controller.save("fake/fake.png");

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
    controller.load("res/example.bmp");
    Image i = model.retrieve("image");
    assertArrayEquals(new BMPFormat().load("res/example.bmp").getPixelArray(), i.getPixelArray());

    controller.load("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(new PNGFormat().load("res/example.png").getPixelArray(), i.getPixelArray());

    controller.load("res/example.jpg");
    i = model.retrieve("image");
    assertArrayEquals(new JPEGFormat().load("res/example.jpg").getPixelArray(), i.getPixelArray());

    controller.load("res/example.png");
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
    controller.save("res/example.bmp");
    Image i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new BMPFormat().load("res/example.bmp").getPixelArray());

    controller.save("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new PNGFormat().load("res/example.png").getPixelArray());

    controller.save("res/example.jpg");
    i = model.retrieve("image");
    assertEquals(i.getWidth(), new JPEGFormat().load("res/example.jpg").getWidth());
    assertEquals(i.getHeight(), new JPEGFormat().load("res/example.jpg").getHeight());

    controller.save("res/example.png");
    i = model.retrieve("image");
    assertArrayEquals(i.getPixelArray(), new PPMFormat().load("res/example.ppm").getPixelArray());
  }

  @Test 
  public void testValidOperations() {
    controller.operate(new Brighten("image", 10));
    new Brighten("test", 10).execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new Brighten("image", -10));
    new Brighten("test", -10).execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new HorizontalFlip("image"));
    new HorizontalFlip("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new VerticalFlip("image"));
    new VerticalFlip("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new RedGreyScale("image"));
    new RedGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new GreenGreyScale("image"));
    new GreenGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new BlueGreyScale("image"));
    new BlueGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new ValueGreyScale("image"));
    new ValueGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new IntensityGreyScale("image"));
    new IntensityGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new LumaGreyScale("image"));
    new LumaGreyScale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new Greyscale("image"));
    new Greyscale("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new Sepia("image"));
    new Sepia("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new Blur("image"));
    new Blur("test").execute(model);
    assertArrayEquals(model.retrieve("test").getPixelArray(), model.retrieve("image").getPixelArray());

    controller.operate(new Sharpen("image"));
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
