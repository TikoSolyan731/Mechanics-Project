package com.aua.mechanics.springs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Iterables;

public class SpringArray {
    public static Spring equivalentSpring(String expr) {
        Iterable<Spring> infiniteSprings = Iterables.cycle(new Spring(1));
        return equivalentSpringRecursive(expr, infiniteSprings.iterator()).get(0);
    }

    public static Spring equivalentSpring(String expr, Spring[] springs) {
        List<Spring> listOfSprings = Arrays.asList(springs);
        return equivalentSpringRecursive(expr, listOfSprings.iterator()).get(0);
    }

    private static List<Spring> equivalentSpringRecursive(String expr, Iterator<Spring> iterator) {
        List<Spring> springs = new ArrayList<>();

        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);
            int closingCharIndex = findMatchingClosingBracket(expr, i);

            if (i + 1 == closingCharIndex) {
                springs.add(iterator.next());
            } else {
                var subExpr = expr.substring(i + 1, closingCharIndex);
                var innerSprings = equivalentSpringRecursive(subExpr, iterator);

                Spring jointSpring = innerSprings.get(0);
                for (int j = 1; j < innerSprings.size(); j++) {
                    if (ch == '{') {
                        jointSpring = jointSpring.inSeries(innerSprings.get(j));
                    } else if (ch == '[') {
                        jointSpring = jointSpring.inParallel(innerSprings.get(j));
                    }
                }
                springs.add(jointSpring);
            }
            i = closingCharIndex + 1;
        }

        return springs;
    }

    private static int findMatchingClosingBracket(String s, int i) {
        int count = 1;
        while (count > 0 && ++i < s.length()) {
            char c = s.charAt(i);
            if (c == '{' || c == '[') {
                count++;
            } else if (c == '}' || c == ']') {
                count--;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        var spring = equivalentSpring("{[{}{}{}][{}{}]}");
        System.out.println(spring.getStiffness());

        spring = equivalentSpring("{[{}{}{}][{}{}]}", new Spring[]{
                new Spring(2), new Spring(3), new Spring(1),
                new Spring(5), new Spring(2)
        });
        System.out.println(spring.getStiffness());
    }
}
