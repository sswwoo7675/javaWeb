package sec03.brd01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
	BoardDAO boardDAO;
	
	public BoardService() {
		boardDAO = new BoardDAO();
	}
	
	public Map listArticles(Map<String, Integer> pagingMap){
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList = boardDAO.selectAllAritcles(pagingMap);
		int totArticles = boardDAO.selectTotArticles();
		int Lsection = (totArticles % 100 == 0) ? totArticles / 100 : totArticles / 100 + 1;
		int Lpage = (totArticles % 10 == 0) ? totArticles / 10 : totArticles / 10 + 1;
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("Lsection", Lsection);
		articlesMap.put("Lpage", Lpage);
		return articlesMap;
	}
	
	public int addArticle(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
	
	public ArticleVO viewArticle(int articleNO) {
		ArticleVO article = null;
		article = boardDAO.selectArticle(articleNO);
		return article;
	}
	
	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
	}
	
	public List<Integer> removeArticle(int articleNO){
		List<Integer> articleNOList = boardDAO.selectRemovedArticles(articleNO);
		boardDAO.deleteArticle(articleNO);
		return articleNOList;
	}
	
	public int addReply(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
}
