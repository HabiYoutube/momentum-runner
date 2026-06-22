package com.habioutube.momentumrunner.util;

import net.minecraft.text.Text;

public class MomentumState {
    private double momentum = 0.5;
    private boolean onGround = false;
    public static Text lastToggleMessage = null;

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double value) {
        this.momentum = Math.max(0.5, Math.min(value, 2.5));
    }

    public void reset() {
        this.momentum = 0.5;
    }

    public boolean wasOnGround() {
        return onGround;
    }

    public void setOnGround(boolean value) {
        this.onGround = value;
    }
}
