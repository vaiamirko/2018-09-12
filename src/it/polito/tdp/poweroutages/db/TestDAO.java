package it.polito.tdp.poweroutages.db;

public class TestDAO {

	public static void main(String[] args) {

		PowerOutagesDAO dao = new PowerOutagesDAO();

		System.out.println(dao.loadAllNercs());
		//System.out.print(dao.loadAllArchi(n1, mappaNerc));
	}

}
