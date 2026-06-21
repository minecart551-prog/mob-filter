package net.pcal.mobfilter.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.pcal.mobfilter.MFService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

/**
 * Intercept calls to EntityType.spawn so we can try to track MobSpawnType for later filtering.
 */
@SuppressWarnings("ALL")
@Mixin(EntityType.class)
public abstract class EntityTypeMixin {

    @Inject(at = @At("HEAD"), method = "spawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;Ljava/util/function/Consumer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;")
    private void mf_spawn(ServerLevel serverLevel,
                          CompoundTag compoundTag,
                          Consumer<?> consumer,
                          BlockPos blockPos,
                          MobSpawnType mobSpawnType,
                          boolean b1,
                          boolean b2,
                          CallbackInfoReturnable<Entity> cir) {
        MFService.getInstance().notifyEntityCreate(mobSpawnType);
    }
}