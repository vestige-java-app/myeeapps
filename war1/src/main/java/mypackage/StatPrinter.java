package mypackage;

import java.text.MessageFormat;

public class StatPrinter {

    public static void stat(final long[] res) {
        int off = Integer.valueOf(System.getProperty("off", "2"));
        int nb = res.length;
        if (nb <= 0) {
            System.out.println("No inputs");
            return;
        }
        int maxOffset = off;
        long min = res[off];
        long max = res[off];
        double moyenne = res[off];
        for (int i = off + 1; i < nb; i++) {
            if (res[i] > max) {
                max = res[i];
                maxOffset = i;
            }
            if (res[i] < min) {
                min = res[i];
            }
            double add = (res[i] - moyenne) / (i + 1);
            moyenne = moyenne + add;
        }

        double moyenneEcart = (moyenne - res[off]) * (moyenne - res[off]);
        for (int i = off + 1; i < nb; i++) {
            double add = (((moyenne - res[i]) * (moyenne - res[i])) - moyenneEcart) / (i + 1);
            moyenneEcart = moyenneEcart + add;
        }
        double milliInNanos = 10000;
        // System.out.println(Arrays.toString(res));
        System.out.println(MessageFormat.format("{0} +- {1} [{2} - {3}]", (moyenne / milliInNanos), (Math.sqrt(moyenneEcart) / milliInNanos), (min / milliInNanos),
                (max / milliInNanos)));
        System.out.println("maxOffset : " + maxOffset);
    }

}
