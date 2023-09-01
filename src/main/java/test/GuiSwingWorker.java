package test;

import javax.swing.*;
import dto.RcvTrxEbBatchItemDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GuiSwingWorker extends JFrame {
    private JButton startButton;
    private JTextArea textArea;
    private int t = 0;
    public GuiSwingWorker() 
    {
        setTitle("SwingWorker Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        

        startButton = new JButton("Start Task");
        textArea = new JTextArea();

        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTask(t++);
            }
        });

        getContentPane().add(startButton, "North");
        getContentPane().add(new JScrollPane(textArea), "Center");
    }

    private void startTask(int idx) {
        SwingWorker<Void, RcvTrxEbBatchItemDTO> worker = new SwingWorker<Void, RcvTrxEbBatchItemDTO>() {
            @Override
            protected Void doInBackground() throws Exception 
            {
                // Simulate a long-running task
                for (int i = 1; i <= 10; i++) {
                    Thread.sleep(1000);
                    //publish(idx+" : Task progress: " + i + " seconds");
                    RcvTrxEbBatchItemDTO dto = new RcvTrxEbBatchItemDTO();
                    dto.setCdc("01289382934 "+i);
                    dto.setIdVenta(Long.parseLong(i+""));
                    publish(dto);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<RcvTrxEbBatchItemDTO> chunks) {
                // Update the GUI with progress updates
                for (RcvTrxEbBatchItemDTO message : chunks) {
                    //textArea.append(message + "\n");
                	textArea.setText(message.getIdVenta()+"  "+message.getCdc());
                }
            }

            @Override
            protected void done() {
                textArea.append("Task completed!\n");
            }
        };

        worker.execute(); // Start the SwingWorker
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiSwingWorker gui = new GuiSwingWorker();
                gui.setVisible(true);
            }
        });
    }
}
