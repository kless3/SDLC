package by.bsuir.glycemicindex.view;

import by.bsuir.glycemicindex.model.ProductInput;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/** Modal product-input window. */
public final class InputDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JTextField productField = new JTextField(24);
    private final JButton submitButton = new JButton("Найти");

    public InputDialog(Frame owner, ProductInput previousInput) {
        super(owner, "Ввод продукта", true);
        configureWindow();
        setContentPane(createContent());
        restore(previousInput);
        pack();
        setMinimumSize(new Dimension(460, getHeight()));
        setLocationRelativeTo(owner);
    }

    public String getProductName() {
        return productField.getText();
    }

    public void onSubmit(Runnable action) {
        submitButton.addActionListener(event -> action.run());
        productField.addActionListener(event -> action.run());
    }

    public void open() {
        SwingUtilities.invokeLater(productField::requestFocusInWindow);
        setVisible(true);
    }

    public void close() {
        dispose();
    }

    private void configureWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getRootPane().setDefaultButton(submitButton);
        getRootPane().registerKeyboardAction(
                event -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 14, 16));
        content.add(createForm(), BorderLayout.CENTER);
        content.add(createButtons(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.anchor = GridBagConstraints.LINE_START;

        constraints.gridx = 0;
        constraints.gridy = 0;
        form.add(new JLabel("Продукт:"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        form.add(productField, constraints);

        JLabel hint = new JLabel("Например: яблоко, банан, белый рис");
        hint.setForeground(java.awt.Color.GRAY);
        constraints.gridy = 1;
        form.add(hint, constraints);
        return form;
    }

    private JPanel createButtons() {
        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(this::cancel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.add(cancelButton);
        buttons.add(submitButton);
        return buttons;
    }

    private void restore(ProductInput previousInput) {
        if (previousInput != null) {
            productField.setText(previousInput.productName());
        }
    }

    private void cancel(ActionEvent event) {
        dispose();
    }

}
