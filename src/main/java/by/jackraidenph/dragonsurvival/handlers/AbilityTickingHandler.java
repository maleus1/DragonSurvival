package by.jackraidenph.dragonsurvival.handlers;

import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class AbilityTickingHandler {

    private ArrayList<IDragonAbility> abilitiesToTick = new ArrayList<>();
    private ArrayList<IDragonAbility> pendingToRemoveList = new ArrayList<>();
    private ArrayList<IDragonAbility> pendingToAddList = new ArrayList<>();

    public void addToTickList(IDragonAbility ability) {
        if (!this.pendingToAddList.contains(ability) && !this.abilitiesToTick.contains(ability))
            this.pendingToAddList.add(ability);
    }

    public void removeFromTickList(IDragonAbility ability) {
        this.pendingToRemoveList.remove(ability);
    }

    @SubscribeEvent
    public void tickAbilities(TickEvent.WorldTickEvent e) {
        //NECESSARY TO EXCLUDE CHANCES OF ConcurrentModificationException
        abilitiesToTick.addAll(pendingToAddList);
        pendingToRemoveList.forEach(abilitiesToTick::remove);
        pendingToRemoveList.clear();
        pendingToAddList.clear();

        abilitiesToTick.forEach(IDragonAbility::tick);
    }
}
