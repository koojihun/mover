package com.newSystem;

import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.newSystem.Dialogs.*;
import com.newSystem.Dialogs.InfoDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class TopPanelButtons {
    private JButton stockBtn;           //  재고 표시
    private JButton informationBtn;     //  정보 표시 (getinfo, getpeerinfo)
    private JButton addressBtn;         //  저장한 address를 표시
    private Vector<JButton> buttons;
    private ClickListener clickListener;
    public JPanel topPanel;

    TopPanelButtons(JPanel parent) {
        topPanel = parent;
        clickListener = new ClickListener();
        buttons = new Vector<JButton>();

        // For Image load (get the path of the image files)
        // First, get the path of the class file
        // Second, and then add the "Font/img.png"
        ClassLoader classLoader = getClass().getClassLoader();
        buttons.add(stockBtn = new JButton( "STOCK", new ImageIcon(classLoader.getResource("btn_stock.png"))));
        buttons.add(informationBtn = new JButton("INFO", new ImageIcon(classLoader.getResource("btn_info.png"))));
    }
    void addBtns() {
        LinearConstraints linearConstraints = new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT);
        for (int cnt = 0; cnt < buttons.size(); cnt++) {
            JButton tmp = buttons.get(cnt);
            topPanel.add(tmp, linearConstraints);
            tmp.addActionListener(clickListener);
        }
        setAllBtnsBorder(false);
        setAllBtnsSize(70, 80);
        setAllBtnsFocusPainted(false);
        setAllBtnsFont();
    }
    void setAllBtnsFont() {
        for (int cnt = 0; cnt < buttons.size(); cnt++) {
            buttons.get(cnt).setFont(Settings.Font14);
            buttons.get(cnt).setIconTextGap(6);
            buttons.get(cnt).setVerticalTextPosition(SwingConstants.BOTTOM);
            buttons.get(cnt).setHorizontalTextPosition(SwingConstants.CENTER);
        }
    }
    void setAllBtnsBorder(boolean b) {
        if (!b) {
            for (int cnt = 0; cnt < buttons.size(); cnt++)
                buttons.get(cnt).setBorderPainted(false);
        }
    }
    void setAllBtnsSize(int x, int y) {
        Dimension tmp = new Dimension(x, y);
        for (int cnt = 0; cnt < buttons.size(); cnt++)
            buttons.get(cnt).setPreferredSize(tmp);
    }
    void setAllBtnsFocusPainted(boolean b) {
        if (!b) {
            for (int cnt = 0; cnt < buttons.size(); cnt++)
                buttons.get(cnt).setFocusPainted(false);
        }
    }

    ///////////////////////////////////////////////////////////////////////
    class ClickListener implements ActionListener {
        ClickListener() {}
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clicked = (JButton) e.getSource();
            if (clicked == stockBtn)
                MidPanel.getCurrentProducts();
            else if (clicked == informationBtn)
                new InfoDialog();
        }
    }
}

