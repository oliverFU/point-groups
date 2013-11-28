package pointGroups.util.polymake;

import pointGroups.geometry.Pair;
import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Schlegel;

import java.util.Collection;


/**
 * Transforms points into a script as string for computing schlegel vertices and
 * edges
 * 
 * @author Nadja, Simon
 */
public class SchlegelTransformer
  implements PolymakeTransformer<Schlegel>
{

  private final Collection<? extends Point> points;
  private final int facet;
  private String result;

  public SchlegelTransformer(Collection<? extends Point> points) {
    this.points = points;
    this.facet = -1;
  }

  public SchlegelTransformer(Collection<? extends Point> points, int facet) {
    this.points = points;
    this.facet = facet;
  }

  @Override
  public String toScript() {

    // Suppose the script has around 1000 characters
    StringBuilder script = new StringBuilder(1000);
    script.append("use application \"polytope\";\n");

    script.append("my $mat=new Matrix<Rational>(");
    script.append(pointsToString());
    script.append(");\n");
    script.append("my $p = new Polytope(POINTS=>$mat);\n");

    script.append("my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;\n");
    script.append("my $edges = $p->GRAPH->EDGES;\n");

    script.append("my $v = \"$schlegelverts\";\n");
    script.append("my $e = \"$edges\";\n");

    script.append("print $v.\"$\".$e");
    return script.toString();
  }

  private String pointsToString() {
    StringBuilder matrix = new StringBuilder(200);
    double[] pointComps;

    matrix.append("[");

    for (Point point : points) {
      matrix.append("[1");
      pointComps = point.getComponents();
      for (double comp : pointComps) {
        matrix.append("," + comp);
      }
      // for simplicity always appending a comma after each transformation of a
      // point
      // afterwards the last comma will be replaced by a closing bracket ']' of
      // the matrix
      matrix.append("],");
    }
    // replacing last comma of the for-loop with a closing bracket of the matrix
    matrix.setCharAt(matrix.length() - 1, ']');
    return matrix.toString();
  }

  @Override
  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public Schlegel getResult() {
    // splitting result string into two. One contains the points, the other the
    // edges.
    String[] pointsAndEdges = result.split("\\$\n");
    String pointsString = pointsAndEdges[0];
    String edgesString = pointsAndEdges[1];

    // splitting string containing points into one per point
    String[] splittedPointsString = pointsString.split("\n");
    // transforming strings into Point-Objects
    Point3D[] points = new Point3D[splittedPointsString.length];
    for (int i = 0; i < splittedPointsString.length; i++) {
      String str = splittedPointsString[i];
      // ignore brackets and split into components
      String[] compStr = str.substring(1, str.length() - 2).split("\\,");
      if (compStr.length == 2) {
        points[i] =
            new Point3D(Double.parseDouble(compStr[0]),
                Double.parseDouble(compStr[1]), 0);
      }
      else if (compStr.length == 3) {
        points[i] =
            new Point3D(Double.parseDouble(compStr[0]),
                Double.parseDouble(compStr[1]), Double.parseDouble(compStr[2]));
      }
      else {
        // TODO Fehler? Vorher? Try-Catch? versehentliche Mischung aus 3D und 2D
        // Punkten?
      }
    }
    // Store Edges as Array von Pair<Point3D,Point3D>
    String[] splittedEdgesString = edgesString.split("\n");
    // TODO das mit den Generics ist Mist. Geht das besser?
    Pair<Point3D, Point3D>[] edges = new Pair[splittedEdgesString.length];
    // start iteration with i = 1 because the first string after splitting is
    // empty caused by leading \n
    for (int i = 1; i < splittedEdgesString.length; i++) {
      String str = splittedEdgesString[i];
      // ignore brackets and split into components
      String[] compStr = str.substring(1, str.length() - 2).split("\\,");
      // TODO what if there are more or less than two components for an edge?
      Point3D from = points[Integer.valueOf(compStr[0])];
      Point3D to = points[Integer.valueOf(compStr[1])];
      edges[i] = new Pair<Point3D, Point3D>(from, to);
    }

    return new Schlegel(points, edges);
  }

}
