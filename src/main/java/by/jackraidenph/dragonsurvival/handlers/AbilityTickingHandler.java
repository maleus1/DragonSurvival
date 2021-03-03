package by.jackraidenph.dragonsurvival.handlers;

import by.jackraidenph.dragonsurvival.abilities.common.BasicDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import com.google.common.collect.Queues;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AbilityTickingHandler {

    private ConcurrentLinkedQueue<IDragonAbility> abilitiesToTick = Queues.newConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<BasicDragonAbility> abilitiesToCoolDown = Queues.newConcurrentLinkedQueue();

    public void addToCoolDownList(BasicDragonAbility ability) {
        if (!this.abilitiesToCoolDown.contains(ability))
            this.abilitiesToCoolDown.add(ability);
    }

    public void removeFromCoolDownList(BasicDragonAbility ability) {
        this.abilitiesToCoolDown.remove(ability);
    }

    public void addToTickList(IDragonAbility ability) {
        if (!this.abilitiesToTick.contains(ability))
            this.abilitiesToTick.add(ability);
    }

    public void removeFromTickList(IDragonAbility ability) {
        this.abilitiesToTick.remove(ability);
    }

    @SubscribeEvent
    public void tickAbilities(TickEvent.WorldTickEvent e) {
        abilitiesToTick.forEach(IDragonAbility::tick);
        abilitiesToCoolDown.forEach(this::decreaseCooldownTimer);
    }

    private void decreaseCooldownTimer(BasicDragonAbility ability) {
        if (ability.getCooldown() != 0)
            ability.decreaseCooldownTimer();
        else
            this.removeFromCoolDownList(ability);
    }
}
