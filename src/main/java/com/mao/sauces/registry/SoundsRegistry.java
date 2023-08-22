package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundsRegistry {
    public static final SoundEvent SPREAD = registerSoundEvents("spread");
    public static final SoundEvent WORK_FINISHED = registerSoundEvents("work_finished");

    private static SoundEvent registerSoundEvents(String id){
        Identifier sound = new Identifier(Sauces.MOD_ID + ":"+ id);
        return Registry.register(Registries.SOUND_EVENT, sound, SoundEvent.of(sound));
    }

    public static void registerModSounds(){
        Sauces.LOGGER.debug("Registering Mod Sounds For" + Sauces.MOD_ID);
    }
}
