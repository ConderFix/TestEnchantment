package barbary.testproject;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class TestEnchantment extends EnchantmentWrapper {
    public TestEnchantment() {
        super("test");
        fix(this);
    }

    public boolean canEnchantItem(ItemStack item) {
        Material itemType = item.getType();
        return itemType == Material.NETHERITE_PICKAXE
                || itemType == Material.DIAMOND_PICKAXE;
    }

    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    public int getMaxLevel() {
        return 1;
    }

    public int getStartLevel() {
        return 1;
    }

    public boolean isTreasure() {
        return false;
    }

    public boolean isCursed() {
        return false;
    }

    public String getName() {
        return "test";
    }

    private void fix(TestEnchantment ech) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(ech);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
