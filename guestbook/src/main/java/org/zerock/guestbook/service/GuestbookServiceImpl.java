package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

import static org.zerock.guestbook.entity.QGuestbook.*;

/**
 * @author sskim
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO----------------------------------");
        log.info("dto = {}", dto);
        Guestbook entity = dtoToEntity(dto);
        log.info("entity = {}", entity);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        //entity 객체를 가져옴 JPA Context
        Page<Guestbook> result = repository.findAll(booleanBuilder,pageable);   //Querydsl 사용

        //Function -> DTO로 변환
        Function<Guestbook, GuestbookDTO> fn = this::entityToDto;

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        Guestbook guestbook = result.orElseGet(null);
        return entityToDto(guestbook);
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = guestbook.gno.gt(0); //gno>0 조건만 생성
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {    //검색 조건이 없는 경우
            return booleanBuilder;
        }

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(guestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(guestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(guestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
