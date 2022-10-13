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
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

/**
 * Represents a set of tests for the TextControllerImpl class.
 */
public class TextControllerImplTest {

  ImageModel model;
  ImageView view;
  Controller controller;
  Readable rd0;
  Appendable ap;

  @Before
  public void init() {
    ap = new StringBuilder();

    model = new SimpleImageModel();
    view = new ImageViewImpl(ap);
  }

  @Test
  public void invalidConstructors() {
    try {
      Controller c1 = new TextControllerImpl(null, view);
      fail("created controller with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("inputs cannot be null", e.getMessage());
    }

    try {
      Controller c1 = new TextControllerImpl(null, null);
      fail("created controller with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("inputs cannot be null", e.getMessage());
    }

    try {
      Controller c1 = new TextControllerImpl(model, null);
      fail("created controller with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("inputs cannot be null", e.getMessage());
    }

    try {
      Controller c1 = new TextControllerImpl(model, view, null);
      fail("created controller with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("inputs cannot be null", e.getMessage());
    }

    try {
      Controller c1 = new TextControllerImpl(null, null, null);
      fail("created controller with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("inputs cannot be null", e.getMessage());
    }
  }

  @Test
  public void testValidCommands() {
    rd0 = new StringReader("load res/random1.ppm rand1\n" +
            "brighten rand1 rand1-bright 10\n" +
            "save res/random1-bright.ppm rand1-bright\n" +
            "red-component rand1 rand1-redgrey\n" +
            "save res/random1-redcomponent.ppm rand1-redgrey\n" +
            "load res/random2.ppm rand2\n" +
            "luma-component rand2 rand2-luma\n" +
            "blur rand2-luma rand2-blur\n" +
            "save res/random2-luma.ppm rand2-luma\n" +
            "save res/random2-luma.bmp rand2-luma");

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    String out = "";
    assertEquals(out, ap.toString());

    int[] pix0 = new int[]{
      -1, -119, -30454, -7796214, -16119159, -7763575, -5597526, -47361, -3012078,
      -13108626, -16119286, -14798286, -12819296, -10840366, -9527041
    };
    int[] pix1 = new int[]{
      -1, -1, -1, -8421505, -16777216, -8421505, -6250336, -1, -3618616,
      -13816531, -16777216, -15461356, -13487566, -11513776, -10197916
    };
    int[] pix2 = new int[]{-1, -12500671, -13224394, -3618616};

    Image bright = new PPMFormat().load("res/random1-bright.ppm");
    Image redgrey = new PPMFormat().load("res/random1-redcomponent.ppm");
    Image luma = new PPMFormat().load("res/random2-luma.ppm");

    assertEquals(5, bright.getWidth());
    assertEquals(3, bright.getHeight());
    assertArrayEquals(pix0, bright.getPixelArray());

    assertEquals(5, redgrey.getWidth());
    assertEquals(3, redgrey.getHeight());
    assertArrayEquals(pix1, redgrey.getPixelArray());

    assertEquals(2, luma.getWidth());
    assertEquals(2, luma.getHeight());
    assertArrayEquals(pix2, luma.getPixelArray());
  }

  @Test
  public void testQuit() {
    rd0 = new StringReader("load res/random1.ppm rand1\n" +
            "q and this stuff wont matter");
    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    assertEquals("Quitting Program!\n", ap.toString());

    rd0 = new StringReader("load res/random1.ppm rand1\n" +
            "Q and this stuff wont matter");

    controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    assertEquals("Quitting Program!\nQuitting Program!\n", ap.toString());
  }

  @Test
  public void testInvalidCommands() {
    rd0 = new StringReader("elbow res/random2.ppm\n" +
            "what am i doing\n" +
            "load res/random2.ppm rand\n" +
            "make-good rand\n" +
            "what\n" +
            "green component rand rand\n" +
            "green-component rand rand");
    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    String out = "Invalid command \"elbow\"! Please enter a valid command.\n" +
            "Invalid command \"res/random2.ppm\"! Please enter a valid command.\n" +
            "Invalid command \"what\"! Please enter a valid command.\n" +
            "Invalid command \"am\"! Please enter a valid command.\n" +
            "Invalid command \"i\"! Please enter a valid command.\n" +
            "Invalid command \"doing\"! Please enter a valid command.\n" +
            "Invalid command \"make-good\"! Please enter a valid command.\n" +
            "Invalid command \"rand\"! Please enter a valid command.\n" +
            "Invalid command \"what\"! Please enter a valid command.\n" +
            "Invalid command \"green\"! Please enter a valid command.\n" +
            "Invalid command \"component\"! Please enter a valid command.\n" +
            "Invalid command \"rand\"! Please enter a valid command.\n" +
            "Invalid command \"rand\"! Please enter a valid command.\n";

    assertEquals(out, ap.toString());
  }

  @Test
  public void testInvalidArguments() {
    rd0 = new StringReader("load what.ppm what\n" +
            "load test/controller/TextControllerImplTest.java java\n" +
            "load res/corrupt0.ppm nowork\n" +
            "load res/random1.ppm rand\n" +
            "brighten rand rand-new ten\n" +
            "save here/there/everywhere.ppm rand\n" +
            "horizontal-flip noexist new\n" +
            "green-component new ");
    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    String out = "File was not found.\n" +
            "Unsupported file format was given.\n" +
            "Given improperly formatted image file.\n" +
            "Invalid Arguments for command given. Please enter a command with valid arguments.\n" +
            "Invalid command \"ten\"! Please enter a valid command.\n" +
            "Invalid file path was given.\n" +
            "Invalid Arguments for command given. Please enter a command with valid arguments.\n" +
            "Invalid Arguments for command given. Please enter a command with valid arguments.\n";

    assertEquals(out, ap.toString());
  }

  @Test
  public void testOps() {
    rd0 = new StringReader("load res/example.ppm ex " +
            "brighten ex bright 30 " +
            "brighten ex dark -30 " +
            "horizontal-flip ex horizontal_flip " +
            "vertical-flip ex vertical_flip " +
            "red-component ex red_component " +
            "green-component ex green_component " +
            "blue-component ex blue_component " +
            "value-component ex value_component " +
            "intensity-component ex intensity_component " +
            "luma-component ex luma_component " + 
            "sepia ex sepia " + 
            "greyscale ex greyscale " + 
            "blur ex blur " + 
            "sharpen ex sharpen ");

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    ImageModel m = new SimpleImageModel();
    m.add("image", new PPMFormat().load("res/example.ppm"));

    new Brighten("image", "bright", 30).execute(m);
    new Brighten("image", "dark", -30).execute(m);
    new HorizontalFlip("image", "hflip").execute(m);
    new VerticalFlip("image", "vflip").execute(m);
    new RedGreyScale("image", "red").execute(m);
    new GreenGreyScale("image", "green").execute(m);
    new BlueGreyScale("image", "blue").execute(m);
    new ValueGreyScale("image", "value").execute(m);
    new IntensityGreyScale("image", "intense").execute(m);
    new LumaGreyScale("image", "luma").execute(m);
    new Sepia("image", "sepia").execute(m);
    new Greyscale("image", "grey").execute(m);
    new Blur("image", "blur").execute(m);
    new Sharpen("image", "sharp").execute(m);
    

    Image bright = m.retrieve("bright");
    Image dark = m.retrieve("dark");
    Image horizontal_flip = m.retrieve("hflip");
    Image vertical_flip = m.retrieve("vflip");
    Image red_component = m.retrieve("red");
    Image green_component = m.retrieve("green");
    Image blue_component = m.retrieve("blue");
    Image value_component = m.retrieve("value");
    Image intensity_component = m.retrieve("intense");
    Image luma_component = m.retrieve("luma");
    Image sepia = m.retrieve("sepia");
    Image grey = m.retrieve("grey");
    Image blur = m.retrieve("blur");
    Image sharp = m.retrieve("sharp");


    assertArrayEquals(bright.getPixelArray(),
            model.retrieve("bright").getPixelArray());
    assertArrayEquals(dark.getPixelArray(),
            model.retrieve("dark").getPixelArray());
    assertArrayEquals(horizontal_flip.getPixelArray(),
            model.retrieve("horizontal_flip").getPixelArray());
    assertArrayEquals(vertical_flip.getPixelArray(),
            model.retrieve("vertical_flip").getPixelArray());
    assertArrayEquals(red_component.getPixelArray(),
            model.retrieve("red_component").getPixelArray());
    assertArrayEquals(green_component.getPixelArray(),
            model.retrieve("green_component").getPixelArray());
    assertArrayEquals(blue_component.getPixelArray(),
            model.retrieve("blue_component").getPixelArray());
    assertArrayEquals(value_component.getPixelArray(),
            model.retrieve("value_component").getPixelArray());
    assertArrayEquals(intensity_component.getPixelArray(),
            model.retrieve("intensity_component").getPixelArray());
    assertArrayEquals(luma_component.getPixelArray(),
            model.retrieve("luma_component").getPixelArray());
    assertArrayEquals(sepia.getPixelArray(),
            model.retrieve("sepia").getPixelArray());
    assertArrayEquals(grey.getPixelArray(),
            model.retrieve("greyscale").getPixelArray());
    assertArrayEquals(blur.getPixelArray(),
            model.retrieve("blur").getPixelArray());
    assertArrayEquals(sharp.getPixelArray(),
            model.retrieve("sharpen").getPixelArray());
  }

  @Test
  public void testFromPPM() {
    rd0 = new StringReader("load res/example.ppm ex-ppm" +
            "save res/example.png ex-ppm " +
            "save res/example.jpg ex-ppm " +
            "save res/example.bmp ex-ppm" );

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();
    
    Image i = new PPMFormat().load("res/example.ppm");
    assertArrayEquals(i.getPixelArray(),
            new PNGFormat().load("res/example.png").getPixelArray());
    assertArrayEquals(i.getPixelArray(),
            new BMPFormat().load("res/example.bmp").getPixelArray());
    assertEquals(i.getWidth(), new JPEGFormat().load("res/example.jpg").getWidth());
    assertEquals(i.getHeight(), new JPEGFormat().load("res/example.jpg").getHeight());
    
  }

  @Test
  public void testFromPNG() {
    rd0 = new StringReader("load res/example.png ex" +
            "save res/example.ppm ex " +
            "save res/example.jpg ex " +
            "save res/example.bmp ex" );

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    Image i = new PNGFormat().load("res/example.png");
    assertArrayEquals(i.getPixelArray(),
            new PPMFormat().load("res/example.ppm").getPixelArray());
    assertArrayEquals(i.getPixelArray(),
            new BMPFormat().load("res/example.bmp").getPixelArray());
    assertEquals(i.getWidth(), new JPEGFormat().load("res/example.jpg").getWidth());
    assertEquals(i.getHeight(), new JPEGFormat().load("res/example.jpg").getHeight());

  }

  @Test
  public void testFromBMP() {
    rd0 = new StringReader("load res/example.bmp ex" +
            "save res/example.png ex " +
            "save res/example.jpg ex " +
            "save res/example.ppm ex" );

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    Image i = new BMPFormat().load("res/example.bmp");
    assertArrayEquals(i.getPixelArray(),
            new PNGFormat().load("res/example.png").getPixelArray());
    assertArrayEquals(i.getPixelArray(),
            new PPMFormat().load("res/example.ppm").getPixelArray());
    assertEquals(i.getWidth(), new JPEGFormat().load("res/example.jpg").getWidth());
    assertEquals(i.getHeight(), new JPEGFormat().load("res/example.jpg").getHeight());

  }

  @Test
  public void testFromJPEG() {
    rd0 = new StringReader("load res/random1.jpeg ex " +
            "save res/test.png ex " +
            "save res/test.ppm ex " +
            "save res/test.bmp ex" );

    Controller controller = new TextControllerImpl(model, view, rd0);
    controller.run();

    Image i = new JPEGFormat().load("res/random1.jpeg");
    assertArrayEquals(i.getPixelArray(),
            new PNGFormat().load("res/test.png").getPixelArray());
    assertArrayEquals(i.getPixelArray(),
            new BMPFormat().load("res/test.bmp").getPixelArray());
    assertArrayEquals(i.getPixelArray(),
            new PPMFormat().load("res/test.ppm").getPixelArray());

  }

  @Test
  public void testViewIOException() {
    rd0 = new StringReader("load res/random2.ppm rand\n" +
            "this invalid command will is bad so the controller " +
            "will try to send a message but cant");
    Controller controller = new TextControllerImpl(model, new FailingView(), rd0);

    try {
      controller.run();
      fail("Succeeded with failing view");
    } catch (IllegalStateException e) {
      assertEquals("Could not render message.", e.getMessage());
    }


    Image i2 = model.retrieve("rand");
    int[] pix2 = new int[]{-1, -8113396, -8709994, -15794233};

    assertEquals(2, i2.getWidth());
    assertEquals(2, i2.getHeight());
    assertArrayEquals(pix2, i2.getPixelArray());
  }

  @Test
  public void testLoggingModel() {
    rd0 = new StringReader("load res/random1.ppm rand1\n" +
            "brighten rand1 rand1-bright 10\n" +
            "load test/controller/TextControllerImplTest.java java\n" +
            "load res/corrupt0.ppm nowork\n" +
            "load res/random1.ppm rand\n" +
            "save res/random1-bright.ppm rand1-bright\n" +
            "red-component rand1 rand1-redgrey\n" +
            "save res/random1-redcomponent.ppm rand1-redgrey\n" +
            "load res/random2.ppm rand2\n" +
            "brighten rand rand-new ten\n" +
            "save here/there/everywhere.ppm rand\n" +
            "luma-component rand2 rand2-luma\n" +
            "save res/random2-luma.ppm rand2-luma\n");

    Appendable log = new StringBuilder();

    Controller controller = new TextControllerImpl(new LoggingModel(log), view, rd0);
    controller.run();

    String out = "\n" +
            "Adding image: rand1\n" +
            "Size: 5 3\n" +
            "Getting image: rand1\n" +
            "Adding image: rand\n" +
            "Size: 5 3\n" +
            "Getting image: rand1-bright\n" +
            "Getting image: rand1\n" +
            "Getting image: rand1-redgrey\n" +
            "Adding image: rand2\n" +
            "Size: 2 2\n" +
            "Getting image: rand\n" +
            "Getting image: rand2\n" +
            "Getting image: rand2-luma";

    assertEquals(out, log.toString());
  }


  private class FailingView implements ImageView {
    @Override
    public void renderMessage(String message) throws IOException {
      throw new IOException("FailingView throws exception on render");
    }
  }

  private class LoggingModel implements ImageModel {

    Appendable log;

    private LoggingModel(Appendable log) {
      this.log = log;
    }

    @Override
    public void add(String imageName, Image image) {
      try {
        this.log.append("\nAdding image: " + imageName);
        this.log.append("\nSize: " + image.getWidth() + " " + image.getHeight());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

    @Override
    public Image retrieve(String imageName) {
      try {
        this.log.append("\nGetting image: " + imageName);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

}
