package blir.engine.game;

import blir.engine.BlirEngine;
import blir.engine.gui.GameGUI;
import blir.engine.item.Item;
import blir.engine.slot.Slot;
import blir.engine.slot.SlotType;
import java.util.Collection;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class Game implements Runnable {

    public static final GameOfLife gameOfLife = new GameOfLife();
    
    public static void log(Level level, String msg, Throwable thrown) {
        BlirEngine.LOGGER.log(level, msg, thrown);
    }
    
    public static void log(Level level, String msg, Object... params) {
        BlirEngine.LOGGER.log(level, msg, params);
    }
    
    public final String name;
    
    final GameGUI gui = new GameGUI(this);
    final Map<Integer, SlotType> slotTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    Slot[][] slots;
    int tick;
    
    public Game(String name) {
        this.name = name;
    }

    public abstract void init();
    
    public void registerItemType(Item item) {
        itemTypes.put(item.id, item);
    }
    
    public void registerSlotType(SlotType type) {
        slotTypes.put(type.id, type);
        if (type.spawner != null) {
            slotTypes.put(type.spawner.id, type.spawner);
        }
    }
    
    public Slot getSlotAt(int x, int y) {
        return slots[x][y];
    }
    
    public void setSlotAt(int x, int y, Slot newSlot) {
        slots[x][y] = newSlot;
    }
    
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
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Game ? ((Game) obj).name.equals(this.name) : false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
