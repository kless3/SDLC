package by.bsuir.glycemicindex.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/** Active model that validates product names and looks up their GI values. */
public final class GlycemicIndexModel {
    public static final String STATE_PROPERTY = "state";

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 60;
    private static final Map<String, ProductInfo> PRODUCTS = createProducts();

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private GlycemicState state = GlycemicState.empty();

    public GlycemicState getState() {
        return state;
    }

    public Optional<ProductInput> getLastInput() {
        return Optional.ofNullable(state.input());
    }

    public void findProduct(String rawName) throws ValidationException {
        String normalizedName = validateAndNormalize(rawName);
        ProductInfo product = PRODUCTS.get(normalizeKey(normalizedName));
        if (product == null) {
            throw new ValidationException(
                    "Продукт не найден. Примеры: яблоко, банан, гречневая каша, белый рис."
            );
        }

        updateState(new GlycemicState(
                new ProductInput(product.displayName()),
                product
        ));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(STATE_PROPERTY, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(STATE_PROPERTY, listener);
    }

    private void updateState(GlycemicState newState) {
        GlycemicState oldState = state;
        state = newState;
        changeSupport.firePropertyChange(STATE_PROPERTY, oldState, newState);
    }

    private static String validateAndNormalize(String value) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Введите название продукта.");
        }

        String normalized = value.strip().replaceAll("\\s+", " ");
        int length = normalized.codePointCount(0, normalized.length());
        if (length < MIN_NAME_LENGTH || length > MAX_NAME_LENGTH) {
            throw new ValidationException(
                    "Название продукта должно содержать от %d до %d символов."
                            .formatted(MIN_NAME_LENGTH, MAX_NAME_LENGTH)
            );
        }
        boolean validCharacters = normalized.codePoints().allMatch(codePoint ->
                Character.isLetter(codePoint) || codePoint == ' ' || codePoint == '-');
        if (!validCharacters) {
            throw new ValidationException(
                    "Название продукта может содержать только буквы, пробелы и дефис."
            );
        }
        return normalized;
    }

    private static String normalizeKey(String value) {
        return value.toLowerCase(Locale.forLanguageTag("ru")).replace('ё', 'е');
    }

    private static Map<String, ProductInfo> createProducts() {
        Map<String, ProductInfo> products = new LinkedHashMap<>();
        register(products, "Глюкоза", 100);
        register(products, "Белый хлеб", 75);
        register(products, "Цельнозерновой хлеб", 53);
        register(products, "Белый рис", 73);
        register(products, "Бурый рис", 50);
        register(products, "Картофель варёный", 78);
        register(products, "Батат", 63);
        register(products, "Овсяная каша", 55);
        register(products, "Гречневая каша", 49);
        register(products, "Макароны", 49, "Спагетти");
        register(products, "Кукуруза", 52);
        register(products, "Яблоко", 36, "Яблоки");
        register(products, "Банан", 51, "Бананы");
        register(products, "Апельсин", 43, "Апельсины");
        register(products, "Груша", 38, "Груши");
        register(products, "Виноград", 53);
        register(products, "Ананас", 59);
        register(products, "Арбуз", 76);
        register(products, "Молоко", 31);
        register(products, "Йогурт натуральный", 35, "Натуральный йогурт");
        register(products, "Нут", 28);
        register(products, "Чечевица", 32);
        register(products, "Фасоль", 24);
        register(products, "Мёд", 58);
        return Map.copyOf(products);
    }

    private static void register(
            Map<String, ProductInfo> products,
            String displayName,
            int index,
            String... aliases
    ) {
        ProductInfo info = new ProductInfo(
                displayName,
                index,
                GlycemicCategory.fromIndex(index)
        );
        products.put(normalizeKey(displayName), info);
        for (String alias : aliases) {
            products.put(normalizeKey(alias), info);
        }
    }
}

