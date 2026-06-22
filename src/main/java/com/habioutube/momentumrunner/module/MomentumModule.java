package com.habioutube.momentumrunner.module;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import com.habioutube.momentumrunner.util.MomentumState;

public class MomentumModule {
    private boolean enabled = false;
    private final MomentumState state = new MomentumState();

    public void toggle() {
        enabled = !enabled;
        state.reset();
        
        if (enabled) {
            state.setMomentum(0.5); // Start with Soul Speed III equivalent
        }
        
        notifyToggle();
    }

    public void tick(ClientPlayerEntity player) {
        if (!enabled) {
            return;
        }

        // Check if player is on ground
        boolean wasOnGround = state.wasOnGround();
        boolean isOnGround = player.isOnGround();
        state.setOnGround(isOnGround);

        // Calculate velocity
        double moveX = player.input.movementSideways;
        double moveZ = player.input.movementForward;
        boolean isMoving = moveX != 0 || moveZ != 0;

        // Handle momentum
        if (isOnGround && isMoving) {
            // Increase momentum while moving
            double currentMomentum = state.getMomentum();
            double newMomentum = Math.min(currentMomentum + 0.008, 2.5); // Max momentum at 2.5
            state.setMomentum(newMomentum);
        } else if (!isMoving || player.isSneaking()) {
            // Reduce momentum when stopped or sneaking
            state.setMomentum(Math.max(state.getMomentum() - 0.03, 0.5));
        }

        // Check for wall collision
        if (wasOnGround && isOnGround && !isMoving) {
            state.setMomentum(state.getMomentum() - 0.05);
        }

        // Apply movement
        applyMovement(player);
    }

    private void applyMovement(ClientPlayerEntity player) {
        if (player.input.movementSideways == 0 && player.input.movementForward == 0) {
            return;
        }

        double momentum = state.getMomentum();
        double yaw = Math.toRadians(player.getYaw());
        
        double moveX = player.input.movementSideways;
        double moveZ = player.input.movementForward;
        
        // Normalize movement vector
        double length = Math.sqrt(moveX * moveX + moveZ * moveZ);
        if (length > 0) {
            moveX /= length;
            moveZ /= length;
        }

        // Rotate movement based on player yaw
        double rotatedX = moveX * Math.cos(yaw) - moveZ * Math.sin(yaw);
        double rotatedZ = moveX * Math.sin(yaw) + moveZ * Math.cos(yaw);

        // Apply momentum
        double speed = getSpeedForMomentum(momentum);
        player.setVelocity(rotatedX * speed, player.getVelocity().y, rotatedZ * speed);

        // Handle water running at max momentum
        if (momentum >= 2.4 && player.isTouchingWater()) {
            player.setVelocity(player.getVelocity().x, 0.1, player.getVelocity().z);
        }

        // Auto-climb small blocks
        autoClimb(player, rotatedX, rotatedZ, speed);
    }

    private void autoClimb(ClientPlayerEntity player, double moveX, double moveZ, double speed) {
        // Check if there's a block ahead
        Vec3d pos = player.getPos();
        Vec3d frontPos = pos.add(moveX * 0.6, 0, moveZ * 0.6);

        if (player.getWorld().getBlockState(frontPos.toBlockPos()).getMaterial().isSolid()) {
            // Try to climb
            Vec3d climbPos = frontPos.add(0, 1, 0);
            if (!player.getWorld().getBlockState(climbPos.toBlockPos()).getMaterial().isSolid()) {
                player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
            }
        }
    }

    private double getSpeedForMomentum(double momentum) {
        // Level progression
        if (momentum < 0.8) {
            return 0.25; // Level 1: Soul Speed III (approx 0.25 blocks/tick)
        } else if (momentum < 1.2) {
            return 0.4;  // Level 2: Noticeably faster
        } else if (momentum < 1.6) {
            return 0.6;  // Level 3: Extremely fast
        } else if (momentum < 2.0) {
            return 0.85; // Level 4: Very high speed
        } else {
            return 1.2;  // Level 5: Maximum momentum mode
        }
    }

    private void notifyToggle() {
        if (enabled) {
            // Notification will be sent via mixin
            MomentumState.lastToggleMessage = Text.literal("§6Momentum Runner §7enabled");
        } else {
            MomentumState.lastToggleMessage = Text.literal("§6Momentum Runner §7disabled");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getMomentum() {
        return state.getMomentum();
    }

    public int getMomentumLevel() {
        double momentum = state.getMomentum();
        if (momentum < 0.8) return 1;
        else if (momentum < 1.2) return 2;
        else if (momentum < 1.6) return 3;
        else if (momentum < 2.0) return 4;
        else return 5;
    }
}
