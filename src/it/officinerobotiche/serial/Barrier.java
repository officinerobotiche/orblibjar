/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

/**
 *
 * @author Raffaello
 */
public class Barrier
{
    public synchronized void block() throws InterruptedException
    {
        wait();
    }
 
    public synchronized void release() throws InterruptedException
    {
        notify();
    }
 
    public synchronized void releaseAll() throws InterruptedException
    {
        notifyAll();
    }
 
}