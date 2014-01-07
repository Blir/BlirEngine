package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.item.Item;
import blir.engine.item.ItemStack;
import blir.engine.util.Location;

import java.util.*;

import static blir.engine.entity.EntityType.*;

/**
 *
 * @author Blir
 */
public class Human extends MortalEntity {

    Map<Integer, ItemStack> inventory = new HashMap<>();

    public Human() {
        super(human.id, 20);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        if (filterByID(game.getSquareNeighbors(x, y, 1), juggernaut.id).isEmpty()) {
            Location loc = game.getFirstSquareNeighborLocation(x, y, 2, zombie.id);
            game.moveEntity(x, y, loc == null ? Location.wander(x, y, 1) : Location.away(x, y, loc, 2));
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        List<Entity> zombies = filterByID(game.getSquareNeighbors(x, y, 1), zombie.id);
        for (Entity entity : zombies) {
            if (((MortalEntity) entity).damage(3)) {
                human.damageDealt += 3;
                human.kills++;
                zombie.deaths++;
            }
        }
    }

    public ItemStack getItemStack(Item item) {
        return inventory.get(item.id);
    }

    public ItemStack getItemStack(int id) {
        return inventory.get(id);
    }

    public Collection<ItemStack> getInventory() {
        return inventory.values();
    }

    public ItemStack removeItemStack(Item item) {
        return inventory.remove(item.id);
    }

    public ItemStack removeItemStack(int id) {
        return inventory.remove(id);
    }

    public ItemStack addItemStack(Item item) {
        return inventory.put(item.id, new ItemStack(item));
    }

    public ItemStack addItemStack(int id) {
        return inventory.put(id, new ItemStack(id));
    }

    public ItemStack addItemStack(Item item, int amount) {
        return inventory.put(item.id, new ItemStack(item, amount));
    }

    public ItemStack addItemStack(int id, int amount) {
        return inventory.put(id, new ItemStack(id, amount));
    }

    public ItemStack addItemStack(ItemStack is) {
        return inventory.put(is.id, is);
    }
}
