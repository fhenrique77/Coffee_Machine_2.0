package machine;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class CoffeeMachineService {

    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int cups = 9;
    private int money = 550;

    public synchronized Map<String, Integer> remaining() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("water", water);
        m.put("milk", milk);
        m.put("coffeeBeans", coffeeBeans);
        m.put("cups", cups);
        m.put("money", money);
        return m;
    }

    public synchronized String buy(int coffeeType) {
        return switch (coffeeType) {
            case 1 -> makeCoffee(250, 0, 16, 4, "an espresso");
            case 2 -> makeCoffee(350, 75, 20, 7, "a latte");
            case 3 -> makeCoffee(200, 100, 12, 6, "a cappuccino");
            default -> "Invalid coffee type";
        };
    }

    private String makeCoffee(int requiredWater, int requiredMilk, int requiredBeans, int price, String name) {
        if (water < requiredWater) return "Not enough water";
        if (milk < requiredMilk) return "Not enough milk";
        if (coffeeBeans < requiredBeans) return "Not enough coffee beans";
        if (cups < 1) return "Not enough cups";

        water -= requiredWater;
        milk -= requiredMilk;
        coffeeBeans -= requiredBeans;
        cups -= 1;
        money += price;

        return "I have made you " + name + "!";
    }

    public synchronized String fill(FillRequest refill) {
        water += refill.water();
        milk += refill.milk();
        coffeeBeans += refill.coffeeBeans();
        cups += refill.cups();
        return "Machine filled";
    }

    public synchronized String take() {
        String msg = "I gave you $" + money;
        money = 0;
        return msg;
    }
}
