package com.br.analisadorlexico.utils;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.br.analisadorlexico.analisadores.AnLexico;
import com.br.analisadorlexico.analisadores.AnSintatico;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 899, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JLabel lblInsiraOCaminho = new JLabel("Insira o caminho do arquivo a ser analisado.");
		
		JLabel lblAnalisadorLxico = new JLabel("Analisador Vagalumes");
		lblAnalisadorLxico.setForeground(SystemColor.desktop);
		lblAnalisadorLxico.setFont(new Font("Dialog", Font.BOLD, 30));

		textField = new JTextField();
		textField.setColumns(10);
		textField.setText("");
		
		final AnSintatico anSintatico = new AnSintatico();
		
		final JLabel sucesso = new JLabel("Código validado com sucesso.");
		sucesso.setForeground(UIManager.getColor("OptionPane.questionDialog.border.background"));
		sucesso.setVisible(false);
		
		final JLabel problemas = new JLabel("Código com erros. Veja o log em ");
		problemas.setForeground(UIManager.getColor("OptionPane.errorDialog.border.background"));
		problemas.setVisible(false);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    JFileChooser abrir = new JFileChooser();  
			    int retorno = abrir.showOpenDialog(null);
			    String caminho = "";
			    sucesso.setVisible(false);
			    problemas.setVisible(false);
			               if (retorno == JFileChooser.APPROVE_OPTION){
			                     caminho = abrir.getSelectedFile().getAbsolutePath();  
			                     textField.setText(caminho);   
			               }
			}
		});
		
		JButton btnAnalise = new JButton("Começar análise!");
		btnAnalise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Caminho do arquivo não encontrado");
				else{
				boolean codigoOk = anSintatico.analise(textField.getText());
				
				int indice = AnLexico.caminhoArquivo.lastIndexOf("/");
				String caminhoArquivo = AnLexico.caminhoArquivo.substring(0, indice) ;
				
				if(codigoOk){
					
					sucesso.setVisible(true);
				}
				else{
					problemas.setText("Código com erros. Veja o log em "+caminhoArquivo);
					problemas.setVisible(true);
				}
				}
			}
		});
		
	
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAnalisadorLxico)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(sucesso)
											.addGap(198))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblInsiraOCaminho)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addComponent(btnSelecionar))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(341)
							.addComponent(btnAnalise))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(325)
							.addComponent(problemas)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAnalisadorLxico)
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInsiraOCaminho)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelecionar))
					.addGap(29)
					.addComponent(btnAnalise)
					.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
					.addComponent(sucesso)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(problemas)
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
