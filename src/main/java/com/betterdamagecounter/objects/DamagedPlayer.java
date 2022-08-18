package com.betterdamagecounter.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DamagedPlayer { //TODO This is something I really want is to collect the damage you take, and record the type of damage and from what attack
    String playerName;
    Integer damageTaken = 0;
}
