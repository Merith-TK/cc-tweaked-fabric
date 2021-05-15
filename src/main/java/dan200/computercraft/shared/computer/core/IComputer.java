/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.computer.core;

import dan200.computercraft.shared.common.ITerminal;

public interface IComputer extends ITerminal, InputHandler {
    int getInstanceID();

    void turnOn();

    void shutdown();

    void reboot();

    default void queueEvent(String event) {
        this.queueEvent(event, null);
    }

    @Override
    void queueEvent(String event, Object[] arguments);

    default ComputerState getState() {
        if (!this.isOn()) {
            return ComputerState.OFF;
        }
        return this.isCursorDisplayed() ? ComputerState.BLINKING : ComputerState.ON;
    }

    boolean isOn();

    boolean isCursorDisplayed();
}
