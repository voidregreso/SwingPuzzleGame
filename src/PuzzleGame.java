import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PuzzleGame extends JFrame {
    private JPanel centerPanel;
    private JButton emptyBtn;
    private final JPanel topPanel;

    public PuzzleGame() {
        super();
        setResizable(false);
        setTitle("Puzzle Game (5x5)");
        setResizable(false);
        setBounds(100, 100, 280, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //////////////////////
        topPanel = new JPanel();
        topPanel.setBorder(new TitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null)); // add thick black border for panel
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        initReference(); // 1
        initPuzzleBtns(); // 2
    }

    private void initReference() {
        //////////////////////
        final JLabel modelLabel = new JLabel();
        modelLabel.setIcon(new ImageIcon("img/model.jpg"));
        modelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(modelLabel, BorderLayout.NORTH);
        //////////////////////
        final JButton nextBtn = new JButton();
        nextBtn.setText("Next Round");
        nextBtn.addActionListener(e -> {
            String[][] placementOrder = reorder();
            int i = 0;
            for(int r = 0; r < 5; r++) {
                for(int c = 0; c < 5; c++) {
                    JButton jbtn = (JButton) centerPanel.getComponent(i++);
                    jbtn.setIcon(new ImageIcon(placementOrder[r][c]));
                    if(placementOrder[r][c].equals("img/00.jpg")) emptyBtn = jbtn;
                }
            }
        });
        topPanel.add(nextBtn, BorderLayout.CENTER);
    }

    private void initPuzzleBtns() {
        centerPanel = new JPanel();
        centerPanel.setBorder(new TitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        centerPanel.setLayout(new GridLayout(0, 5));
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        String[][] placementOrder = reorder();
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                final JButton jbtn = new JButton();
                jbtn.setName(r+""+c);
                jbtn.setIcon(new ImageIcon(placementOrder[r][c]));
                if(placementOrder[r][c].equals("img/00.jpg")) emptyBtn = jbtn;
                jbtn.addActionListener(new ImgBtnActionListener());
                centerPanel.add(jbtn);
            }
        }
    }

    private String[][] reorder() {
        Random rd = new Random();
        String[][] correctOrder = new String[5][5];
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                correctOrder[r][c] = "img/" + r + "" + c + ".jpg";
            }
        }
        String[][] placementOrder = new String[5][5];
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                while(placementOrder[r][c] == null) {
                    int rr = rd.nextInt(5);
                    int cc = rd.nextInt(5);
                    if(correctOrder[rr][cc] != null) {
                        placementOrder[r][c] = correctOrder[rr][cc];
                        correctOrder[rr][cc] = null;
                    }
                }
            }
        }
        return placementOrder;
    }

    private class ImgBtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String emptyName = emptyBtn.getName();
            char emptyR = emptyName.charAt(0);
            char emptyC = emptyName.charAt(1);
            JButton clkBtn = (JButton) e.getSource();
            String clkName = clkBtn.getName();
            char clkR = clkName.charAt(0);
            char clkC = clkName.charAt(1);
            if(Math.abs(clkR - emptyR) + Math.abs(clkC - emptyC) == 1) {
                emptyBtn.setIcon(clkBtn.getIcon());
                clkBtn.setIcon(new ImageIcon("img/00.jpg"));
                emptyBtn = clkBtn;
            }
        }
    }

    public static void main(String[] args) {
        PuzzleGame frm = new PuzzleGame();
        frm.setVisible(true);
    }
}
