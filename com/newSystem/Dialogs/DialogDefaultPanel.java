package com.newSystem.Dialogs;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.newSystem.Settings;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DialogDefaultPanel extends JPanel {
    public enum DIALOG {
        INFO, SIGNUP
    }

    public ArrayList<JPanel> eachLine; // if total = 4 Lines, 4th line will contain ok & cancel buttons.
    public ArrayList<JLabel> eachLabel;
    public ArrayList<JTextField> eachText;
    JButton okBtn;
    JButton cancelBtn;
    JButton trackBtn;
    int padding;
    DIALOG dialog;
    ClickListener clickListener;

    DialogDefaultPanel(int padding, DIALOG dialog) {
        this.padding = padding;
        eachLine = new ArrayList<JPanel>();
        eachLabel = new ArrayList<JLabel>();
        eachText = new ArrayList<JTextField>();
        this.dialog = dialog;
        setLayout(new LinearLayout(Orientation.VERTICAL, 10));
        clickListener = new ClickListener();
    }

    public void makeEmptyLine() {
        JPanel targetLine;
        eachLine.add(targetLine = new JPanel(new LinearLayout(Orientation.HORIZONTAL, 10)));
        targetLine.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(targetLine, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
    }

    public void makePasswordLine(String l) {
        JPanel targetLine;
        eachLine.add(targetLine = new JPanel(new LinearLayout(Orientation.HORIZONTAL, 10)));
        JLabel targetLabel;
        eachLabel.add(targetLabel = new JLabel(l));

        JPasswordField targetText = new JPasswordField();
        eachText.add(targetText);

        targetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        targetLabel.setVerticalAlignment(SwingConstants.CENTER);
        targetLabel.setFont(Settings.Font14);
        targetText.setFont(Settings.Font14);

        targetLine.add(targetLabel, new LinearConstraints().setWeight(4).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        targetLine.add(targetText, new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        targetLine.setBorder(BorderFactory.createEmptyBorder(padding + 10, padding, padding, padding));
        add(targetLine, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
    }
    public void makeExplainLine(String s) {
        JPanel targetLine;
        JLabel targetLabel;
        eachLine.add(targetLine = new JPanel(new LinearLayout(Orientation.VERTICAL, 10)));
        eachLabel.add(targetLabel = new JLabel(s));
        eachText.add(new JTextField());
        targetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        targetLabel.setVerticalAlignment(SwingConstants.CENTER);
        targetLabel.setFont(Settings.Font18);
        targetLine.add(targetLabel, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        targetLine.setBorder(BorderFactory.createEmptyBorder(padding + 10, padding, padding, padding));
        add(targetLine, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
    }
    public void makeNonEmptyLine(String l, String t, boolean textFieldChange) {
        JPanel targetLine;
        eachLine.add(targetLine = new JPanel(new LinearLayout(Orientation.HORIZONTAL, 10)));
        JLabel targetLabel;
        eachLabel.add(targetLabel = new JLabel(l));
        JTextField targetText;
        eachText.add(targetText = new JTextField());

        targetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        targetLabel.setVerticalAlignment(SwingConstants.CENTER);
        targetLabel.setFont(Settings.Font14);
        targetText.setFont(Settings.Font14);
        targetText.setText(t);

        if (!textFieldChange)
            targetText.setEditable(false);

        targetLine.add(targetLabel, new LinearConstraints().setWeight(4).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        targetLine.add(targetText, new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        targetLine.setBorder(BorderFactory.createEmptyBorder(padding + 10, padding, padding, padding));
        add(targetLine, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
    }

    public void makeButtonLine() {
        JPanel targetLine;
        eachLine.add(targetLine = new JPanel(new LinearLayout(Orientation.HORIZONTAL, 10)));

        targetLine.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        targetLine.add(okBtn = new JButton("OK"), new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
        okBtn.setFont(Settings.Font14);
        okBtn.setFocusPainted(false);
        okBtn.addActionListener(clickListener);

        add(targetLine, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
    }

    class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clicked = (JButton) e.getSource();
            // Info창은 버튼이 1개이므로 눌리면 바로 종료.
            if (dialog == DIALOG.INFO)
                // 해당 다이얼로그 종료.
                SwingUtilities.getWindowAncestor(clicked).dispose();
        }
    }
}
