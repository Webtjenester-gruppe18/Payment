package dtu.ws.messagingutils;

import dtu.ws.model.Event;

/**
 * @author Marcus August Christiansen - s175185
 */

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}