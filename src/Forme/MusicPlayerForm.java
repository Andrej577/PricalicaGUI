package Forme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import API.MainAPI;
import POCO.WavFilePOCO;
import Util.MP3Player;

public class MusicPlayerForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
    private MP3Player player = new MP3Player();
    private WavFilePOCO wavFilePoco = new WavFilePOCO();
	private File wavFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MusicPlayerForm dialog = new MusicPlayerForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadAudioFile(JLabel lblNewLabel_1, JLabel lblNewLabel_2)
	{
		MainAPI api = new MainAPI();
		wavFile = api.GetWavStream();
    	wavFilePoco = player.getFileInfo(wavFile);
    	lblNewLabel_1.setText(wavFilePoco.Duzina);
    	lblNewLabel_2.setText(wavFilePoco.Ime);
    	player.ucitajAsync(wavFile);
	}
	
	/**
	 * Create the dialog.
	 */
	public MusicPlayerForm() {
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(483, 292, 94, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(173, 35, 238, 55);
		contentPanel.add(lblNewLabel_2);
		
		setBounds(100, 100, 603, 453);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("Pokreni");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					        if (wavFile != null)
					        {
					        	if (player.pokrenutoSwitch)
					        	{
					        		player.pauziraj();
					        		btnNewButton.setText("Pokreni");
					        	}
					        	else
					        	{
						        	player.pokreni();
						        	btnNewButton.setText("Pauziraj");
					        	}
					        }
				}
			});
			btnNewButton.setForeground(SystemColor.textHighlight);
			btnNewButton.setBackground(Color.WHITE);
			btnNewButton.setBounds(213, 359, 160, 44);
			contentPanel.add(btnNewButton);
		}
		
		JSlider slider = new JSlider();
		slider.setMaximum(0);
		slider.setBounds(10, 317, 567, 26);
		contentPanel.add(slider);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 292, 80, 14);
		contentPanel.add(lblNewLabel);
		
		loadAudioFile(lblNewLabel_1, lblNewLabel_2);
	}
}
