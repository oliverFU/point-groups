package pointGroups.geometry;

/**
 * Right now this only contains a String. We may change this to a propper
 * representation of the result like points and edges.
 * 
 * @author nadjascharf
 */
public class Schlegel
{
  public final Point3D[] points;
  public final Edge<Point3D,Point3D>[] edges;

  public Schlegel(Point3D[] points, Edge<Point3D,Point3D>[] edges) {
    this.points = points;
    this.edges = edges;
  }
}