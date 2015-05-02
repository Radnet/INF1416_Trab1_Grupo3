import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class FrameFileExplorer extends JFrame{

	public JFrame ThisFrame;
	public Container Panel;
	public byte[] Kprivbuffer;
	public File FilePath;
	
	public JPasswordField TXT_SecretPhrase   ;
	
	public String ErrorMessage;
	
	public FrameFileExplorer(String Title)
	{
		super(Title);
		ThisFrame = this;
		setLayout(null);
		
		User user = User.GetUserObj();
		
		Dao dao = new Dao();
		
		/*****  Setting the attributes of the Frame *****/
		
		//********** HEADER*******************************
  		JLabel LB_Login         = new JLabel("Login: " + user.getLogin());
  		JLabel LB_Grupo         = new JLabel("Grupo: " + user.getGrupo());
  		JLabel LB_Decricao      = new JLabel("Descri��o: " + user.getDescricao());
  		JLabel LB_Consults      = new JLabel("Total de Consultas: " + dao.GetUserConsults(user.getLogin()));
  		JLabel LB_ArchiveSystem = new JLabel("Sistema de Arquivos Secretos");
  		//************************************************
  		
  		//**************** FORM **************************
  		
  		JLabel LB_UserKPrivPath = new JLabel("Caminho Chave Privada:");
  		JLabel LB_SecretPhrase  = new JLabel("Frase Secreta:");                
  		JLabel LB_FolderPath    = new JLabel("Caminho da Pasta:");     
  		
  		TXT_SecretPhrase        = new JPasswordField();  		
  		
  		JButton BTN_ShowFiles    = new JButton("Listar");
  		JButton BTN_KprivChooser = new JButton(">");
  		JButton BTN_FileChooser  = new JButton(">");
  		
  		JButton BTN_Back         = new JButton("Voltar");
  		
  		final JFileChooser KprivChooser    = new JFileChooser();
  		final JFileChooser FilePathChooser = new JFileChooser();
  		
  		FilePathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  		
  		//***********************************************
  		
  		
		/***********************************************/
  		
  		Panel =  getContentPane();
  		
  		/*****  Adjusting the size of attributes *****/
  		
  		LB_Login         .setBounds (10,5,  350,25);
  		LB_Grupo         .setBounds (10,25, 350,25);
  		LB_Decricao      .setBounds (10,45, 350,25);
  		LB_Consults      .setBounds (10,65, 350,25);
  		LB_ArchiveSystem .setBounds (10,105,350,25);
  		                
  		LB_UserKPrivPath .setBounds (10,125,350,25);    
  		LB_SecretPhrase  .setBounds (10,155,350,25);   
  		LB_FolderPath    .setBounds (10,185,350,25); 
  		
  		BTN_KprivChooser .setBounds (160,125,50,25);
  		TXT_SecretPhrase .setBounds (160,155,350,25);
  		BTN_FileChooser  .setBounds (160,185,50,25);
  		
  		BTN_ShowFiles    .setBounds (10 ,220,100,25);
  		BTN_Back         .setBounds (120,220,100,25);
  		
		/*********************************************/

  		/*****  Adding attributes to the  panel *****/
  		
  		Panel.add(LB_Login);
  		Panel.add(LB_Grupo);
  		Panel.add(LB_Decricao);
  		Panel.add(LB_Consults);
  		Panel.add(LB_ArchiveSystem);
  		   
  		Panel.add(LB_SecretPhrase);   
  		Panel.add(LB_UserKPrivPath);
  		Panel.add(LB_FolderPath);
  		
  		Panel.add(BTN_KprivChooser);
  		Panel.add(TXT_SecretPhrase);
  		Panel.add(BTN_FileChooser);
  		
  		Panel.add(BTN_ShowFiles);
  		Panel.add(BTN_Back);
  		
  		/********************************************/
  		
  		/*******************  Setting listeners *************************/
  		
  		BTN_KprivChooser.addActionListener( new ActionListener () {
  		    public void actionPerformed(ActionEvent e) 
  		    {
  		      if (KprivChooser.showOpenDialog(ThisFrame) == JFileChooser.APPROVE_OPTION) { 
  		    	  	try {
  		    	  		Kprivbuffer = new byte[1024];
  		    	  		
						InputStream is = new FileInputStream(KprivChooser.getSelectedFile());
						is.read(Kprivbuffer);
						
						is.close();
						
					} catch(IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  		        }
  		    }
  		    
  		  });
  		
  		BTN_FileChooser.addActionListener( new ActionListener () {
  		    public void actionPerformed(ActionEvent e) 
  		    {
  		      if (FilePathChooser.showOpenDialog(ThisFrame) == JFileChooser.APPROVE_OPTION) { 
  		    	  	try {
  		    	  		
						FilePath = FilePathChooser.getCurrentDirectory();
						
					} catch(Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  		        }
  		    }
  		    
  		  });
  		
  		BTN_Back.addActionListener( new ActionListener () {
  		    public void actionPerformed(ActionEvent e) 
  		    {
    			// Open new user frame
				FrameMenu FM_Menu = new FrameMenu("Frame Menu");
				FM_Menu.setVisible(true);
				
				// Close this frame
	    		ThisFrame.dispose();
  		    }
  		    
  		  });
  		
  		BTN_ShowFiles.addActionListener( new ActionListener () {
  		    public void actionPerformed(ActionEvent e) 
  		    {
  		    	// Verify fields
  		    	if(IsAllFieldsOK())
  		    	{
  		    	
			    	// Increment consult on DB
			    	dao.IncrementConsult(user.getLogin());
			    	LB_Consults.setText("Total de Consultas: " + dao.GetUserConsults(user.getLogin()));
			    	
			    	// 
			    	
			    	
			    	String[] columnNames = {"Nome", "Hexa AssD", "Hexa EnvD", "Status"};
			    	
			  		Object[][] data = {	};
			  		
			    	JTable table = new JTable(data, columnNames);
			    	table.setFillsViewportHeight(true);
			    	JScrollPane scrollPane = new JScrollPane(table);
			    	scrollPane.setBounds (10,260,780,300);
			    	Panel.add(scrollPane);
			    	
			    	
			    	Panel.revalidate();
			    	Panel.repaint();
  		    	}
  		    	else
  		    	{
  		    		JOptionPane.showMessageDialog(ThisFrame, ErrorMessage);
  		    	}
  		    	
  		    }
		  });
  	
  		/****************************************************************/
  	
  		/*********************** Centralizing the frame on the screen  *********************/
  		
  		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
  		setBounds((screenSize.width-890)/2, (screenSize.height-670)/2, 800, 600);

  		/***********************************************************************************/
  		
  		// Makes the size of the screen unchangeable
  		setResizable(false);  
  		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public boolean IsAllFieldsOK()
	{
		if(Kprivbuffer == null)
		{
			ErrorMessage = "Por favor, selecione o caminho da chave privada";
			return false;
		}
		if(TXT_SecretPhrase.getText().equals(""))
		{
			ErrorMessage = "Por favor, insira a frase secreta";
			return false;
		}
		if(FilePath == null)
		{
			ErrorMessage = "Por favor, selecione o caminho da pasta";
			return false;
		}
		
		return true;
	}
	
	public PrivateKey GetPrivateKey()
	{
		return null;
	}
}
