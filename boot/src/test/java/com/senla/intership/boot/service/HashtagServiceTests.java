package com.senla.intership.boot.service;

import com.senla.intership.boot.api.repository.HashtagRepository;
import com.senla.intership.boot.dto.hashtag.HashtagWithPostsDto;
import com.senla.intership.boot.entity.Hashtag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HashtagServiceTests {

    @InjectMocks
    private HashtagServiceImpl hashtagService;

    @Mock
    private HashtagRepository hashtagRepository;

    @Spy
    private ModelMapper mapper;

    private static final String firstValue = "#hash";


    @Test
    public void shouldSaveHashtagCorrect(){
        Hashtag firstHashtag= new Hashtag();
        firstHashtag.setId(123L);
        firstHashtag.setValue(firstValue);
        when(hashtagRepository.save(any())).thenReturn(firstHashtag);

        HashtagWithPostsDto hashtagDto = new HashtagWithPostsDto();
        hashtagDto.setValue(firstValue);
        hashtagDto = hashtagService.save(hashtagDto);
        assertEquals(123L,hashtagDto.getId());
        assertEquals(firstValue,hashtagDto.getValue());
    }
    @Test
    public void shouldGetHashtagCorrect(){
        Hashtag firstHashtag= new Hashtag();
        firstHashtag.setId(123L);
        firstHashtag.setValue(firstValue);
        when(hashtagRepository.save(any())).thenReturn(firstHashtag);
        when(hashtagRepository.findById(any())).thenReturn(Optional.of(firstHashtag));

        HashtagWithPostsDto hashtagDto = new HashtagWithPostsDto();
        hashtagDto.setValue(firstValue);
        hashtagService.save(hashtagDto);
        hashtagDto = hashtagService.read(123L);
        assertEquals(123L,hashtagDto.getId());
        assertEquals(firstValue,hashtagDto.getValue());
    }
    @Test
    public void shouldDeleteHashtagCorrect(){
        Hashtag firstHashtag= new Hashtag();
        firstHashtag.setId(123L);
        firstHashtag.setValue(firstValue);
        when(hashtagRepository.save(any())).thenReturn(firstHashtag);

        HashtagWithPostsDto hashtagDto = new HashtagWithPostsDto();
        hashtagDto.setValue(firstValue);
        hashtagService.save(hashtagDto);

        hashtagService.delete(hashtagDto.getId());

        verify(hashtagRepository).deleteById(hashtagDto.getId());
    }




}
