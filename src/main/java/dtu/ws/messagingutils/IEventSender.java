package dtu.ws.messagingutils;

import dtu.ws.model.Event;

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
