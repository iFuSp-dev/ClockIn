package org.aifusp.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import de.tr7zw.nbtapi.NBTItem;
import org.aifusp.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPlaceButton implements Listener {

    private final Main main;
    public PlayerPlaceButton(Main plugin){this.main = plugin;}


    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItemInHand();
        NBTItem nbtItem = new NBTItem(item);

        if (!player.hasPermission("clockIn.admin")){player.sendMessage("No tienes permisos para user esto. Contacta con un administrador para devolverlo!"); return;}

        if (nbtItem.getBoolean("clockIn")&& item.getType() == Material.POLISHED_BLACKSTONE_BUTTON){
            Block block = e.getBlock();
            NBTBlock nbtBlock = new NBTBlock(block);

            //Giving NBT's from item to block
            nbtBlock.getData().setBoolean("clockIn",true);
            nbtBlock.getData().setBoolean("boss",(nbtItem.getBoolean("boss")));
            nbtBlock.getData().setString("job",nbtItem.getString("job"));
        }


    }
}
