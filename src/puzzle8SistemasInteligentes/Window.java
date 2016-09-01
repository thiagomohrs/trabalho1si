package puzzle8SistemasInteligentes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	public JPanel panel;
	public JButton[][]  matrizJButton;
	public Window(int [][] matriz){
		this.setSize(300,300);
		componentes(matriz);
		this.setVisible(true);
	}
	public void componentes(int [][] matriz){
		this.setLayout(new BorderLayout());
		matrizJButton = new JButton[3][3];
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,3));
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(matriz[i][j] == 9){
					matrizJButton[i][j] = new JButton("");
				}else {
					matrizJButton[i][j] = new JButton(String.valueOf(matriz[i][j]));
				}
				panel.add(matrizJButton[i][j]);
			}
		}
		this.add(panel, BorderLayout.CENTER);
	}	
}