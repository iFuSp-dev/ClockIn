package me.aifusp.clockin;

import de.tr7zw.nbtapi.NBTItem;
import org.aifusp.utils.MessageUtils;
import org.aifusp.utils.SkullUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public ItemStack Back(){
        ItemStack book = new ItemStack(Material.BOOK);
        NBTItem nbtItem = new NBTItem(book);
        nbtItem.setBoolean("Back",true);
        nbtItem.applyNBT(book);
        ItemMeta itemMeta = book.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.getColoredMessage("#2F829D&l&oBack"));
        book.setItemMeta(itemMeta);
        return book;
    }
    public ItemStack DevelperHead(){
        String textureId = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE0MzMyMzBhYjlmZGNhMDE5ZTQ4MTIwYjBhMzZlN2ZlODE4N2VkZWRhZDVmNGYwZTc3ODNhNjgwYThmYzRmZSJ9fX0=";
        ItemStack playerHead = SkullUtils.getPlayerHead(textureId);

        ItemMeta meta = playerHead.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("#0FF6AC&nDeveloper:&f #F50087&naifusp"));
        lore.add(MessageUtils.getColoredMessage("&f&o&nCheck &f&o&n&laifusp´s &f&o&nplugins"));
        meta.setLore(lore);
        meta.setDisplayName(" ");
        playerHead.setItemMeta(meta);

        return playerHead;
    }



    public ItemStack NextPage(){
        ItemStack itemStack = new ItemStack(Material.ARROW);
        NBTItem  nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("CustomModelData",1);
        nbtItem.setBoolean("next",true);
        nbtItem.setInteger("Page",0);

        nbtItem.applyNBT(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&7&lNext Page"));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public ItemStack PrevPage(){
        ItemStack itemStack = new ItemStack(Material.STICK);
        NBTItem  nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("CustomModelData",1);
        nbtItem.setBoolean("prev",true);
        nbtItem.applyNBT(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        nbtItem.setInteger("Page",0);
        meta.setDisplayName(MessageUtils.getColoredMessage("&7&lPrevious  Page"));

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack Check(){
        ItemStack itemStack = new ItemStack(Material.LIME_DYE);
        NBTItem  nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("CustomModelData",1);
        nbtItem.setBoolean("check",true);
        nbtItem.applyNBT(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("#65d267&l&nCheck-In"));

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack UnCheck(){
        ItemStack itemStack = new ItemStack(Material.RED_DYE);
        NBTItem  nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("CustomModelData",1);
        nbtItem.setBoolean("uncheck",true);
        nbtItem.applyNBT(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("&4&l&nCheckOut"));

        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public void CreatePages(Inventory GUI){
        ItemStack itemStack = new ItemStack(Material.MAP);

        for (int i = 1; i<9; i++){
            itemStack.setAmount(i);

            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setInteger("Page",i);
            nbtItem.applyNBT(itemStack);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(MessageUtils.getColoredMessage("#F9F33CPage "+i));
            itemStack.setItemMeta(meta);

            GUI.setItem(36+i,itemStack.clone());
        }
    }


    public ItemStack DisplayNameEditor(){
        ItemStack itemStack = new ItemStack(Material.CLOCK);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&oDisplayName"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#2dedfb&n&lDisplay#2dfb45&n&lName"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_DisplayName",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }

    public ItemStack FeedEditor(){
        ItemStack itemStack = new ItemStack(Material.COOKED_CHICKEN);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&fFeed Amount"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fb9b2d&l&nFeed#1fffc7 Amount"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_Feed",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }


    public ItemMeta getMeta(ItemStack item){
        return item.getItemMeta();
    }

}
