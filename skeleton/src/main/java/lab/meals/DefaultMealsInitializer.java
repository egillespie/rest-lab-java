package lab.meals;

import com.google.common.collect.ImmutableSet;
import lab.repository.InMemoryRepository;

public class DefaultMealsInitializer implements InMemoryRepository.Initializer<Long, Meal> {
    @Override
    public void setupData(InMemoryRepository<Long, Meal> repository) {
        repository.add(Meal.builder()
                .setName("Spaghetti")
                .setDescription(Description.of("Delicious spaghetti with red sauce.",
                        "Mini Penne Pasta tossed with Ricotta Cheese, zesty meat sauce, Mama’s hand rolled miniature meatballs, and lots of Mozzarella cheese, then baked to perfection."))
                .addIngredients(ImmutableSet.of("wheat pasta", "tomato paste", "diced tomatoes", "onions", "sausage", "ground beef", "ricotta cheese", "mozzarella cheese"))
                .build());
        repository.add(Meal.builder()
                .setName("Mystery Meat")
                .setDescription(Description.of("It's meat. Maybe.",
                        "You really don't want to know."))
                .addIngredients(ImmutableSet.of("unknown"))
                .build());
        repository.add(Meal.builder()
                .setName("Fruit Salad")
                .setDescription(Description.of("Selection of fresh fruit.",
                        "Fruit that's been cut up and mixed together."))
                .addIngredients(ImmutableSet.of("melon", "strawberries", "blueberries", "pineapple"))
                .build());
        repository.add(Meal.builder()
                .setName("Tuna Salad Sandwich")
                .setDescription(Description.of("Tuna between bread.",
                        "Fresh, local tuna caught in a dolphin-safe manner. Nevermind what happens to the tuna. Includes our secret blend of seasoning."))
                .addIngredients(ImmutableSet.of("tuna", "bread", "spices"))
                .build());
        repository.add(Meal.builder()
                .setName("Donuts")
                .setDescription(Description.of("More than one donut.",
                        "Fried, round batter with a hole in the middle."))
                .addIngredients(ImmutableSet.of("flour", "sugar", "shortening"))
                .build());
    }
}
