package com.bilgeadam.personelkayit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

public class PersonelFormu extends JFrame {

	private JPanel contentPane;
	private JTextField txtAra;
	private JTextField txtSoyad;
	private JTextField txtDogumTarihi;
	private JTextField txtMaas;
	private JLabel lblMaas;
	private JTextField txtId;
	private JTable table;

	DefaultTableModel model;
	List<Personel> personel;
	private JButton btnKaytSe;
	private JButton btnSil;
	private JTextField txtAd;
	private JButton btnAra;

	private void personelListele(List<Personel> personel) {
		this.personel = personel;
		// students = business.liste();
//		try {
//			personel = PersonelRepository.listele();
//		} catch (SQLException e) {
//			System.out.println("Hata oluştu." + e.getMessage());
//		}
		// new ArrayList<>();
		model = new DefaultTableModel();

		model.addColumn("Id");
		model.addColumn("Ad");
		model.addColumn("Soyad");
		model.addColumn("Doğum Tarihi");
		model.addColumn("Maaş");

		Object[] row = new Object[5];

		int size = personel.size();

		// personel tablosundaki satırlar ve veriler oluşuyor
		for (int i = 0; i < size; i++) {
			row[0] = personel.get(i).getId();
			row[1] = personel.get(i).getAd();
			row[2] = personel.get(i).getSoyad();
			row[3] = personel.get(i).getDogumTarihi();
			row[4] = personel.get(i).getMaas();

			model.addRow(row);
		}

		table.setModel(model);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// personel formunun açılmasını sağlamak için
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonelFormu frame = new PersonelFormu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PersonelFormu() {
		setTitle("Personel Kayıt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(111, 68, 86, 20);
		contentPane.add(txtSoyad);

		JLabel lblSoyad = new JLabel("Soyad");
		lblSoyad.setBounds(23, 74, 46, 14);
		contentPane.add(lblSoyad);

		txtDogumTarihi = new JTextField();
		txtDogumTarihi.setColumns(10);
		txtDogumTarihi.setBounds(111, 99, 86, 20);
		contentPane.add(txtDogumTarihi);

		JLabel lblDoumTarihi = new JLabel("Doğum Tarihi");
		lblDoumTarihi.setBounds(23, 105, 78, 14);
		contentPane.add(lblDoumTarihi);

		txtMaas = new JTextField();
		txtMaas.setColumns(10);
		txtMaas.setBounds(111, 130, 86, 20);
		contentPane.add(txtMaas);

		lblMaas = new JLabel("Maaş");
		lblMaas.setBounds(23, 133, 82, 14);
		contentPane.add(lblMaas);

		JButton btnKaydet = new JButton("Kaydet");
		btnKaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Personel personel = null;

				try {
					if (txtId.getText().length() == 0) {
						personel = new Personel(txtAd.getText(), txtSoyad.getText(),
								LocalDate.parse(txtDogumTarihi.getText()), Double.valueOf(txtMaas.getText()));
						PersonelRepository.kaydet(personel);
					} else {
						personel = new Personel(Integer.valueOf(txtId.getText()), txtAd.getText(), txtSoyad.getText(),
								LocalDate.parse(txtDogumTarihi.getText()), Double.valueOf(txtMaas.getText()));

						PersonelRepository.guncelle(personel);
					}

					JOptionPane.showMessageDialog(btnKaydet, "Kayıt işlemi başarılı");
					ekranTemizle();
					personelListele(PersonelRepository.listele());
				} catch (SQLException e1) {
					System.out.println("Hata oluştu." + e1.getMessage());
				}
			}
		});
		btnKaydet.setBounds(111, 165, 89, 23);
		contentPane.add(btnKaydet);

		JLabel lblId = new JLabel("Id");
		lblId.setBounds(23, 12, 46, 14);
		contentPane.add(lblId);

		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		txtId.setBounds(111, 9, 86, 20);
		contentPane.add(txtId);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 199, 414, 166);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);

		btnKaytSe = new JButton("Kayıt Seç");
		btnKaytSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir = table.getSelectedRow();

				if (secilenSatir > -1) {
					Personel secilenPersonel = personel.get(secilenSatir);

					txtId.setText(String.valueOf(secilenPersonel.getId()));
					txtAd.setText(secilenPersonel.getAd());
					txtSoyad.setText(secilenPersonel.getSoyad());
					txtDogumTarihi.setText(secilenPersonel.getDogumTarihi().toString());
					txtMaas.setText(String.valueOf(secilenPersonel.getMaas()));
				} else {
					JOptionPane.showMessageDialog(scrollPane, "Tablodan seçim yapınız!");
				}
			}
		});
		btnKaytSe.setBounds(212, 165, 89, 23);
		contentPane.add(btnKaytSe);

		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir = table.getSelectedRow();

				if (secilenSatir > -1) {
					try {
						PersonelRepository.sil(personel.get(secilenSatir).getId());
						personelListele(PersonelRepository.listele());
					} catch (SQLException e1) {
						System.out.println("Hata oluştu." + e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(scrollPane, "Tablodan seçim yapınız!");
				}
			}
		});
		btnSil.setBounds(311, 165, 89, 23);
		contentPane.add(btnSil);

		JPanel panel = new JPanel();
		panel.setBounds(227, 12, 173, 122);
		contentPane.add(panel);
		panel.setLayout(null);

		txtAra = new JTextField();
		txtAra.setBounds(63, 21, 86, 20);
		panel.add(txtAra);
		txtAra.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ad");
		lblNewLabel.setBounds(21, 24, 46, 14);
		panel.add(lblNewLabel);

		btnAra = new JButton("Ara");
		btnAra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtAra.getText().length() == 0) {
					JOptionPane.showMessageDialog(btnAra, "Lütfen aramak istediğiniz kelimeyi giriniz.");
				} else {
					try {
						personel = PersonelRepository.ara(txtAra.getText());
						personelListele(personel);
					} catch (SQLException e1) {
						System.out.println("Hata" + e1.getMessage());
					}
				}
			}
		});
		btnAra.setBounds(60, 63, 89, 23);
		panel.add(btnAra);

		JLabel lblNewLabel_1 = new JLabel("Ad");
		lblNewLabel_1.setBounds(23, 43, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtAd = new JTextField();
		txtAd.setColumns(10);
		txtAd.setBounds(111, 37, 86, 20);
		contentPane.add(txtAd);

		try {
			personelListele(PersonelRepository.listele());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	void ekranTemizle() {
		txtId.setText("");
		txtAd.setText("");
		txtSoyad.setText("");
		txtDogumTarihi.setText("");
		txtMaas.setText("");
	}
}
