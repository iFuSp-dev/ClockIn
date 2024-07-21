package me.aifusp.clockin.gui.menu.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.InventoryName;
import me.aifusp.clockin.gui.menu.boss.Boss_Workers_Hours;
import me.aifusp.clockin.gui.menu.boss.Boss_Workers_List;
import me.aifusp.clockin.gui.menu.workers.CheckIn;
import me.aifusp.clockin.gui.menu.workers.Worker_Registred_Hours;
import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ClickGui implements Listener {
    private final Main main;
    public ClickGui(Main plugin){this.main = plugin;}
    private Map<Player, String> pendingConfirm = new HashMap<>();


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        String title = e.getView().getTitle();
        if (!title.equals(InventoryName.CHECKIN.getName())
                && !title.equals(InventoryName.BOSS_MENU.getName())
                && !title.equals(InventoryName.BOSS_WORKERS.getName())
                && !title.equals(InventoryName.WORKER_MENU.getName())
                && !title.equals(InventoryName.BOSS_WORKERS_HOURS.getName())
                && !title.equals(InventoryName.REGISTRED_HOURS.getName()))
            return;
        e.setCancelled(true);
        for (Integer slot : e.getRawSlots()){
            if (slot < e.getInventory().getSize()){
                return;
            }
        }
        e.setCancelled(false);
    }





    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        String title = e.getView().getTitle();

        if (!title.equals(InventoryName.CHECKIN.getName())
                && !title.equals(InventoryName.BOSS_MENU.getName())
                && !title.equals(InventoryName.BOSS_WORKERS.getName())
                && !title.equals(InventoryName.WORKER_MENU.getName())
                && !title.equals(InventoryName.BOSS_WORKERS_HOURS.getName())
                && !title.equals(InventoryName.REGISTRED_HOURS.getName()))
            return;
        e.setCancelled(true);
        Player staff = (Player) e.getWhoClicked();
        ItemStack icon = e.getCurrentItem();

        if (title.equals(InventoryName.CHECKIN.getName())) {
            checkIn(e,staff,icon);
        }
        if (title.equals(InventoryName.WORKER_MENU.getName())) {
            workerMenu(e,staff,icon);
        }
        if (title.equals(InventoryName.REGISTRED_HOURS.getName())) {
            registredHours(e,staff,icon);
        }
        if (title.equals(InventoryName.BOSS_MENU.getName())) {
            bossMenu(e,staff,icon);
        }
        if (title.equals(InventoryName.BOSS_WORKERS.getName())) {
            bossWorkers(e,staff,icon);
        }
    }
    private void bossWorkers(InventoryClickEvent e, Player staff, ItemStack icon) {
        if (icon != null || icon.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(icon);
            if (nbtItem.getBoolean("worker")) {
                String job = nbtItem.getString("Job");
                String worker = nbtItem.getString("Worker");
                Boss_Workers_Hours bossWorkersHours = new Boss_Workers_Hours(staff,worker,job,0);
                staff.openInventory(bossWorkersHours.getInventory());
            }
        }

    }
    private void bossMenu(InventoryClickEvent e, Player staff, ItemStack icon) {
        if (icon != null || icon.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(icon);

            if (nbtItem.getBoolean("workers")) {
                String job = nbtItem.getString("Job");
                Boss_Workers_List bossWorkersList = new Boss_Workers_List(staff,job);
                staff.openInventory(bossWorkersList.getInventory());
            }

            if (nbtItem.getBoolean("deleteFiles")) {
                String job = nbtItem.getString("Job");
                pendingConfirm.put(staff,job);
                staff.sendMessage("Are you sure to delete all the files?");
                staff.sendMessage("Type: Yes");
                staff.closeInventory();
            }
        }
    }

    private void registredHours(InventoryClickEvent e, Player staff, ItemStack icon) {
        if (icon != null || icon.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(icon);

            if (nbtItem.getBoolean("next")) {
                String job = nbtItem.getString("Job");
                int page = nbtItem.getInteger("Page");
                File playerfolder = new File(main.getDataFolder() + "/saves/"+job, staff.getName());
                File[] files = playerfolder.listFiles();

                int filesCount = files.length;
                if (filesCount  > 36 * (page + 1)) {
                    Worker_Registred_Hours workerRegistredHours = new Worker_Registred_Hours(staff,job,page+1);
                    staff.openInventory(workerRegistredHours.getInventory());
                }else {
                    Worker_Registred_Hours workerRegistredHours = new Worker_Registred_Hours(staff,job,page);
                    staff.openInventory(workerRegistredHours.getInventory());
                }



            }
            if (nbtItem.getBoolean("prev")) {
                String job = nbtItem.getString("Job");
                int page = nbtItem.getInteger("Page");
                File playerfolder = new File(main.getDataFolder() + "/saves/"+job, staff.getName());
                long filessize = playerfolder.length();
                if(filessize > 36L *(page-1)){
                    if (page - 1 < 0){
                        Worker_Registred_Hours workerRegistredHours = new Worker_Registred_Hours(staff,job,page);
                        staff.openInventory(workerRegistredHours.getInventory());
                    }else {
                        Worker_Registred_Hours workerRegistredHours = new Worker_Registred_Hours(staff,job,page-1);
                        staff.openInventory(workerRegistredHours.getInventory());
                    }
                }
            }
        }
    }





    private void workerMenu(InventoryClickEvent e, Player staff, ItemStack icon){
        if (icon != null || icon.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(icon);

            if (nbtItem.getBoolean("registry")){
                String job = nbtItem.getString("Job");
                Worker_Registred_Hours workerRegistredHours = new Worker_Registred_Hours(staff,job,0);
                staff.openInventory(workerRegistredHours.getInventory());
            }
            if (nbtItem.getBoolean("checkIn")){
                String job = nbtItem.getString("Job");
                CheckIn checkIn = new CheckIn(staff,job);
                staff.openInventory(checkIn.getInventory());
            }

        }

    }

    private void checkIn(InventoryClickEvent e, Player staff, ItemStack icon){
        if (icon != null || icon.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(icon);

            if (nbtItem.getBoolean("check")) {
                String job = nbtItem.getString("Job");

                File file = new File(main.getDataFolder() + "/temp", staff.getName() + "_" + job + ".yml");

                if (!file.exists()) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    config.set("Name", staff.getName());
                    config.set("Join", getCurrentTimeAndDate());
                    config.set("Job", job);

                    try {
                        config.save(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"lp user "+staff.getName()+" parent set "+job);
                CheckIn checkIn = new CheckIn(staff, job);
                staff.openInventory(checkIn.getInventory());
            }


            if (nbtItem.getBoolean("uncheck")) {
                String job = nbtItem.getString("Job");
                File file = new File(main.getDataFolder() + "/temp", staff.getName() + "_" + job + ".yml");

                if (file.exists()) {

                    File playerfolder = new File(main.getDataFolder() + "/saves/"+job, staff.getName());

                    if (!playerfolder.exists()){playerfolder.mkdirs();}


                    int date = playerfolder.list().length;


                    File newfile = new File(playerfolder, String.valueOf(date)+".yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String joinDate = config.getString("Join");
                    String leaveDate = getCurrentTimeAndDate();
                    String totalTime = calculateElapsedTime(joinDate,leaveDate);



                    FileConfiguration newconfig = YamlConfiguration.loadConfiguration(newfile);
                    newconfig.set("Name",staff.getName());
                    newconfig.set("Join",joinDate);
                    newconfig.set("Leave",leaveDate);
                    newconfig.set("Job",job);
                    newconfig.set("TotalTime",totalTime);

                    try {
                        newconfig.save(newfile); // Guardamos el nuevo archivo YAML
                        file.delete(); // Aquí eliminamos el archivo

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    CheckIn checkIn = new CheckIn(staff, job);
                    staff.openInventory(checkIn.getInventory());

                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"lp user "+staff.getName()+" parent set default");

                }
            }
        }


    }



    private String getCurrentTimeAndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date now = new Date();
        return sdf.format(now);
    }
    public static String calculateElapsedTime(String startDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            long timeDifferenceInMillis = endDate.getTime() - startDate.getTime();

            long seconds = timeDifferenceInMillis / 1000 % 60;
            long minutes = timeDifferenceInMillis / (1000 * 60) % 60;
            long hours = timeDifferenceInMillis / (1000 * 60 * 60) % 24;
            long days = timeDifferenceInMillis / (1000 * 60 * 60 * 24);

            return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null; // En caso de error, devuelve null
    }











    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if (pendingConfirm.containsKey(player)){
         String job = pendingConfirm.get(player);
         String message = e.getMessage();

         if (message.equalsIgnoreCase("Yes")){
             File file = new File(main.getDataFolder() + "/saves", job);
             deleteDirectory(file);
             player.sendMessage(" ");
             player.sendMessage("All files has been deleted!");
             player.sendMessage(" ");

         }else {
             Bukkit.getConsoleSender().sendMessage("All files of the Job: "+ job+" has been deleted");
         }


         e.setCancelled(true);
         pendingConfirm.remove(player);
        }
    }








    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Llamada recursiva para eliminar subdirectorios
                    } else {
                        file.delete(); // Eliminar archivo
                    }
                }
            }
            directory.delete(); // Eliminar directorio vacío
        }
    }

}
