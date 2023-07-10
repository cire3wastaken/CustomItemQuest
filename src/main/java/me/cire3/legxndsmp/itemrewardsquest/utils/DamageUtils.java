package me.cire3.legxndsmp.itemrewardsquest.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class DamageUtils {
    public static float damageCalculator(Entity target, Player attacker) {
        if(target instanceof Player) {
            Player playerTarget = (Player) target;
            ItemStack helmet = playerTarget.getInventory().getHelmet();
            ItemStack chestplate = playerTarget.getInventory().getChestplate();
            ItemStack leggings = playerTarget.getInventory().getLeggings();
            ItemStack boots = playerTarget.getInventory().getBoots();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armor.add(helmet);
            armor.add(chestplate);
            armor.add(leggings);
            armor.add(boots);

            int armorPoints = 0;
            for(ItemStack item : armor){
                armorPoints = Math.max(armorPoints, DamageUtils.getArmorPoints(item));
            }

            int amplifier = 0;
            for(PotionEffect effect : playerTarget.getActivePotionEffects()){
                if(effect.getType() == PotionEffectType.DAMAGE_RESISTANCE){
                    amplifier = Math.max(amplifier, effect.getAmplifier());
                }
            }

            int armorToughness = 0;
            for(ItemStack item : armor){
                armorPoints = Math.max(armorPoints, DamageUtils.getArmorToughness(item));
            }

            int epf = 0;

            return DamageUtils.calcDamage(armorPoints,
                    getAttackDamage(attacker.getItemInHand()), armorToughness, epf, amplifier);
        } else {
            return DamageUtils.calcDamage(0, getAttackDamage(attacker.getItemInHand()), 0, 0, 0);
        }
    }

    public static float calcDamage(int armorPoints, float weaponDamage, int armorToughness, int protectionEpf, int resistanceAmplifier){
        return  (weaponDamage * (1 - Math.min(20, Math.max(armorPoints / 5F,
                (armorPoints - weaponDamage)/(2 + armorToughness / 4F)))/25) * (1 - Math.min(protectionEpf, 20) / 25F) *
                (1 - Math.min(resistanceAmplifier, 5) / 5F));
    }

    /**
     * @param item - nullable
     * */
    public static int getArmorToughness(ItemStack item){
        Material material = item == null ? null : item.getType();
        if(material == null) return 0;

        switch (material){
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
                return 8;
            default:
                return 0;
        }
    }

    public static float getAttackDamage(ItemStack itemStack) {
        Material item = itemStack.getType();
        float bonusEnchantDamage = 0;
        boolean hasEnchant = itemStack.getEnchantments().containsKey(Enchantment.DAMAGE_ALL);
        if(hasEnchant){
            bonusEnchantDamage += 1F + (itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL) - 1) * 0.5F;
        }

        if(item.equals(Material.DIAMOND_SWORD)){
            return 7 + bonusEnchantDamage;
        }

        return 1 + bonusEnchantDamage;
    }

    /**
     * @param item - nullable
     * */
    public static int getArmorPoints(ItemStack item) {
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
