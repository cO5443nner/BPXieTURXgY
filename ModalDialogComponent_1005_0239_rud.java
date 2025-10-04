// 代码生成时间: 2025-10-05 02:39:24
// ModalDialogComponent.java
// 模态对话框组件，使用Java Swing构建图形用户界面
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModalDialogComponent {

    // 主框架
    private JFrame frame;
    // 模态对话框
    private JDialog dialog;

    // 构造函数，初始化组件
    public ModalDialogComponent() {
        initialize();
    }

    // 初始化方法
    private void initialize() {
        // 初始化主框架
        frame = new JFrame("Modal Dialog Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // 初始化模态对话框
        dialog = new JDialog(frame, "Modal Dialog", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new FlowLayout());

        // 添加按钮，点击时显示模态对话框
        JButton showDialogButton = new JButton("Show Dialog");
        showDialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });
        frame.getContentPane().add(showDialogButton);

        // 添加文本框到对话框中
        JTextField textField = new JTextField(20);
        dialog.add(textField);

        // 添加确认按钮到对话框中
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmDialog();
            }
        });
        dialog.add(confirmButton);

        // 显示主框架
        frame.setVisible(true);
    }

    // 显示模态对话框的方法
    private void showDialog() {
        dialog.setVisible(true);
    }

    // 确认对话框的方法
    private void confirmDialog() {
        String input = ((JTextField)dialog.getComponent(0)).getText();
        if(input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter some text.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "You entered: " + input, "Info", JOptionPane.INFORMATION_MESSAGE);
            dialog.setVisible(false);
        }
    }

    // 主方法，程序入口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ModalDialogComponent();
            }
        });
    }
}
