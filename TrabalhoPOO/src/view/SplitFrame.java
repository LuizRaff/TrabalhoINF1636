package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

class SplitFrame extends JFrame {
	private PainelCartas painelCartas;
	private JLabel labelPontos;
	private JLabel rodaPe;

	/* vvvvvvvvv EXEMPLO SINGLETON vvvvvvvvv */
	private static SplitFrame instance;

	private SplitFrame() {
	}

	public static synchronized SplitFrame getInstance() {
		if (instance == null) {
			instance = new SplitFrame();
		}

		return instance;
	}
	/* ^^^^^^^^^ EXEMPLO SINGLETON ^^^^^^^^^ */

	public void initComponents() {
		painelCartas = new PainelCartas();
		setLayout(new BorderLayout());
		addLabel();
		add(painelCartas, BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		FacadeView facadeView = FacadeView.getInstance();
		setBounds(facadeView.getJanelaPrincipalBounds().x + 1050, 333, 205, 350);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void updateCartas(String tipo) {
		limpaPainel();
		FacadeView facadeView = FacadeView.getInstance();
		ArrayList<String> playerMao = facadeView.importaCarta(tipo);
		ArrayList<String> cartasPlayer = new ArrayList<>();

		for (int i = 0; i < playerMao.size(); i += 2) {
			cartasPlayer.add("src/cartasImg/" + playerMao.get(i + 1) + playerMao.get(i) + ".gif");
		}

		// Carrega as imagens e configura no painel
		List<Image> cardImages = new ArrayList<>();
		for (String caminho : cartasPlayer) {
			try {
				ImageIcon icon = new ImageIcon(caminho);
				Image img = icon.getImage();
				cardImages.add(img);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		painelCartas.setCardImages(cardImages);
	}

	public void updatePontos() {
		FacadeView facadeView = FacadeView.getInstance();
		labelPontos.setText("Pontos: " + facadeView.getSomaCarta("Split"));
	}
	
	public void updateRodaPe(boolean bool) {
		if(bool) {
			this.rodaPe.setText("YOUR TURN");
		}else {
			this.rodaPe.setText("");
		}
	}

	public void addLabel() {
		labelPontos = new JLabel("Pontos: ", SwingConstants.CENTER);
		labelPontos.setVerticalAlignment(SwingConstants.TOP);
		add(labelPontos, BorderLayout.NORTH);

		rodaPe = new JLabel("YOUR TURN", SwingConstants.CENTER);
		rodaPe.setVerticalAlignment(SwingConstants.BOTTOM);
		add(rodaPe, BorderLayout.SOUTH);
	}

	public void limpaPainel() {
		painelCartas.setCardImages(null); // Limpa as imagens
	}

	public void deleteFrame() {
		remove(painelCartas);
		remove(labelPontos);
		remove(rodaPe);
		dispose();
	}
}
