package me.cire3.legxndsmp.itemrewardsquest.command;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.cire3.legxndsmp.itemrewardsquest.Constants;
import net.ess3.api.MaxMoneyException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class LFixCommand implements CommandExecutor {
    private final Set<Player> needConfirm = new HashSet<>();

    @SuppressWarnings(value = {"deprecation"})
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equalsIgnoreCase("lfix")){
            commandSender.sendMessage(Constants.UNKNOWN_COMMAND);
            return true;
        }

        if(!commandSender.hasPermission("itemrewardsquest.updateitems") && !commandSender.isOp()){
            commandSender.sendMessage(Constants.PERMISSION_DENIED);
            return true;
        }

        if(commandSender instanceof Player){
            Player player = ((Player) commandSender);
            if(this.needConfirm.contains(player)){
                if(strings.length == 1 && strings[0].equalsIgnoreCase("confirm")){
                    if(isDamageable(player.getItemInHand())){
                        int count = player.getItemInHand().getEnchantments().keySet().size();
                        int cost = 2000 + count * 900;

                        if(Economy.playerExists(player.getName())){
                            try {
                                if(Economy.hasEnough(player.getName(), cost)){
                                    ItemStack item = player.getItemInHand();
                                    item.setDurability((short) 0);
                                    player.setItemInHand(item);
                                    player.updateInventory();
                                    Economy.subtract(player.getName(), cost);
                                    player.sendMessage(Constants.CHAT_PREFIX +
                                            "Successfully repaired your item for $" + cost + "!");
                                    this.needConfirm.remove(player);
                                } else {
                                    commandSender.sendMessage(Constants.FAIL_PREFIX + "You need $" +
                                            BigDecimal.valueOf(cost).subtract(Economy.getMoneyExact(player.getName()))
                                            + " more to repair this item!");
                                }
                                // none of these exceptions actually get thrown since we have checks
                                // This is just to make compiler happy
                            } catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException ignored) {
                            }
                        }
                    }
                }
            } else {
                if(this.needConfirm.add(player)) {
                    commandSender.sendMessage(Constants.CHAT_PREFIX + "This command will cost $2000 as base, " +
                            "and another $900 for each enchant on this item.");
                    commandSender.sendMessage(ChatColor.YELLOW + "Use /lfix confirm to confirm fixing!");
                } else {
                    commandSender.sendMessage(Constants.FAIL_PREFIX + "Please use /lfix confirm!");
                }
            }
        } else {
            commandSender.sendMessage(Constants.FAIL_PREFIX + "Only players can use this command!");
        }
        return true;
    }

    public static boolean isDamageable(ItemStack itemStack)
    {
        if(itemStack == null) return false;

        switch (itemStack.getType()) {
            case DIAMOND_SWORD:
            case STONE_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
            case DIAMOND_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case STONE_HOE:
            case WOOD_HOE:
            case GOLD_HOE:
            case DIAMOND_HOE:
            case IRON_HOE:
            case DIAMOND_AXE:
            case GOLD_AXE:
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case STONE_SPADE:
            case DIAMOND_SPADE:
            case IRON_SPADE:
            case GOLD_SPADE:
            case WOOD_SPADE:
            case CHAINMAIL_HELMET:
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case LEATHER_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS:
            case LEATHER_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case SHEARS:
            case BOW:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
                return true;
            default:
                return false;
        }
    }
}
