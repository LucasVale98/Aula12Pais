package dao;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import command.CryptoDummy;
import command.Impressora;
import model.Pais;
import model.Usuario;

public class UsuarioDAO {

	public boolean validar(Usuario usuario) throws Exception {
		String sqlSelect = "SELECT username, password FROM usuario "
				+ "WHERE username = ? and password = ?";
		// pega a conexão em um try normal para que ela não seja fechada
		try {
			
			Connection conn = ConnectionFactory.conexaoPais();
			// usando o try with resources do Java 7, que fecha o que abriu
			try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
				
				
				stm.setString(1, usuario.getUsername());
				stm.setString(2, usuario.getPassword());
				try (ResultSet rs = stm.executeQuery();) {
					if (rs.next()) {
						return true;
					} else {
						return false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return false;
	}
	
PreparedStatement pst = null;
	
	public void cripto(Usuario usuario) {
		
		String sql = "Insert into usuario (username, password) values (?, ?)";
		String senha =usuario.getPassword();
		
		try {
			
			
			Connection conn = ConnectionFactory.conexaoPais();
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, usuario.getUsername());
			pst.setString(2, usuario.getPassword());
			
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void criptoCliente(Usuario usuario) {
		String sqlInsert = "Insert into usuario (username, password) values (?, ?)";
		
		try (Connection conn = ConnectionFactory.conexaoPais(); 
			PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			
			stm.setString(1, usuario.getUsername());
			stm.setString(2, usuario.getPassword());
			stm.execute();
			
			String sMsgClara = usuario.getUsername();
			String sMsgCifrada = null;
			String sMsgDecifrada = null;
			byte[] bMsgClara = null;
			byte[] bMsgCifrada = null;
			byte[] bMsgDecifrada = null;
			
			try {
				
				Impressora prn = new Impressora();
				
				// Converte o texto String dado no equivalente byte[]
				bMsgClara = sMsgClara.getBytes("ISO-8859-1");
				
				// Criptografia Dummy
				
				CryptoDummy cdummy = new CryptoDummy();
				
				// Gera a chave criptografica Dummy simetrica e nome do arquivo onde
				// sera armazenada
				cdummy.geraChave(new File("chave.dummy"));
				
				// Gera a cifra Dummy da mensagem dada, com a chave Dummy simetrica dada
				cdummy.geraCifra(bMsgClara, new File("chave.dummy"));
				
				bMsgCifrada = cdummy.getTextoCifrado();
				
				// Converte o texto byte[] no equivalente String
				sMsgCifrada = (new String(bMsgCifrada, "ISO-8859-1")); 
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

		
}
