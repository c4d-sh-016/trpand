package net.exshinigami.remembrance.model;

public enum UserType {
    ADMIN("A"),
    MODERATOR("M"),
    CLIENT("C");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}