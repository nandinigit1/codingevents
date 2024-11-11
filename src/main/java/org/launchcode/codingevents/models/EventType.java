package org.launchcode.codingevents.models;
//We can delete EventType enum as it is not used in relationshipMapping
public enum EventType {

    CONFERENCE("Conference"),
    MEETUP("Meetup"),
    WORKSHOP("Workshop"),
    SOCIAL("Social");

    private final String displayName;
    //For final fields no setter

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
