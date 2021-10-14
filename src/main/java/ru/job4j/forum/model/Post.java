package ru.job4j.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String desc;

    @Column(name = "created", nullable = false)
    private Calendar created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "posts_comments",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments = new ArrayList<>();

    public static Post of(String name, String desc, User author) {
        Post post = new Post();
        post.setName(name);
        post.setDesc(desc);
        post.setAuthor(author);
        post.setCreated(Calendar.getInstance());
        return post;
    }

    public void setCurrentDate() {
        created = Calendar.getInstance();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(name, post.name)
                && Objects.equals(desc, post.desc)
                && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, created);
    }
}
