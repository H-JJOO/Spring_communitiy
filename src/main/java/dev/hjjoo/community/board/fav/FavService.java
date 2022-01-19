package dev.hjjoo.community.board.fav;

import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.BoardFavEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//mapper;//Bean 등록했기때문에 가능
public class FavService {

    @Autowired
    private FavMapper mapper;

    @Autowired
    private UserUtils userUtils;

    public int insBoardFav(BoardFavEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.insBoardFav(entity);
    }

    public BoardFavEntity selBoardFav(int iboard) {
        return mapper.selBoardFav(createBoardFavEntity(iboard));
    }

    public int delBoardFav(int iboard) {
        return mapper.delBoardFav(createBoardFavEntity(iboard));
    }

    private BoardFavEntity createBoardFavEntity(int iboard) {
        BoardFavEntity entity = new BoardFavEntity();
        entity.setIboard(iboard);
        entity.setIuser(userUtils.getLoginUserPk());
        return entity;
    }
}
