package de.iplexy.family.groups;

import lombok.Getter;

import java.util.UUID;

public class User {
    @Getter
    private UUID user;
    @Getter
    private Family family;
    @Getter
    private String rank;
    @Getter
    private Integer rankID;

    public User(UUID uuid){
        this.user=uuid;
        if(Family.getUsers().containsKey(uuid)) {
            this.family = Family.getUsers().get(uuid);
            this.rankID=this.family.getMembers().get(this.user);
            this.rank = this.family.getRanks().get(this.rankID);
        }
    }

    public boolean hasFamily(){
        return this.family != null;
    }

}
