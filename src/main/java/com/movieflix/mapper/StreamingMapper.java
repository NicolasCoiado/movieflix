package com.movieflix.mapper;

import com.movieflix.entity.Streaming;
import com.movieflix.controller.request.StremingRequest;

import com.movieflix.controller.response.StreamingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {
    public static Streaming toStreaming(StremingRequest request) {
        return Streaming
                .builder()
                .name(request.name())
                .build();
    }

    public static StreamingResponse toStreamingRespose(Streaming streaming){
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}
