package ru.job4j.forum.model;

import java.util.Calendar;
import java.util.Objects;

public class Comment {
    private int id;
    private String text;
    private User user;
    private Calendar created;

    public static Comment of(String text, User user) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setCreated(Calendar.getInstance());
        return comment;
    }

    public void setCurrentDate() {
        created = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
