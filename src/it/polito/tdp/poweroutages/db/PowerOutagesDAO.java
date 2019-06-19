package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Nerc> loadAllArchi(int n1,Map<Integer,Nerc> mappaNerc ) {

		String sql = "SELECT * " + 
				"from nercrelations AS n " + 
				"WHERE n.nerc_one= ? ";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n1);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = mappaNerc.get(res.getInt("nerc_two"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public int GetPeso(int n1,int n2,Map<Integer,Nerc> mappaNerc ) {

		String sql = "SELECT p1.nerc_id,p2.nerc_id,p1.date_event_began,p2.date_event_began, " + 
				"MONTH(p1.date_event_began) AS m ,YEAR(p1.date_event_began) AS Y " + 
				"FROM poweroutages AS p1 , poweroutages AS p2 " + 
				"WHERE p1.nerc_id<> p2.nerc_id AND MONTH(p1.date_event_began)=MONTH(p2.date_event_began) " + 
				"AND YEAR(p1.date_event_began)=YEAR(p2.date_event_began)  " + 
				"AND p1.nerc_id= ?  AND p2.nerc_id= ?   " + 
				"GROUP BY m,y  ";
		List<Nerc> nercList = new ArrayList<>();
		int peso =0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n1);
			st.setInt(2, n2);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				peso++;
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return peso;
	}
}
