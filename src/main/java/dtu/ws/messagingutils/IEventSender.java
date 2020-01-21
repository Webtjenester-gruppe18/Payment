package dtu.ws.messagingutils;

import dtu.ws.model.Event;

/**
 * @author Marcus August Christiansen - s175185
 */

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
