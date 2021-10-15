package ru.job4j.forum.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    public void shouldReturnPostPageWithPostAttribute() throws Exception {
        Post post = Post.of("first post", "description", new User());
        post.setId(1);
        when(postService.getPostById(1)).thenReturn(post);

        mockMvc.perform(get("/post?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attribute("post", hasProperty("id", is(1))))
                .andExpect(model().attribute("post", hasProperty("name", is("first post"))))
                .andExpect(model().attribute("post", hasProperty("desc", is("description"))))
                .andExpect(model().attribute("post", hasProperty("author")))
                .andExpect(model().attribute("post", hasProperty("comments")));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditPage() throws Exception {
        mockMvc.perform(get("/addPost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser
    public void shouldSaveNewPostWithParams() throws Exception {
        mockMvc.perform(post("/addPost")
                .param("name","First Post")
                .param("desc", "Description"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService).savePost(argument.capture());
        assertThat(argument.getValue().getName(), is("First Post"));
        assertThat(argument.getValue().getDesc(), is("Description"));
    }

    @Test
    @WithMockUser
    public void shouldAddNewCommentToPost() throws Exception {
        mockMvc.perform(post("/addComment")
                .param("postId", "1")
                .param("text", "first comment"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Integer> firstArgument = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Comment> secondArgument = ArgumentCaptor.forClass(Comment.class);
        verify(postService).addCommentToPost(firstArgument.capture(),secondArgument.capture());
        assertThat(firstArgument.getValue(), is(1));
        assertThat(secondArgument.getValue().getText(), is("first comment"));
    }
}
