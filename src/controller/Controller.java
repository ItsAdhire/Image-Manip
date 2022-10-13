package controller;

/**
 * This interface represents the operations provided by the
 * image model controller.
 */
public interface Controller {
  /**
   * Performs certain operations on an image based
   * on the user's inputs. Obtains data from the
   * model and passes it onto the view, factoring
   * in the user's inputs accordingly.
   * @throws IllegalStateException if a transmission
   *      to the view fails, OR if an attempt to read from the
   *      Readable object fails.
   */
  void run() throws IllegalStateException;
}
