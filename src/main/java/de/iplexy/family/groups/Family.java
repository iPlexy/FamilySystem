package de.iplexy.family.groups;

import de.iplexy.family.utils.FamilySerialzier;
import de.iplexy.family.utils.RandomString;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static de.iplexy.family.FamilySystem.getPlugin;

public class Family {
    private static HashMap<String, Family> cachedFamilies = new HashMap<>(); //uid,family
    private static HashMap<UUID, Family> users = new HashMap<>();
    @Getter
    private String uid;
    @Getter
    private String name;
    @Getter
    private UUID owner;
    @Getter
    private HashMap<UUID, Integer> members = new HashMap<>(); //UUID, RankID
    @Getter
    private HashMap<Integer, String> ranks = new HashMap<>(); //RankID, RankName
    private Location base;

    public Family() {
        updateUsers();
    }

    public Family(String name, Player owner) {
        this.name = name;
        this.owner = owner.getUniqueId();
        this.members = new HashMap<>();
        this.ranks = new HashMap<>();
        this.base=null;
        this.uid=newID();
        updateUsers();
    }

    public String[] getInfo() {
        return new String[]{"Uid: "+this.uid,"Name: " + this.name, "Owner: " + this.owner, "Members: " + this.members, "Ranks: " + this.ranks, "Base: " + this.base};
    }

    private String newID(){
        String uid = RandomString.get(16);
        File file = new File(getPlugin().getDataFolder()+"/families/",uid+".json");
        while (file.exists()){
            uid = RandomString.get(16);
            file = new File(getPlugin().getDataFolder()+"/families/",uid+".json");
        }
        return uid;
    }

    private void updateUsers(){
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            users.put(this.owner, this);
            this.members.put(this.owner,14);
            if(!this.ranks.containsKey(14)) this.ranks.put(14,"Owner");
            if(!this.ranks.containsKey(1)) this.ranks.put(1,"Member");
            for(UUID uuid :this.getMembers().keySet()){
                users.put(uuid,this);
            }
        }, 20L);
    }

    public void cacheFamily(){
        if(!cachedFamilies.containsKey(this.uid)){
            cachedFamilies.put(this.uid,this);
        }else{
            Bukkit.getConsoleSender().sendMessage("§4Error Family.java");
        }
    }

    public boolean createRank(String name,Integer id){
        if(this.ranks.containsKey(id)) return false;
        this.ranks.put(id,name);
        return true;
    }







    public static HashMap<String, Family> getCachedFamilies(){
        return cachedFamilies;
    }

    public static void loadAllFamilies(){
        File folder = new File(getPlugin().getDataFolder()+"/families/");
        for(File f : folder.listFiles()){
            FamilySerialzier.deserialize(f);
        }
    }

    public static void saveAllFamilies(){
        for(Family family : Family.getCachedFamilies().values()) {
            FamilySerialzier.serialize(family);
        }
    }

    public static boolean hasFamily(OfflinePlayer p){
        List<UUID> uuidList = new ArrayList<>();
        for(Family family : getCachedFamilies().values()){
            uuidList.add(family.owner);
            for(UUID uuid : family.getMembers().keySet()){
                uuidList.add(uuid);
            }
        }
        return uuidList.contains(p.getUniqueId());
    }

    public static HashMap<UUID, Family> getUsers(){
        return users;
    }

}
