package me.aifusp.clockin;

public enum InventoryName {

    BOSS_MENU("Boss Menu", 9),
    BOSS_WORKERS("Workers",45),
    BOSS_WORKERS_HOURS("Worker Hours",45),
    WORKER_MENU("Worker Menu",9),
    CHECKIN("Check-In",9),
    REGISTRED_HOURS("Registred Hours",45);

    private final String menuName;
    private final int size;

    private InventoryName(String name, int size) {
        this.menuName = name;
        this.size = size;
    }

    public String getName() {
        return this.menuName;
    }

    public int getSize() {
        return this.size;
    }

}