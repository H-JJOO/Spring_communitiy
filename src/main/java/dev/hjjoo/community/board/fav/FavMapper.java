package dev.hjjoo.community.board.fav;

import dev.hjjoo.community.model.BoardFavEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavMapper {
    int insBoardFav(BoardFavEntity entity);
    BoardFavEntity selBoardFav(BoardFavEntity entity);
    int delBoardFav(BoardFavEntity entity);
}
