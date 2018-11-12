package com.teksoftwares;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.TextField;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.awt.Button;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Insets;
import javax.swing.SwingConstants;
import org.apache.commons.io.FilenameUtils;
import java.awt.TextArea;

public class Renamer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Renamer window = new Renamer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void renameFiles(String path, String filePrefix, TextArea outputLog) {
		outputLog.setText("");
		if (path.trim().length() == 0) {
			outputLog.append("Please enter the directory path and try again\n");
		} else if (filePrefix.trim().length() == 0) {
			outputLog.append("Please enter the file prefix and try again\n");
		} else {
			try (Stream<Path> paths = Files.walk(Paths.get(path))) {
				outputLog.append("Processing Folder " + path + "\n");
				paths.filter(Files::isRegularFile).forEach(file -> {
					String epNo = file.getFileName().toString().replaceAll("[^?0-9]+", "");
					String extension = FilenameUtils.getExtension(file.getFileName().toString());
					outputLog.append(file.getFileName().toString() + " is Changed to " + filePrefix + epNo + "."
							+ extension + "\n");
					System.out.println(path + File.separator + filePrefix + epNo + "." + extension);
					file.toFile().renameTo(new File(path + File.separator + filePrefix + epNo + "." + extension));
				});
				outputLog.append("---------------------------------------------------------------\n");
				outputLog.append("All files have been processed.! \n");
				outputLog.append("---------------------------------------------------------------\n");
			} catch (Exception e) {
				outputLog.append(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Create the application.
	 */
	public Renamer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Rename Files");
		frame.setBounds(100, 100, 500, 350);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mnOptions.add(mntmHelp);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnOptions.add(mntmExit);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 24, 143, 143, 168, 0 };
		gridBagLayout.rowHeights = new int[] { 22, 22, 0, 0, 0, 0, 0, 96, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		Label label = new Label("Rename names of randomly generated / downloaded files to proper format.");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTHWEST;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridwidth = 3;
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		frame.getContentPane().add(label, gbc_label);

		JLabel subHeader = DefaultComponentFactory.getInstance().createTitle("Select Options");
		subHeader.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_subHeader = new GridBagConstraints();
		gbc_subHeader.insets = new Insets(0, 0, 5, 5);
		gbc_subHeader.gridx = 2;
		gbc_subHeader.gridy = 2;
		frame.getContentPane().add(subHeader, gbc_subHeader);

		Label folderPathLabel = new Label("Folder Path");
		GridBagConstraints gbc_folderPathLabel = new GridBagConstraints();
		gbc_folderPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_folderPathLabel.anchor = GridBagConstraints.NORTH;
		gbc_folderPathLabel.gridx = 1;
		gbc_folderPathLabel.gridy = 3;
		frame.getContentPane().add(folderPathLabel, gbc_folderPathLabel);

		TextField folderPath = new TextField();
		GridBagConstraints gbc_folderPath = new GridBagConstraints();
		gbc_folderPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_folderPath.insets = new Insets(0, 0, 5, 5);
		gbc_folderPath.gridx = 2;
		gbc_folderPath.gridy = 3;
		frame.getContentPane().add(folderPath, gbc_folderPath);

		Button browseButton = new Button("Browse");
		browseButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);
			int rVal = fileChooser.showOpenDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				folderPath.setText(fileChooser.getSelectedFile().toString());
			}
		});
		GridBagConstraints gbc_browseButton = new GridBagConstraints();
		gbc_browseButton.insets = new Insets(0, 0, 5, 0);
		gbc_browseButton.anchor = GridBagConstraints.WEST;
		gbc_browseButton.gridx = 3;
		gbc_browseButton.gridy = 3;
		frame.getContentPane().add(browseButton, gbc_browseButton);

		Label fileFormatLabel = new Label("FileName Format");
		GridBagConstraints gbc_fileFormatLabel = new GridBagConstraints();
		gbc_fileFormatLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileFormatLabel.gridx = 1;
		gbc_fileFormatLabel.gridy = 4;
		frame.getContentPane().add(fileFormatLabel, gbc_fileFormatLabel);

		TextField filePrefix = new TextField();
		filePrefix.setText("Naruto_Shippuden_Episode_");
		GridBagConstraints gbc_filePrefix = new GridBagConstraints();
		gbc_filePrefix.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePrefix.insets = new Insets(0, 0, 5, 5);
		gbc_filePrefix.gridx = 2;
		gbc_filePrefix.gridy = 4;
		frame.getContentPane().add(filePrefix, gbc_filePrefix);
		frame.setVisible(true);

		TextArea outputLog = new TextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 7;
		outputLog.setText("");
		frame.getContentPane().add(outputLog, gbc_textArea);

		Button executeButton = new Button("Execute");
		GridBagConstraints gbc_exe_button = new GridBagConstraints();
		gbc_exe_button.insets = new Insets(0, 0, 5, 5);
		gbc_exe_button.gridx = 2;
		gbc_exe_button.gridy = 5;
		executeButton.addActionListener(e -> renameFiles(folderPath.getText(), filePrefix.getText(), outputLog));
		frame.getContentPane().add(executeButton, gbc_exe_button);

	}

}
