package dev.hjjoo.community.board;

import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.BoardDto;
import dev.hjjoo.community.model.BoardEntity;
import dev.hjjoo.community.model.BoardPrevNextVo;
import dev.hjjoo.community.model.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BoardService {

    @Autowired
    private BoardMapper mapper;

    @Autowired
    private UserUtils userUtils;

    public int insBoard(BoardEntity entity) {
        int result = 0;
        try {
            entity.setIuser(userUtils.getLoginUserPk());
            result = mapper.insBoard(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<BoardVo> selBoardList(BoardDto dto) {
        return mapper.selBoardList(dto);
    }

    public BoardVo selBoard(BoardDto dto) {
        BoardVo detail = mapper.selBoard(dto);//기존에 있는 ip 값 최초에는 null
        if (dto.getLastip() != null && !Objects.equals(dto.getLastip(), detail.getLastip())) {//조회 시도하는 ip 와 기존에 있는 ip 가 다르다면
            int hitsResult = mapper.addHits(dto);//조회수 + 1 쿼리 실행
            if (hitsResult == 1) {//조회수 + 1 쿼리문이 실행되었다면
                detail.setHits(detail.getHits() + 1);//디테일 창에 들어갈때 + 1 바로 적용
            }
        }
        return detail;
    }

    public BoardPrevNextVo selPrevNext(BoardVo vo) {
        return mapper.selPrevNext(vo);
    }

    public int updBoard(BoardEntity entity) {
        try {
            entity.setIuser(userUtils.getLoginUserPk());
            return mapper.updBoard(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }

    public int delBoard(BoardEntity entity) {// icategory, iboard
        entity.setIuser(userUtils.getLoginUserPk());
        entity.setIsdel(1);
        return mapper.updBoard(entity);
    }
}
