package sampleagent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import rescuecore2.messages.Command;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;
import sample.AbstractSampleAgent;
import sample.DistanceSorter;

/**
 * A sample fire brigade agent.
 */
public class SampleFireBrigade extends AbstractSampleAgent<FireBrigade> {

  private static final Logger LOG = Logger.getLogger(SampleFireBrigade.class);
  private Collection<EntityID> unexploredBuildings;

  public SampleFireBrigade(ArrayList<Integer> path) {
	super();
	unexploredBuildings = new HashSet<EntityID>();
	for(Integer id : path) unexploredBuildings.add(new EntityID(id));
  }
  @Override
  public String toString() {
    return "Sample fire brigade";
  }


  @Override
  protected void postConnect() {
    super.postConnect();
    model.indexClass(StandardEntityURN.BUILDING, StandardEntityURN.REFUGE,
        StandardEntityURN.HYDRANT, StandardEntityURN.GAS_STATION);
    LOG.info("Sample fire brigade connected");
    //unexploredBuildings = new HashSet<EntityID>(buildingIDs); 
  }


  @Override
  protected void think(int time, ChangeSet changed, Collection<Command> heard) {
    if (time == config
        .getIntValue(kernel.KernelConstants.IGNORE_AGENT_COMMANDS_KEY)) {
      // Subscribe to channel 1
      sendSubscribe(time, 1);
    }
    for (Command next : heard) {
      LOG.debug("Heard " + next);
    }
    updateUnexploredBuildings(changed);

    // Go through targets (sorted by distance) and check for things we can do
    for (Human next : getTargets()) {
      if (next.getPosition().equals(location().getID())) {
        // Targets in the same place might need rescuing or loading
        if (next.getBuriedness() > 0) {
          // Rescue
          LOG.info("Rescuing " + next);
          sendRescue(time, next.getID());
          return;
        }
      } else {
        // Try to move to the target
        List<EntityID> path = search.breadthFirstSearch(me().getPosition(),
            next.getPosition());
        if (path != null) {
          LOG.info("Moving to target");
          sendMove(time, path);
          return;
        }
      }
    }
    // Nothing to do
    List<EntityID> path = search.breadthFirstSearch(me().getPosition(),
        unexploredBuildings);
    if (path != null) {
      LOG.info("Searching buildings");
      sendMove(time, path);
      return;
    }
    LOG.info("Moving randomly");
    sendMove(time, randomWalk());
  }


  @Override
  protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
    return EnumSet.of(StandardEntityURN.FIRE_BRIGADE);
  }


  private List<Human> getTargets() {
    List<Human> targets = new ArrayList<Human>();
    for (StandardEntity next : model.getEntitiesOfType(
        StandardEntityURN.CIVILIAN, StandardEntityURN.FIRE_BRIGADE,
        StandardEntityURN.POLICE_FORCE, StandardEntityURN.AMBULANCE_TEAM)) {
      Human h = (Human) next;
      if (h == me()) {
        continue;
      }
      if (h.isHPDefined() && h.isBuriednessDefined() && h.isDamageDefined()
          && h.isPositionDefined() && h.getHP() > 0
          && (h.getBuriedness() > 0 || h.getDamage() > 0)) {
        targets.add(h);
      }
    }
    Collections.sort(targets, new DistanceSorter(location(), model));
    return targets;
  }

  private void updateUnexploredBuildings(ChangeSet changed) {
    for (EntityID next : changed.getChangedEntities()) {
      unexploredBuildings.remove(next);
    }
  }
}