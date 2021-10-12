package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ForumRepository {
    private Map<Integer, Post> posts = new ConcurrentHashMap();
    private Map<String, User> users = new ConcurrentHashMap<>();
    private AtomicInteger nextPostId = new AtomicInteger(1);
    private AtomicInteger nextCommentId = new AtomicInteger(1);
    private AtomicInteger nextUserId = new AtomicInteger(1);

    public ForumRepository() {
        User sergey = User.of("sergey", "sergey@mail.ru", "$2a$10$lhmKWGTBpHwGeBvtJ3wD3uxiyARG5EusLQ8LpGKZOnMn6D2Fnhz4y");
        sergey.setId(nextUserId.getAndIncrement());
        User elena = User.of("elena", "elena@mail.ru", "$2a$10$EL.HOX9R11KGS2nOuNs3bOjrtsgzit3CbqQmhL9EIW3oeespcKhby");
        elena.setId(nextUserId.getAndIncrement());

        Post postOne = Post.of("First post", "trying to create post", sergey);
        postOne.setId(nextPostId.getAndIncrement());
        Post postTwo = Post.of("Second post", "About computers", elena);
        postTwo.setId(nextPostId.getAndIncrement());

        Comment comment1 = Comment.of("Hello", elena);
        comment1.setId(nextCommentId.getAndIncrement());
        Comment comment2 = Comment.of("How are you?", sergey);
        comment2.setId(nextCommentId.getAndIncrement());
        Comment comment3 = Comment.of("See you soon", elena);
        comment3.setId(nextCommentId.getAndIncrement());

        postOne.addComment(comment1);
        postOne.addComment(comment2);
        postTwo.addComment(comment1);
        postTwo.addComment(comment2);
        postTwo.addComment(comment3);

        posts.put(postOne.getId(), postOne);
        posts.put(postTwo.getId(), postTwo);

        users.put(sergey.getUsername(), sergey);
        users.put(elena.getUsername(), elena);
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(posts.values());
    }

    public Post getPostById(int id) {
        return posts.get(id);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public void saveUser(User user) {
        user.setId(nextUserId.getAndIncrement());
        users.put(user.getUsername(), user);
    }

    public void addCommentToPost(int postId, Comment comment) {
        comment.setId(nextCommentId.getAndIncrement());
        posts.get(postId).addComment(comment);
    }

    public boolean checkIfUserExist(User user) {
        return users.containsKey(user.getUsername());
    }

    public void addPost(Post post) {
        post.setId(nextPostId.getAndIncrement());
        posts.put(post.getId(), post);
    }
}
