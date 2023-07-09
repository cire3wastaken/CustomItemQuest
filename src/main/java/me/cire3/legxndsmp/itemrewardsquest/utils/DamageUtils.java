package me.cire3.legxndsmp.itemrewardsquest.utils;

import com.sun.istack.internal.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DamageUtils {
    public static float calcDamage(int armorPoints, float weaponDamage, int armorToughness, int protectionEpf, int resistanceAmplifier){
        return  (weaponDamage * (1 - Math.min(20, Math.max(armorPoints / 5F,
                (armorPoints - weaponDamage)/(2 + armorToughness / 4F)))/25) * (1 - Math.min(protectionEpf, 20) / 25F) *
                (1 - Math.min(resistanceAmplifier, 5) / 5F));
    }

    public static int getArmorPoints(@Nullable ItemStack item) {
        Material material = item == null ? null : item.getType();
        if(material == null) return 0;

        switch (material){
            case DIAMOND_HELMET:
            case DIAMOND_BOOTS:
            case GOLD_LEGGINGS:
            case LEATHER_CHESTPLATE:
                return 3;
            case DIAMOND_CHESTPLATE:
                return 8;
            case DIAMOND_LEGGINGS:
            case IRON_CHESTPLATE:
                return 6;
            case IRON_HELMET:
            case IRON_BOOTS:
            case CHAINMAIL_HELMET:
            case GOLD_HELMET:
            case LEATHER_LEGGINGS:
                return 2;
            case IRON_LEGGINGS:
            case CHAINMAIL_CHESTPLATE:
            case GOLD_CHESTPLATE:
                return 5;
            case CHAINMAIL_BOOTS:
            case GOLD_BOOTS:
            case LEATHER_HELMET:
            case LEATHER_BOOTS:
                return 1;
            case CHAINMAIL_LEGGINGS:
                return 4;
            default:
                return 0;
        }
    }
}
