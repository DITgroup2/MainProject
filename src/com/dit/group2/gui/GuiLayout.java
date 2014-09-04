package com.dit.group2.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GuiLayout extends JPanel implements ComponentListener{
	
	protected JPanel mainPanel,outerPanel;
	protected int xSize;
	protected int ySize;
	protected int xPosition;
	protected int yPosition;
	protected JLabel titleLabel;
	
	
	public GuiLayout(){
		addComponentListener(this);
		xSize= 530;
		ySize = 350;	
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		outerPanel = new JPanel();
		outerPanel.setLayout(null);
		outerPanel.add(mainPanel);
		titleLabel = new JLabel("Supply Order Form",SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
		titleLabel.setForeground(Color.GRAY);
		outerPanel.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168),8,true));
		setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168),4));
		add(outerPanel, new GridBagConstraints());
		add(titleLabel);
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		xPosition = ((((int) mainPanel.getParent().getSize().getWidth())-xSize)/2);
		yPosition = ((((int) mainPanel.getParent().getSize().getHeight())-ySize)/2);
		mainPanel.setBounds(xPosition, yPosition, xSize, ySize);
		
		xPosition = ((((int) outerPanel.getParent().getSize().getWidth())-xSize)/2);
		yPosition = ((((int) outerPanel.getParent().getSize().getHeight())-ySize)/2)+40;
		outerPanel.setBounds(xPosition-50, yPosition-40, xSize+100, ySize+80);
		titleLabel.setBounds((xPosition-50)+(xSize+100-titleLabel.getWidth())/2, yPosition-120, 400, 60);	
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}
}
