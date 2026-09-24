package by.bsuir.glycemicindex.view;

import by.bsuir.glycemicindex.model.GlycemicIndexModel;
import by.bsuir.glycemicindex.model.GlycemicState;
import by.bsuir.glycemicindex.model.ProductInfo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** Main application window and active-model listener. */
public final class MainView extends JFrame implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private final JTextArea productArea = createTextArea();
    private final JTextArea resultArea = createTextArea();
    private final JButton inputButton = new JButton("Ввести продукт");

    public MainView() {
        super("Гликемический индекс продуктов — MVC");
        configureWindow();
        setContentPane(createContent());
        pack();
        setLocationRelativeTo(null);
    }

    public void onInputRequested(ActionListener listener) {
        inputButton.addActionListener(listener);
    }

    public void open() {
        setVisible(true);
    }

    public void showError(Component owner, String message) {
        JOptionPane.showMessageDialog(owner, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }

    public void render(GlycemicState state) {
        if (!state.hasResult()) {
            productArea.setText("Продукт ещё не введён.");
            resultArea.setText("Нажмите «Ввести продукт», чтобы получить результат.");
            return;
        }

        ProductInfo result = state.result();
        productArea.setText("Последний продукт: " + state.input().productName());
        resultArea.setText("Гликемический индекс: %d%nКатегория: %s"
                .formatted(result.glycemicIndex(), result.category()));
        resultArea.setCaretPosition(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (!GlycemicIndexModel.STATE_PROPERTY.equals(event.getPropertyName())) {
            return;
        }

        GlycemicState newState = (GlycemicState) event.getNewValue();
        if (SwingUtilities.isEventDispatchThread()) {
            render(newState);
        } else {
            SwingUtilities.invokeLater(() -> render(newState));
        }
    }

    private void configureWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(14, 14));
        content.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JLabel title = new JLabel("Гликемический индекс продуктов");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 21f));
        top.add(title, BorderLayout.NORTH);
        top.add(createDefinition(), BorderLayout.CENTER);
        content.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 12, 12));
        center.add(createSection("Последние введённые данные", productArea));
        center.add(createSection("Результат", resultArea));
        content.add(center, BorderLayout.CENTER);
        content.add(createButtons(), BorderLayout.SOUTH);
        return content;
    }

    private JTextArea createDefinition() {
        JTextArea definition = createTextArea();
        definition.setRows(3);
        definition.setText(
                "Гликемический индекс (ГИ) — показатель того, насколько быстро углеводы "
                        + "из продукта повышают уровень глюкозы в крови по сравнению с чистой глюкозой."
        );
        return definition;
    }

    private JPanel createSection(String title, JTextArea textArea) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBorder(BorderFactory.createTitledBorder(title));
        section.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return section;
    }

    private JPanel createButtons() {
        inputButton.setPreferredSize(new Dimension(180, 38));
        JPanel buttons = new JPanel();
        buttons.add(inputButton);
        return buttons;
    }

    private static JTextArea createTextArea() {
        JTextArea area = new JTextArea(3, 38);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        area.setBackground(new Color(250, 250, 250));
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }
}

