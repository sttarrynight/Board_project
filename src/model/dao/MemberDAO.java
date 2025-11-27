package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dto.MemberDTO;
import util.DBUtil;

public class MemberDAO {

	//회원 등록(회원가입)
	public int insert(MemberDTO member) {
		Connection conn = null;
		PreparedStatement st = null;
		
		String sql = "INSERT INTO Member(username, password, phone, created_at) " +
				     "VALUES(?, ?, ?, SYSDATE)";
		
		int result = 0;
		
		try {
			conn = DBUtil.dbConnect();
			st = conn.prepareStatement(sql);
			st.setString(1, member.getUsername());
			st.setString(2, member.getPassword());
			st.setString(3, member.getPhone());
			
			result = st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return result;
	}
	
	//특정 회원 조회(username - 로그인용)
	public MemberDTO selectByUsername(String username) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "select * from member where username = ?";
			
		MemberDTO member = null;
			
		try {
			conn = DBUtil.dbConnect();
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			rs = st.executeQuery();
				
			if(rs.next()) {
				member = makeMember(rs);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
			
		return member;
	}
	
	// user_id로 회원 조회
	public MemberDTO selectById(int user_id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "select * from member where user_id = ?";
		
		MemberDTO member = null;
		
		try {
			conn = DBUtil.dbConnect();
			st = conn.prepareStatement(sql);
			st.setInt(1, user_id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				member = makeMember(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return member;
	}
	
	//모든 회원 조회
	public List<MemberDTO> selectAll() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from member";
		
		List<MemberDTO> memberList = new ArrayList<>();
		
		try {
			conn = DBUtil.dbConnect();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
				
			while(rs.next()) {
				MemberDTO member = makeMember(rs);
				memberList.add(member);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
			
		return memberList;
	}
	
	// 회원 정보 수정 (UPDATE)
	public int update(MemberDTO member) {
		Connection conn = null;
		PreparedStatement st = null;
		
		String sql = "UPDATE Member SET password = ? WHERE user_id = ?";

		int result = 0;
		
		try {
			conn = DBUtil.dbConnect();
			st = conn.prepareStatement(sql);
			st.setString(1, member.getPassword());
			st.setInt(2, member.getUser_id());
			
			result = st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return result;
	}
	
	// 회원 삭제 (DELETE)
	public int delete(int userId) {
		Connection conn = null;
		PreparedStatement st = null;
		
		String sql = "DELETE FROM Member WHERE user_id = ?";

		int result = 0;
		
		try {
			conn = DBUtil.dbConnect();
			st = conn.prepareStatement(sql);
			st.setInt(1, userId);
			
			result = st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return result;
	}
	
	private MemberDTO makeMember(ResultSet rs) throws SQLException {
		MemberDTO member = new MemberDTO();
		
		member.setUser_id(rs.getInt("user_id"));
		member.setUsername(rs.getString("username"));
		member.setPassword(rs.getString("password"));
		member.setPhone(rs.getString("phone"));
		member.setCreated_at(rs.getDate("created_at"));
		
		return member;
	}
}