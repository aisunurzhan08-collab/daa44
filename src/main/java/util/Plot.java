package util;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plot {

    public static void save(String path, String title, String xLabel, double[] xs, String[] names, double[][] ys) throws IOException {
        System.setProperty("java.awt.headless", "true");
        int w = 800;
        int h = 500;
        int left = 80;
        int right = 30;
        int top = 50;
        int bottom = 70;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.drawString(title, w / 2 - title.length() * 3, 25);

        double maxX = xs[xs.length - 1];
        double maxY = 0;
        for (double[] series : ys) {
            for (double v : series) {
                maxY = Math.max(maxY, v);
            }
        }
        if (maxY == 0) {
            maxY = 1;
        }

        g.drawLine(left, h - bottom, w - right, h - bottom);
        g.drawLine(left, top, left, h - bottom);
        for (int i = 0; i <= 5; i++) {
            int py = (h - bottom) - (int) ((h - bottom - top) * i / 5.0);
            g.drawLine(left - 4, py, left, py);
            g.drawString(String.format("%.2f", maxY * i / 5.0), 20, py + 4);
        }
        for (double x : xs) {
            int px = left + (int) ((w - left - right) * x / maxX);
            g.drawLine(px, h - bottom, px, h - bottom + 4);
            g.drawString(String.valueOf((long) x), px - 15, h - bottom + 20);
        }
        g.drawString(xLabel, w / 2 - 40, h - 20);
        g.drawString("time, ms", 10, 40);

        Color[] colors = {new Color(214, 39, 40), new Color(31, 119, 180), new Color(44, 160, 44), Color.ORANGE};
        for (int s = 0; s < ys.length; s++) {
            g.setColor(colors[s % colors.length]);
            int prevX = 0;
            int prevY = 0;
            for (int k = 0; k < xs.length; k++) {
                int px = left + (int) ((w - left - right) * xs[k] / maxX);
                int py = (h - bottom) - (int) ((h - bottom - top) * ys[s][k] / maxY);
                g.fillOval(px - 4, py - 4, 8, 8);
                if (k > 0) {
                    g.drawLine(prevX, prevY, px, py);
                }
                prevX = px;
                prevY = py;
            }
            g.fillRect(w - 180, top + s * 20, 12, 12);
            g.setColor(Color.BLACK);
            g.drawString(names[s], w - 160, top + s * 20 + 11);
        }
        g.dispose();
        ImageIO.write(image, "png", new File(path));
    }
}
