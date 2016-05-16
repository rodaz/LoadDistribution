package main.java.EVM.util;

/**
 * Created by Alexey on 28.03.2016.
 *
 * Класс для преобразования String в int и double.
 * В отличии от стандартных методов при пустой строке возвращает 0.
 */
public class Convert {
    public static int rend(String str) {
        if (str.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    public static double rendD(String str){
        if (str.equals("")) {
            return 0;
        } else {
            return Double.parseDouble(str);
        }
    }
}
