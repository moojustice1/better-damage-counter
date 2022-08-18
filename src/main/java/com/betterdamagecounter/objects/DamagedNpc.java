package com.betterdamagecounter.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DamagedNpc {
    private String name;
    private Integer damageDone=0;
    private Integer uniqueNpcId;
    private Integer maxHealth; //TODO: this might not be possible

    //TODO: we may be able to "fake" a percentage of the damage you dealt depending on how the npc.getMaxRatio stuff works

    public void addDamage(int damage){
        this.damageDone+=damage;
    }
}
