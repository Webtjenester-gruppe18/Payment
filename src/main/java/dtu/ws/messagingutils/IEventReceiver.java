package dtu.ws.messagingutils;

import dtu.ws.model.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}