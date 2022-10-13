package model.operations;

import model.ImageModel;

/**
 * Represents operation macros that can be done on an {@link ImageModel} to
 * alter/add images to it.
 */
public interface ImageOp {
  /**
   * Represents the macro to be run on the model.
   *
   * @param m the instance of the {@link ImageModel}.
   * @throws IllegalStateException if an operation cannot be done on a model.
   */
  void execute(ImageModel m) throws IllegalStateException;
}
