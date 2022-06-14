package com.senla.intership.boot.service;

import com.senla.intership.boot.api.repository.PostCommentRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class PostCommentServiceTests {

    @InjectMocks
    private PostCommentServiceImpl postCommentService;

    @Mock
    private PostCommentRepository postCommentRepository;

    @Spy
    private ModelMapper mapper;

    /*@Test
    public void shouldSaveCommentCorrect(){
        PostComment comment = new PostComment();
        String text = "text";
        comment.setId(123L);
        comment.setText(text);

        when(postCommentRepository.save(any())).thenReturn(comment);

        PostCommentWithPostDto postDto = new PostCommentWithPostDto();
        postDto.setText(text);
        PostDto post = new PostDto();
        post.setId(1234L);
        postDto.setPost(post);
        postDto = postCommentService.create(postDto);

        assertEquals(postDto.getId(),123L);
        assertEquals(postDto.getText(),text);
    }

    @Test
    public void shouldGetCommentCorrect(){
        PostComment comment = new PostComment();
        String text = "text";
        comment.setId(123L);
        comment.setText(text);

        when(postCommentRepository.save(any())).thenReturn(comment);
        when(postCommentRepository.findById(any())).thenReturn(Optional.of(comment));

        PostCommentWithAllDto postDto = new PostCommentWithAllDto();
        postDto.setText(text);
        PostDto post = new PostDto();
        post.setId(1234L);
        postDto.setPost(post);

        postCommentService.create(mapper.map(postDto,PostCommentWithPostDto.class));

        postDto = postCommentService.read(123L);

        assertEquals(postDto.getId(),123L);
        assertEquals(postDto.getText(),text);
    }

    @Test
    public void shouldDeleteCommentCorrect(){
        PostComment comment = new PostComment();
        String text = "text";
        comment.setId(123L);
        comment.setText(text);

        when(postCommentRepository.save(any())).thenReturn(comment);

        PostCommentWithPostDto postDto = new PostCommentWithPostDto();
        postDto.setText(text);
        PostDto post = new PostDto();
        post.setId(1234L);
        postDto.setPost(post);

        postDto = postCommentService.create(postDto);

        postCommentService.delete(postDto.getId());

        verify(postCommentRepository).deleteById(123L);*/


}
