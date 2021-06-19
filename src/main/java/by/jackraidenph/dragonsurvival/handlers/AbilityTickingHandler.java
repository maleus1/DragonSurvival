package by.jackraidenph.dragonsurvival.handlers;

import by.jackraidenph.dragonsurvival.abilities.common.BasicDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import com.google.common.collect.Queues;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AbilityTickingHandler {

    private ConcurrentLinkedQueue<IDragonAbility> abilitiesToTick = Queues.newConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<BasicDragonAbility> abilitiesToCoolDown = Queues.newConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<IDragonAbility> abilitiesToFrame = Queues.newConcurrentLinkedQueue();

    public void addToCoolDownList(BasicDragonAbility ability) {
        if (!this.abilitiesToCoolDown.contains(ability))
            this.abilitiesToCoolDown.add(ability);
    }

    public void addToFrameList(BasicDragonAbility ability) {
        if (!this.abilitiesToFrame.contains(ability))
            this.abilitiesToFrame.add(ability);
    }

    public void removeFromCoolDownList(BasicDragonAbility ability) {
        this.abilitiesToCoolDown.remove(ability);
    }

    public void removeFromFrameList(BasicDragonAbility ability) {
        this.abilitiesToFrame.remove(ability);
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

    @SubscribeEvent
    public void frameAbilities(RenderWorldLastEvent e){
        abilitiesToFrame.forEach(x -> x.frame(e.getPartialTicks()));
    }

    private void decreaseCooldownTimer(BasicDragonAbility ability) {
        if (ability.getCooldown() != 0)
            ability.decreaseCooldownTimer();
        else
            this.removeFromCoolDownList(ability);
    }
}
