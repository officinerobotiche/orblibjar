/*
 * Copyright (C) 2014 Officine Robotiche.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Raffaello Bonghi - raffaello.bonghi@officinerobotiche.it
 */
package it.officinerobotiche.serial;

/**
 * Object to create a blocking method.
 * Used for block serial communication.
 * @author Raffaello Bonghi
 */
public class Barrier {
    /**
     * Blocking method for a fixed time.
     * @param timeout the maximum time to wait in milliseconds. 
     * @throws InterruptedException if any thread interrupted the current thread
     * before or while the current thread was waiting for a notification. 
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     */
    public synchronized void block(long timeout) throws InterruptedException {
        wait(timeout);
    }
    /**
     * Blocking method.
     * @throws InterruptedException if any thread interrupted the current thread
     * before or while the current thread was waiting for a notification. 
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     */
    public synchronized void block() throws InterruptedException {
        wait();
    }
    /**
     * Release method.
     * @throws InterruptedException if any thread interrupted the current thread
     * before or while the current thread was waiting for a notification. 
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     */
    public synchronized void release() throws InterruptedException {
        notify();
    }
    /**
     * Release all thread from a blocking method.
     * @throws InterruptedException if any thread interrupted the current thread
     * before or while the current thread was waiting for a notification. 
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     */
    public synchronized void releaseAll() throws InterruptedException {
        notifyAll();
    }

}
