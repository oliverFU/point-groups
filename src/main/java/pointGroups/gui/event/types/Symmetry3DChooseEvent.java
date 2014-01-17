package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Symmetry.Subgroup;
import pointGroups.gui.event.Event;


/**
 * This event is supposed to be fired, when the current symmetry was changed to
 * another {@link Symmetry} of {@link Point3D} type. The chosen symmetry can be
 * retrieved by {@link #getSymmetry3D()}.
 * 
 * @author Marcel, Alex
 */
public class Symmetry3DChooseEvent
  extends Event<Symmetry3DChooseHandler>
{
  protected final Symmetry<Point3D, ?> symmetry;
  protected final String subgroup;
  /**
   * The type of the corresponding {@link Symmetry3DChooseHandler}.
   */
  public final static Class<Symmetry3DChooseHandler> TYPE =
      Symmetry3DChooseHandler.class;

  /**
   * Creates a new {@link Symmetry3DChooseEvent} with given {@link Symmetry}.
   * 
   * @param symmetry The symmtry chosen
   */
  public Symmetry3DChooseEvent(final Symmetry<Point3D, ?> symmetry,
      final String subgroup) {
    this.symmetry = symmetry;
    this.subgroup = subgroup;
  }

  /**
   * The chosen {@link Symmetry} that caused this event to be fired.
   * 
   * @return The chosen symmetry.
   */
  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetry;
  }

  public Symmetry.Subgroup<Symmetry<Point3D, ?>> getSubgroup3D() {
    return (Subgroup<Symmetry<Point3D, ?>>) symmetry.getSubgroupByName(this.subgroup);
  }

  @Override
  public final Class<Symmetry3DChooseHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Symmetry3DChooseHandler handler) {
    handler.onSymmetry3DChooseEvent(this);
  }
}
