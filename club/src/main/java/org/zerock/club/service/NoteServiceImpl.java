package org.zerock.club.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.Note;
import org.zerock.club.repository.NoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sskim
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        log.info("==========================");
        log.info("note = {}", note);

        noteRepository.save(note);

        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {

        Optional<Note> result = noteRepository.getWithWriter(num);
        return result.map(this::entityToDTO).orElse(null);
    }

    @Override
    public void modify(NoteDTO noteDTO) {

        Long num = noteDTO.getNum();

        Optional<Note> result = noteRepository.findById(num);

        if(result.isPresent()){

            Note note  = result.get();

            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {

        List<Note> noteList = noteRepository.getList(writerEmail);
        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    }
}
