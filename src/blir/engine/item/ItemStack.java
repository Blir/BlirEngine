package blir.engine.item;

/**
 *
 * @author Blir
 */
public class ItemStack {

    public final int id;

    private int amount;

    public ItemStack(Item item) {
        this(item.id, 1);
    }

    public ItemStack(int id) {
        this(id, 1);
    }

    public ItemStack(Item item, int amount) {
        this(item.id, amount);
    }

    public ItemStack(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void changeAmountBy(int amount) {
        this.amount += amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
