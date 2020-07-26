package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;

public interface BoardDAO {
	public List<ArticleVO> selectAllArticlesList(Map<String, Integer> pagingMap) throws DataAccessException;
	public int selectTotArticles() throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public int selectNewArticleNO() throws DataAccessException;
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
}
