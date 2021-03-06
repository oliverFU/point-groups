package pointGroups.util;

import java.util.concurrent.Future;


/**
 * An instance holds everything polymake needs for computation.
 * 
 * @author Nadja, Simon
 */
public interface Transformer<E>
  extends Future<E>
{
  /**
   * Generates a script.
   * 
   * @return A script as String.
   */
  public String toScript();

  /**
   * Sets the result string of an external calculation as the basis for a later
   * transformation to an internal representation of the result.
   * 
   * @param resultString Result of an external calculation, e.g. polymake.
   */
  public void setResultString(String resultString);

}
