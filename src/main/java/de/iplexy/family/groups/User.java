package de.iplexy.family.groups;

import de.iplexy.family.enums.Permissions;
import lombok.Getter;

import java.util.*;

public class User {

    private static HashMap<UUID, User> cachedUsers = new HashMap<>();
    @Getter
    private UUID user;
    @Getter
    private Family family;
    @Getter
    private String rank;
    @Getter
    private Integer rankID;

    private List<Permissions> permissions;

    public User(UUID uuid){
        this.user=uuid;
        if(Family.getUsers().containsKey(uuid)) {
            this.family = Family.getUsers().get(uuid);
            this.rankID=this.family.getMembers().get(this.user);
            this.rank = this.family.getRanks().get(this.rankID);
            this.permissions=new ArrayList<>();
            if(this.rankID.equals(14)){
                permissions = Arrays.asList(Permissions.values());
            }
        }
        if (!cachedUsers.containsKey(uuid)){
            cachedUsers.put(uuid,this);
        }
    }

    public boolean hasFamily(){
        return this.family != null;
    }

    public boolean hasPermission(Permissions perm){
        return permissions.contains(perm);
    }

    public static User getUser(UUID uuid){
        if(cachedUsers.containsKey(uuid)) return cachedUsers.get(uuid);
        else return new User(uuid);
    }

}
