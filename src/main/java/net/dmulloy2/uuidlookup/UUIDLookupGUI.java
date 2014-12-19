/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.uuidlookup;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.dmulloy2.uuidlookup.types.Constants;
import net.dmulloy2.uuidlookup.types.NameFetcher;
import net.dmulloy2.uuidlookup.types.UUIDFetcher;

/**
 * @author dmulloy2
 */

public class UUIDLookupGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField inputField;
	private JTextField outputField;

	public UUIDLookupGUI() {
		super(Constants.NAME);

		initComponents();
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				setVisible(true);
			}

		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private final void initComponents() {
		JLabel name = new JLabel(Constants.getFullName());
		
		JLabel lblNameUuid = new JLabel("Name / UUID:");
		
		inputField = new JTextField();
		inputField.setColumns(10);
		inputField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				lookup();
			}

		});
		
		JLabel lblResult = new JLabel("Result:");
		
		outputField = new JTextField();
		outputField.setColumns(10);
		
		JButton btnLookup = new JButton("Lookup");
		btnLookup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				lookup();
			}

		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(name)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNameUuid)
								.addComponent(lblResult))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(outputField, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
								.addComponent(inputField, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))
						.addComponent(btnLookup, Alignment.TRAILING))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(name)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNameUuid)
						.addComponent(inputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResult)
						.addComponent(outputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLookup)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		setSize(400, 175);
	}

	private final void lookup() {
		String text = inputField.getText();
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must specify a name or UUID!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (text.length() == 36) {
			UUID uuid;

			try {
				uuid = UUID.fromString(text);
			} catch (Throwable ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Invalid UUID format!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Map<UUID, String> results;

			try {
				NameFetcher fetcher = new NameFetcher(Arrays.asList(uuid));
				results = fetcher.call();
			} catch (Throwable ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Failed to fetch name:\n\t" + ex, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (results.size() == 0) {
				outputField.setText("No name found.");
			} else {
				outputField.setText(results.get(uuid));
			}
		} else {
			Map<String, UUID> results;

			try {
				UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(text));
				results = fetcher.call();
			} catch (Throwable ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Failed to fetch UUID:\n\t" + ex, "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (results.size() == 0) {
				outputField.setText("No UUID found.");
			} else {
				outputField.setText(results.get(text).toString());
			}
		}
	}
}