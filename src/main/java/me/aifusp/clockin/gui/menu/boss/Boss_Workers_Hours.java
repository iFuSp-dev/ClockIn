package me.aifusp.clockin.gui.menu.boss;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.Buttons;
import me.aifusp.clockin.InventoryName;
import org.aifusp.Main;
import org.aifusp.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Boss_Workers_Hours {

    private Player player;

    private String worker;
    private String job;
    private Inventory inventory;
    private Buttons buttons;
    private int page;
    private Main main = Main.getInstance();


    public Boss_Workers_Hours(Player player,String worker,String job,int page){
        this.player = player;
        this.worker = worker;
        this.job = job;
        this.buttons = new Buttons();
        this.page = page;


        createInventory();



    }
    private void createInventory(){

        inventory = Bukkit.createInventory(player, InventoryName.REGISTRED_HOURS.getSize(), InventoryName.REGISTRED_HOURS.getName());


        Bukkit.getConsoleSender().sendMessage(String.valueOf(page));
        File playerfolder = new File(main.getDataFolder() + "/saves/"+job, worker);

        String totalTime;



        inventory.setItem(44,jobTag(jobPage(buttons.NextPage())));
        inventory.setItem(36,jobTag(jobPage(buttons.PrevPage())));



        if (playerfolder.exists()){
            File[] files = playerfolder.listFiles();
            int index = 0+(36*page);
            if (files.length > 0){
                Bukkit.getConsoleSender().sendMessage(String.valueOf(index));
                int pos = 0;
                long totalSeconds = 0;
                for (int i = index; i < (1 + page) * 36 && i < files.length; i++) {

                    Bukkit.getConsoleSender().sendMessage(String.valueOf(i));
                    File sfile = files[i];
                    FileConfiguration config = YamlConfiguration.loadConfiguration(sfile);
                    String name = config.getString("Name");
                    String join = config.getString("Join");
                    String leave = config.getString("Leave");
                    String total = config.getString("TotalTime");



                    ItemStack item = new ItemStack(Material.MAP);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Entry# "+String.valueOf(i));
                    List<String> lore = new ArrayList<>();
                    lore.add("#d86facWorker: &7&o"+ name);
                    lore.add("#d86facJoin: &7&o"+join);
                    lore.add("#d86facLeave: "+leave);
                    lore.add("  ");
                    lore.add("&b&lTotal: &7&o"+ total);
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    inventory.setItem(pos,item);
                    long seconds = parseTotalTimeToSeconds(total);
                    totalSeconds += seconds;
                    pos++;
                }
                totalTime = formatSecondsToTotalTime(totalSeconds);

                ItemStack item = new ItemStack(Material.MAP);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("&b&lTotal Time: &7&o"+String.valueOf(index));
                List<String> lore = new ArrayList<>();
                lore.add(MessageUtils.getColoredMessage("#d44e9c&l&nTotal Hours: "+ totalTime));
                meta.setLore(lore);
                item.setItemMeta(meta);
                inventory.setItem(40,item);
            }
        }


    }


    private ItemStack jobPage(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger("Page",page);
        nbtItem.applyNBT(item);
        return item;
    }
    private ItemStack jobTag(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("Job",this.job);
        nbtItem.applyNBT(item);

        return item;
    }
    private long parseTotalTimeToSeconds(String totalTime) {
        String[] parts = totalTime.split(", ");

        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        for (String part : parts) {
            String[] timeUnit = part.split(" ");
            if (timeUnit[1].equals("days")) {
                days = Long.parseLong(timeUnit[0]);
            } else if (timeUnit[1].equals("hours")) {
                hours = Long.parseLong(timeUnit[0]);
            } else if (timeUnit[1].equals("minutes")) {
                minutes = Long.parseLong(timeUnit[0]);
            } else if (timeUnit[1].equals("seconds")) {
                seconds = Long.parseLong(timeUnit[0]);
            }
        }

        // Convertir días, horas, minutos y segundos a segundos
        return days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds;
    }

    // Función para convertir los segundos a un formato de tiempo legible
    private String formatSecondsToTotalTime(long totalSeconds) {
        long days = totalSeconds / (24 * 60 * 60);
        long remainingSeconds = totalSeconds % (24 * 60 * 60);
        long hours = remainingSeconds / (60 * 60);
        remainingSeconds %= (60 * 60);
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;

        return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }
    public Inventory getInventory() {
        return this.inventory;
    }

}
