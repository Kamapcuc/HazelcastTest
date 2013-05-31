package ru.combo_breaker;


import com.hazelcast.core.EntryAdapter;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.impl.DataAwareEntryEvent;

public class KeyholeWatcher extends EntryAdapter {

    @Override
    public void entryAdded(EntryEvent event) {
        System.out.println("Added " + event.getValue());
    }

    @Override
    public void entryRemoved(EntryEvent event) {
        System.out.println("Removed " + event);
    }

    @Override
    public void entryUpdated(EntryEvent event) {
        DataAwareEntryEvent tmp = (DataAwareEntryEvent)event;
        Object oldVal = tmp.getOldValue();
        Object newVal = tmp.getValue();
        System.out.println("Updated from " + oldVal + " to " + newVal);
    }

    @Override
    public void entryEvicted(EntryEvent event) {
        System.out.println("Evicted " + event);
    }
}
