package com.bilgeadam.personelkayit;

import java.time.LocalDate;

// POJO : Plain old java object
public class Personel {
	
	private int id;
	private String ad;
	private String soyad;
	private LocalDate dogumTarihi;
	private double maas;
	
	// immutable
	// veritabanı insert işlemi için
	public Personel(String ad, String soyad, 
			LocalDate dogumTarihi, double maas) {
		
		this.ad = ad;
		this.soyad = soyad;
		this.dogumTarihi = dogumTarihi;
		this.maas = maas;
		
	}
	// veritabaından veri çekerken nesne oluşturmak için kullanacağım 
	public Personel(int id, String ad, String soyad, 
			LocalDate dogumTarihi, double maas) {
		this(ad, soyad, dogumTarihi, maas);
		this.id = id;	
	}

	public int getId() {
		return id;
	}

	public String getAd() {
		return ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public LocalDate getDogumTarihi() {
		return dogumTarihi;
	}

	public double getMaas() {
		return maas;
	}
	
	

}
