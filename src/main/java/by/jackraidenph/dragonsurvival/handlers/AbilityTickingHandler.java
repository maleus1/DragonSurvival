package by.jackraidenph.dragonsurvival.handlers;

import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class AbilityTickingHandler {

    private ArrayList<IDragonAbility> abilitiesToTick = new ArrayList<>();

    public void addToTickList(IDragonAbility ability) {
        this.abilitiesToTick.add(ability);
    }

    public void removeFromTickList(IDragonAbility ability) {
        this.abilitiesToTick.remove(ability);
    }

    @SubscribeEvent
    public void tickAbilities(TickEvent.WorldTickEvent e) {
        for (IDragonAbility ability : this.abilitiesToTick) {
            ability.tick();
        }
    }
}
