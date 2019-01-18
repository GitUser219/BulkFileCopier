package automation.filecopier;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class BulkFileCopier {
	public static String source = "";
	public static String destination = "";
	public static JButton sourceButton;
	public static JButton destinationButton;
	public static JButton copyButton;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		Font primaryFont = new Font("", Font.PLAIN, 16);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setIconImage(new ImageIcon(ClassLoader.getSystemResource("images/logo.png")).getImage());
		frame.setLocation(screenSize.width / 2 - 197, screenSize.height / 2 - 130);
		frame.setTitle("Bulk File Copier");
		frame.setSize(395, 260);
		frame.setLayout(null);
		
		ImageIcon ready = new ImageIcon(ClassLoader.getSystemResource("images/ready.png"));
		ImageIcon notReady = new ImageIcon(ClassLoader.getSystemResource("images/not_ready.png"));
		
		JLabel sourceIcon = new JLabel(notReady);
		sourceIcon.setBounds(320, 25, 30, 30);
		frame.add(sourceIcon);
		
		JLabel destinationIcon = new JLabel(notReady);
		destinationIcon.setBounds(320, 85, 30, 30);
		frame.add(destinationIcon);
		
		sourceButton = new JButton(new AbstractAction("sourceAction") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser sourceChooser = new JFileChooser();
				sourceChooser.setDialogTitle("Select the source");
				sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if (sourceChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && sourceChooser.getSelectedFile().exists()) {
					source = sourceChooser.getSelectedFile().getPath() + "\\";
					sourceIcon.setIcon(ready);
					destinationButton.setEnabled(true);
				}
			}
		});
		sourceButton.setText("Select the source");
		sourceButton.setFont(primaryFont);
		sourceButton.setBounds(20, 20, 280, 40);
		frame.add(sourceButton);
		
		destinationButton = new JButton(new AbstractAction("destinationAction") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser destinationChooser = new JFileChooser();
				destinationChooser.setDialogTitle("Select the destination");
				destinationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if (destinationChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && destinationChooser.getSelectedFile().exists()) {
					destination = destinationChooser.getSelectedFile().getPath() + "\\";
					destinationIcon.setIcon(ready);
					copyButton.setEnabled(true);
				}
			}
		});
		destinationButton.setText("Select the destination");
		destinationButton.setFont(primaryFont);
		destinationButton.setBounds(20, 80, 280, 40);
		destinationButton.setEnabled(false);
		frame.add(destinationButton);
		
		copyButton = new JButton(new AbstractAction("copyAction") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent event) {				
				if (JOptionPane.showConfirmDialog(frame, "You are about to copy all files from\n\n" + source + "\nto " + destination +"\n\nAll files in this directory with existing names will be overwritten\n\nWould you like to continue?", "Continue?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						ArrayList<ArrayList<String>> input = getFilesInDirectory(source);
						ArrayList<String> output = new ArrayList<String>();
						
						if (input.get(0).size() > 0) {
							for (int i = 0; i < input.get(0).size(); i++) {
								output.add(input.get(0).get(i));
							}
							
							boolean changeMade;
							
							do {
								changeMade = false;
								
								for (int i = 0; i < output.size(); i++) {
									String fileName = output.get(i);
									int count = 1;
									for (int j = i + 1; j < output.size(); j++) {
										if (fileName.equals(output.get(j))) {
											output.set(j, "(" + count++ + ")" + output.get(j));
											changeMade = true;
										}
									}
								}
							} while (changeMade == true);
							
							for (int i = 0; i < input.get(0).size(); i++) {
								copyFile(input.get(1).get(i) + input.get(0).get(i), destination + output.get(i));
							}
							
							JOptionPane.showMessageDialog(null, "The files have been successfully renamed and copied from\n" + source + "\nto " + destination, "Success", JOptionPane.PLAIN_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "There are no files in " + source, "Failure", JOptionPane.PLAIN_MESSAGE);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e, "Failure", JOptionPane.PLAIN_MESSAGE);
					}
					
					source = "";
					sourceIcon.setIcon(notReady);
					destination = "";
					destinationIcon.setIcon(notReady);
					destinationButton.setEnabled(false);
					copyButton.setEnabled(false);
				}
			}
		});
		copyButton.setText("Copy");
		copyButton.setFont(primaryFont);
		copyButton.setBounds(20, 140, 330, 40);
		copyButton.setEnabled(false);
		frame.add(copyButton);
		frame.setVisible(true);
	}
	
	private static ArrayList<ArrayList<String>> getFilesInDirectory(String directory) {
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<String> directoryList = new ArrayList<String>();
		
		String[] files = new File(directory).list(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
				  return !(new File(current, name).isDirectory());
			  }
		});
		
		String[] folders = new File(directory).list(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
				  return new File(current, name).isDirectory();
			  }
		});
		
		for (String folder : folders) {
			ArrayList<ArrayList<String>> temporaryList = getFilesInDirectory(directory + folder + "\\");
			fileList.addAll(temporaryList.get(0));
			directoryList.addAll(temporaryList.get(1));
		}
		
		for (String file : files) {
			fileList.add(file);
			directoryList.add(directory);
		}
		
		ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		finalList.add(fileList);
		finalList.add(directoryList);
		
		return finalList;
	}
	
	@SuppressWarnings("resource")
	private static void copyFile(String source, String destination) throws Exception {
	    FileChannel sourceChannel = new FileInputStream(new File(source)).getChannel();
	    FileChannel destinationChannel = new FileOutputStream(new File(destination)).getChannel();
	    destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	    sourceChannel.close();
	    destinationChannel.close();
	}
}
