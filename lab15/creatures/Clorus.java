package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates clorus with energy equal to e. */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /** Cloruses lose 0.03 units of energy when move. */
    @Override
    public void move() {
        energy -= 0.03;
    }

    /** Cloruses gain all the energy of the attacked creature. */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     *  lost to the process.
     * @return a baby Clorus
     */
    @Override
    public Clorus replicate() {
        energy *= 0.5;
        double babyEnergy = energy;
        return new Clorus(babyEnergy);
    }

    /** Cloruses lose 0.01 units of energy when move. */
    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() > 0){
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        } else {
            return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(empties));
        }
    }


    @Override
    public Color color() {
        return new Color(r, g, b);
    }
}
