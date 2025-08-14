package com.movieflix.service;

import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.entity.Streaming;
import com.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private final StreamingRepository repository;

    public List<Streaming> findAll(){
        return repository.findAll();
    }

    public Streaming save (Streaming streaming){
        return repository.save(streaming);
    }

    public Optional<Streaming>  update (Long id, StreamingRequest request){
        return repository.findById(id)
                .map(existingStreaming -> {
                    existingStreaming.setName(request.name());
                    return repository.save(existingStreaming);
                });
    }

    public Optional<Streaming> findById(Long id){
        return repository.findById(id);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
