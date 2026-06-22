package com.habioutube.momentumrunner.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.callback.CallbackInfo;
import net.minecraft.client.network.ClientPlayerEntity;
import com.habioutube.momentumrunner.util.MomentumState;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (MomentumState.lastToggleMessage != null && player.networkHandler != null) {
            player.sendMessage(MomentumState.lastToggleMessage, false);
            MomentumState.lastToggleMessage = null;
        }
    }
}
