package blir.engine.game;

import blir.engine.gui.GameGUI;
import blir.engine.item.Item;
import blir.engine.slot.Slot;
import blir.engine.slot.SlotType;
import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Travis
 */
public abstract class Game implements Runnable {

    final GameGUI gui = new GameGUI(this);
    final Map<Integer, SlotType> slotTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    Slot[][] slots;
    int tick;

    public abstract void init();
    
    public SlotType getSlotTypeForID(int id) {
        return slotTypes.get(id);
    }
    
    public Item getItemForID(int id) {
        return itemTypes.get(id);
    }
    
    public Collection<SlotType> getSlotTypes() {
        return slotTypes.values();
    }
    
    public Collection<Item> getItemTypes() {
        return itemTypes.values();
    }
}
