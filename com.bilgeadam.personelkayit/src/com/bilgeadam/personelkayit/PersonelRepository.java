package com.bilgeadam.personelkayit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonelRepository {
	
	static final String DB_CONNECTION_STRING = 
	"jdbc:postgresql://localhost/personel_kayit_db"+
	"?user=postgres&password=123456";
	
	
	
	public static void kaydet(Personel personel) throws SQLException {
		
		Connection connection = 
				DriverManager.getConnection(DB_CONNECTION_STRING);
		PreparedStatement insert = connection.prepareStatement(
		"INSERT INTO personeller(name, surname, date_of_birth, salary)" +
		" VALUES(?, ?, ?, ?)");
		
		insert.setString(1,personel.getAd());
		insert.setString(2,personel.getSoyad());
		insert.setDate(3, java.sql.Date.valueOf(personel.getDogumTarihi()));
		insert.setDouble(4, personel.getMaas());
		
		insert.executeUpdate();
		//Statement insert = connection.createStatement();
		
		// SQL Injection 
		// "select * from personeller where id = + "0 OR 1=1 SELE ";
		//insert.executeUpdate(
		//"INSERT INTO personeller(name, surname, date_of_birth, salary)" +
		//" VALUES('"+ personel.getAd() +"', '" + personel.getSoyad() +"','" +
		//		personel.getDogumTarihi() + "'," + personel.getMaas()+")");
		
		
		

				
		connection.close();
		insert.close();
		
	}



	public static List<Personel> listele() throws SQLException {
		List<Personel> liste = new ArrayList<>();
		
		Connection connection = 
				DriverManager.getConnection(DB_CONNECTION_STRING);
		
		Statement selectAll = connection.createStatement();
		
		ResultSet rs =  selectAll.executeQuery(
				"select id,name,surname, date_of_birth, salary from personeller");
		
		while(rs.next()) {
			Personel personel = new Personel(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					LocalDate.parse(rs.getDate(4).toString()),
					rs.getDouble(5)
					);
			liste.add(personel);
		}
		
		return liste;
	}



	public static void guncelle(Personel personel) throws SQLException {
		Connection connection = 
				DriverManager.getConnection(DB_CONNECTION_STRING);
		
		PreparedStatement update = connection.prepareStatement(
		"UPDATE personeller SET name = ?,"+
							   "surname = ?," +
							   "date_of_birth = ?," +
							   "salary = ?" +
							   " WHERE id = ?"
		);
		
		update.setString(1,personel.getAd());
		update.setString(2,personel.getSoyad());
		update.setDate(3, java.sql.Date.valueOf(personel.getDogumTarihi()));
		update.setDouble(4, personel.getMaas());
		update.setInt(5, personel.getId());
		
		
		update.executeUpdate();
		
	}



	public static void sil(int id) throws SQLException {
		Connection connection = 
				DriverManager.getConnection(DB_CONNECTION_STRING);
		
		PreparedStatement update = connection.prepareStatement(
		"DELETE FROM personeller  WHERE id = ?"
		);
		
		update.setInt(1,id);
		
		update.executeUpdate();
	}



	public static List<Personel> ara(String text) throws SQLException {
		List<Personel> liste = new ArrayList<>();
		
		Connection connection = 
				DriverManager.getConnection(DB_CONNECTION_STRING);
		
		text = "%"+ text + "%";
		
		PreparedStatement search = connection.prepareStatement(
		"SELECT * FROM personeller WHERE name ILIKE ? OR surname ILIKE ?"
		);
		
		search.setString(1, text);
		search.setString(2, text);
		
		ResultSet rs = search.executeQuery();
		
		while(rs.next()) {
			Personel personel = new Personel(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					LocalDate.parse(rs.getDate(4).toString()),
					rs.getDouble(5)
					);
			liste.add(personel);
		}
		
		return liste;
	}

}
