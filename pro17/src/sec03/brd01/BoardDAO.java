package sec03.brd01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ArticleVO> selectAllAritcles(Map pagingMap){
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		int section = (Integer)pagingMap.get("section");
		int pageNum = (Integer)pagingMap.get("pageNum");
		try {
			conn = dataFactory.getConnection();
			String query ="SELECT * FROM ( "
					+ "select ROWNUM  as recNum,"+"LVL,"
						+"articleNO,"
						+"parentNO,"
						+"title,"
						+"id,"
						+"writeDate"
			                  +" from (select LEVEL as LVL, "
							+"articleNO,"
							+"parentNO,"
							+"title,"
							+"id,"
							 +"writeDate"
						   +" from t_board" 
						   +" START WITH  parentNO=0"
						   +" CONNECT BY PRIOR articleNO = parentNO"
						  +"  ORDER SIBLINGS BY articleNO DESC)"
				+") "                        
				+" where recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+?*10";   
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("lvl");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				
				ArticleVO article = new ArticleVO();
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setId(id);
				article.setWriteDate(writeDate);
				
				articlesList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return articlesList;
	}
	
	private int getNewArticleNO() {
		int newNO = 0;
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT max(articleNO) from t_board";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				newNO =  rs.getInt(1) + 1;
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return newNO;
	}
	
	public int insertNewArticle(ArticleVO article) {
		int articleNO = getNewArticleNO();
		try {
			conn = dataFactory.getConnection();
			
			int parnetNO = article.getParentNO();
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			
			String query = "INSERT INTO t_board(articleNO, parentNO, title, content, imageFileName, id)"
					+" VALUES(?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			pstmt.setInt(2, parnetNO);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return articleNO;
	}
	
	public ArticleVO selectArticle(int articleNO) {
		ArticleVO article = new ArticleVO();
		try {
			conn = dataFactory.getConnection();
			String query = "select articleNO, parentNO, title, content, imageFileName, id, writeDate"
					+" from t_board"
					+" where articleNO=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int _articleNO = rs.getInt("articleNO");
			int parentNO = rs.getInt("parentNO");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageFileName = rs.getString("imageFileName");
			String id = rs.getString("id");
			Date writeDate = rs.getDate("writeDate");
			
			article.setArticleNO(_articleNO);
			article.setParentNO (parentNO);
			article.setTitle(title);
			article.setContent(content);
			article.setImageFileName(imageFileName);
			article.setId(id);
			article.setWriteDate(writeDate);
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public void updateArticle(ArticleVO article) {
		int articleNO = article.getArticleNO();
		String title = article.getTitle();
		String content = article.getContent();
		String imageFileName = article.getImageFileName();
		
		try {
			conn = dataFactory.getConnection();
			String query = "update t_board set title=?, content=?";
			if(imageFileName != null && imageFileName.length()!=0) {
				query += ", imageFileName=?";
			}
			query += " where articleNO=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			if(imageFileName != null && imageFileName.length()!=0) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNO);
			} else {
				pstmt.setInt(3, articleNO);
			}
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> selectRemovedArticles(int articleNO){
		List<Integer> articleNOList = new ArrayList<Integer>();
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT articleNO FROM t_board";
			query += " START WITH articleNO = ?";
			query += " CONNECT BY PRIOR articleNO = parentNO";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				articleNO = rs.getInt("articleNO");
				articleNOList.add(articleNO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return articleNOList;
	}
	
	public void deleteArticle(int articleNO) {
		try {
			conn = dataFactory.getConnection();
			String query = "DELETE FROM t_board";
			query += " WHERE articleNO in";
			query += " (SELECT articleNO FROM t_board";
			query += " START WITH articleNO = ?";
			query += " CONNECT BY PRIOR articleNO = parentNO)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int selectTotArticles() {
		int totArticles = 0;
		try {
			conn = dataFactory.getConnection();
			String query = "select count(articleNO) from t_board";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				totArticles = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totArticles;
	}
}









































