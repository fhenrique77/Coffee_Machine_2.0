package machine;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoffeeMachineServiceTest {

    @Test
    void eachCoffeeUsesTheOriginalRecipe() {
        assertPurchase(1, "I have made you an espresso!", 150, 540, 104, 554);
        assertPurchase(2, "I have made you a latte!", 50, 465, 100, 557);
        assertPurchase(3, "I have made you a cappuccino!", 200, 440, 108, 556);
    }

    @Test
    void insufficientStockDoesNotChangeTheMachine() {
        CoffeeMachineService machine = new CoffeeMachineService();
        machine.buy(1);
        Map<String, Integer> stock = machine.remaining();

        assertEquals("Not enough water", machine.buy(2));
        assertEquals(stock, machine.remaining());
        assertEquals("Invalid coffee type", machine.buy(4));
        assertEquals(stock, machine.remaining());
    }

    @Test
    void fillAddsResourcesAndTakeEmptiesTheCashBox() {
        CoffeeMachineService machine = new CoffeeMachineService();

        assertEquals("Machine filled", machine.fill(new FillRequest(10, 20, 30, 2)));
        assertEquals(Map.of("water", 410, "milk", 560, "coffeeBeans", 150, "cups", 11, "money", 550),
                machine.remaining());
        assertEquals("I gave you $550", machine.take());
        assertEquals(0, machine.remaining().get("money"));
    }

    private static void assertPurchase(int type, String message, int water, int milk, int beans, int money) {
        CoffeeMachineService machine = new CoffeeMachineService();

        assertEquals(message, machine.buy(type));
        assertEquals(Map.of("water", water, "milk", milk, "coffeeBeans", beans, "cups", 8, "money", money),
                machine.remaining());
    }
}
