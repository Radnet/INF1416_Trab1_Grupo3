
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		//FrameLogin FrameLogin = new FrameLogin("Seja bem-vindo ao Trab3 de seguranša!");
		//FrameLogin.setVisible(true);
		
		//******************************************************************
		//TESTE
		User user = User.GetUserObj();
		user.setLogin("rafa");
		
		SharedLibrary.FillUserProps("rafa");
		
		FrameMenu frameMenu = new FrameMenu("Frame Menu");
		frameMenu.setVisible(true);
		//**********************************************
	}
}
