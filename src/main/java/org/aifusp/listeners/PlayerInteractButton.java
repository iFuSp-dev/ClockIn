package org.aifusp.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import me.aifusp.clockin.gui.menu.boss.Boss_Menu;
import me.aifusp.clockin.gui.menu.workers.Worker_Menu;
import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractButton implements Listener {
    private final Main main;
    public PlayerInteractButton(Main plugin){this.main = plugin;}
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null){return;}
        NBTBlock nbtBlock = new NBTBlock(clickedBlock);
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK ){        //Right click? for buttons
            if (clickedBlock.getType() == Material.POLISHED_BLACKSTONE_BUTTON && nbtBlock.getData().getBoolean("clockIn")) {    //cheing Material and NBT
                String job = nbtBlock.getData().getString("job");   //Saving the job of the Button

                if (nbtBlock.getData().getBoolean("boss")) {        //Checking if Boss Button
                    if (player.hasPermission("job."+job+"Boss")){       //Checking if Boss Permission
                        player.openInventory(new Boss_Menu (player,job).getInventory());
                    }
                }else{
                    if (player.hasPermission("job."+job)){          //Checking Job Permission
                        player.openInventory(new Worker_Menu(player,job).getInventory());
                    }
                }
            }
        }
    }
}
