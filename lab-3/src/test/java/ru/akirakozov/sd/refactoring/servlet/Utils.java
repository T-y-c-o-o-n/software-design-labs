package ru.akirakozov.sd.refactoring.servlet;

public class Utils {
    public static String joinLines(String... lines) {
        return String.join("" + (char) Character.LINE_SEPARATOR, lines) + (char) Character.LINE_SEPARATOR;
    }
}
