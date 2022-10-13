# Image Manip

Image Manip is an image editor and manipulator written in java. It currently uses Text Based 
input and output however will be using a GUI in the future. The program can perform operations on 
images by using commands to be run on files and loaded images. To use the program just type in the
relevant commands once your run the program. To get started, load an image into the program using 
the `load` command.

## Design Overview

We followed the MVC design pattern for our program, meaning that we separated
our program into a model, view, and controller. 

The model is responsible for storing the data used to represent the images. 
The Image class represents an image, which consists of a list of pixels, 
and its width and height. We represent each pixel as a 32-bit integer, which
also accounts for transparency. The PixelUtils class allows for conversion
between r,g,b,a values and the 32-bit integers. The SimpleImageModel class implements the
ImageModel and ImageModelState interfaces which means it can add Image objects and
also retrieve them. Note that the model does not store images of a particular format, which 
means so that it can store images that came from multiple formats at once and can even convert 
between formats. 

The model also works with image operations. The AbstractImageOp class implements the ImageOp 
interface, which means that it performs an operation on the model. The AbstractUnitImageOp makes it easy 
to add support for operations that perform the same operation on each pixel in an image. 
The AbstractGreyScaleOp extends the  AbstractUnitImageOp class, which makes it easy to add support for 
operations that greyscale each pixel in an image the same way. The AbstractMovePixelsOp 
extends the AbstractImageOp class, which makes it easy to move pixels in an image following a certain pattern.
In addition, we have an AbstractColorTransform class which extends the AbstractUnitImageOp with 
makes it easy to transform each pixel using a color transform matrix which is used for greyscale and
to apply the sepia tone. There is also an AbstractFilter class which extends the AbstractImageOp 
which applies a filter matrix to the image. This is used for blurring and sharpening given their 
kernel (filtering matrix).

The view is responsible for displaying text. The ImageViewImpl class implements
the ImageView interface which means that it can render messages through a text output. 

The controller is responsible for taking in user input and using this input to interact
with both the model and the view. The AbstractImageFormat class implements the ImageFormat 
interface, which means that it can take in files of any type and convert the image into a representation
that the model can use and vice versa. The PPMFormat class is responsible for loading and 
saving PPM files. The AbstractImageFormat class makes it easy to add support for other types of files.
The AbstractImageIOFormat class makes it easy to add support for types of files that java can already
handle. This includes jpeg, bmp, and png, which is why the JPEGFormat, BMPFormat, and the PNGFormat class
extend this abstract class and allow for the loading and saving of these file types.

## Design Changes

### Completed Parts
The current operations of the program are completed which means that they will not change functionality. 
The list of these operations can be found in the USEME. The text based ImageViewImpl is also complete 
and will not be changed. In addition, the already supplied formats (ppm, png, jpeg/jpg, bmp) are all complete,
and their functionality will not change. There will however be the possibility of adding
additional operations, views (such as a GUI), and more supported formats which will not affect these
completed aspects of the program.

### Design Modifications
Other than adding features and support which follow our design and do not affect other aspects of our program,
we did have to modify our controller. First, we changed how we determine what file type to load from when given a file path.
Before we looked into the file and determined the type (for example how ppm files start with P3), however we
realized this is not a consistent approach and modified to determine the file type by extension. In addition to this
we added the four operations (greyscale, sepia, blur, sharpen) to the hashmap. We did this because that way, the client/controller
can interact with the images using these operations. 

## Image Citations
The images used in this project serve as testing images and examples. All of these images were 
created by the creators of this program and have authorized use of them for this project.
