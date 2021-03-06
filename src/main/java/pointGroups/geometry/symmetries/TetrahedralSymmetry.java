package pointGroups.geometry.symmetries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.UnitQuaternion;


/**
 * The tetrahedral group of order 12, rotational symmetry group of the regular
 * tetrahedron.
 * 
 * @author Alex
 */
public class TetrahedralSymmetry
  implements Symmetry<Point3D, TetrahedralSymmetry>
{
  /**
   * The singleton instance of the tetrahedron's symmetry class.
   */
  private final static TetrahedralSymmetry sym;
  /**
   * Stores the membership relation of the subgroups of the symmetry group
   * represented by {@link TetrahedralSymmetry}. Subgroups are identified by
   * {@link Subgroups}.
   */
  private final Map<Subgroup<TetrahedralSymmetry>, Collection<UnitQuaternion>> subgroupTable;

  /**
   * identity rotation
   */
  final static UnitQuaternion id;

  /**
   * rotation about a height going through an corner of the tetrahedron and the
   * center of the opposite face by an angle of 120°, 240° (4 axes)
   */
  final static UnitQuaternion diagonalAxis1_120, diagonalAxis1_240,
      diagonalAxis2_120, diagonalAxis2_240, diagonalAxis3_120,
      diagonalAxis3_240, diagonalAxis4_120, diagonalAxis4_240;

  /**
   * rotation about an axis from the center of an edge to the center of the
   * opposite edge by an angle of 180° (3 axis)
   */
  final static UnitQuaternion edgeAxis_1, edgeAxis_2, edgeAxis_3;

  {
    subgroupTable =
        new HashMap<Subgroup<TetrahedralSymmetry>, Collection<UnitQuaternion>>();
  }

  /**
   * The symmetry axes of the tetrahedron are the 24 unit hurwitz quaternions.
   * We use only half of them, since q and -q denote the same rotation.
   */
  static {
    sym = new TetrahedralSymmetry();

    id = new UnitQuaternion(1, 0, 0, 0);

    edgeAxis_1 = new UnitQuaternion(0, 1, 0, 0);
    edgeAxis_2 = new UnitQuaternion(0, 0, 1, 0);
    edgeAxis_3 = new UnitQuaternion(0, 0, 0, 1);

    diagonalAxis1_120 = new UnitQuaternion(0.5d, 0.5d, 0.5d, 0.5d);
    diagonalAxis1_240 = new UnitQuaternion(0.5d, -0.5d, -0.5d, -0.5d);
    diagonalAxis2_120 = new UnitQuaternion(0.5d, -0.5d, 0.5d, 0.5d);
    diagonalAxis2_240 = new UnitQuaternion(0.5d, 0.5d, -0.5d, -0.5d);
    diagonalAxis3_120 = new UnitQuaternion(0.5d, -0.5d, -0.5d, 0.5d);
    diagonalAxis3_240 = new UnitQuaternion(0.5d, 0.5d, 0.5d, -0.5d);
    diagonalAxis4_120 = new UnitQuaternion(0.5d, 0.5d, -0.5d, 0.5d);
    diagonalAxis4_240 = new UnitQuaternion(0.5d, -0.5d, 0.5d, -0.5d);
  }

  static {
    sym.subgroupTable.put(Subgroups.Id, Collections.singleton(id));

    // temporal
    Collection<UnitQuaternion> full = new HashSet<UnitQuaternion>();
    full.add(id);
    full.add(diagonalAxis1_120);
    full.add(diagonalAxis1_240);
    full.add(diagonalAxis2_120);
    full.add(diagonalAxis2_240);
    full.add(diagonalAxis3_120);
    full.add(diagonalAxis3_240);
    full.add(diagonalAxis4_120);
    full.add(diagonalAxis4_240);
    full.add(edgeAxis_1);
    full.add(edgeAxis_2);
    full.add(edgeAxis_3);
    sym.subgroupTable.put(Subgroups.Full, full);
  }


  /**
   * Identifiers for the five subgroups of the tetrahedral group. See
   * {@link http://en.wikipedia.org/wiki/Tetrahedral_symmetry#
   * Subgroups_of_chiral_tetrahedral_symmetry} for further information.
   * 
   * @author Alex
   * @see Subgroup
   */
  public enum Subgroups
    implements Subgroup<TetrahedralSymmetry> {
    Id("Trivial group"), Full("Full tetrahedral symmetry");

    private final String name;

    Subgroups(final String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return this.name;
    }

    @Override
    public int order() {
      return sym.order(this);
    }
  }

  /**
   * Objects of this type cannot be constructed directly. Instead, use the
   * singleton instance returned by {@link #get()}.
   */
  protected TetrahedralSymmetry() {
  }

  /**
   * Returns the singleton instance of the {@link TetrahedralSymmetry} class.
   * 
   * @return Singleton instance
   */
  public static TetrahedralSymmetry get() {
    return sym;
  }

  @Override
  public Collection<Point3D> images(final Point3D p,
      final Subgroup<TetrahedralSymmetry> s) {
    Set<Point3D> res = new HashSet<Point3D>(24);

    for (UnitQuaternion q : subgroupTable.get(s)) {
      res.add(p.rotate(q).asPoint3D());
    }

    return res;
  }

  @Override
  public int order(final Subgroup<TetrahedralSymmetry> s) {
    return sym.subgroupTable.get(s).size();
  }

  @Override
  public String getName() {
    return "Tetrahedral Symmetry";
  }

  @Override
  public Subgroup<TetrahedralSymmetry> getSubgroupByName(final String subgroup) {
    return Subgroups.valueOf(subgroup);
  }

  @Override
  public Collection<Subgroup<TetrahedralSymmetry>> getSubgroups() {
    return sym.subgroupTable.keySet();
  }

  @Override
  public Class<Point3D> getType() {
    return Point3D.class;
  }

}
