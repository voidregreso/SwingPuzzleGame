import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class SplitIntoGrids {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Image path:");
        String imgfn = sc.nextLine();

        File file = new File(imgfn);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage orig = ImageIO.read(fis);

        // Resize to 255*255
        BufferedImage image = new BufferedImage(255, 255, orig.getType());
        image.getGraphics().drawImage(orig, 0, 0, 255, 255, null);
        ImageIO.write(image, "jpg", new File("img/model.jpg"));

        // Divide into 25 picturitas
        int rows = 5;
        int cols = 5;
        int chunks = rows * cols;

        // Calculate the width and height of each picturita
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;
        System.out.println("Width:" + chunkWidth * rows + ", Height:" + chunkHeight * cols);

        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                // Set the size and type of the picturita
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                // Write image content
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                System.out.println("Coordinates of source matrix's 1st corner: " + chunkWidth * y + "+" + chunkHeight * x);
                System.out.println("Coordinates of source matrix's 2nd corner: " + chunkWidth * (y + 1) + "+" + chunkHeight * (x + 1));
                gr.dispose();
            }
        }
        // fill 00 as blank
        imgs[0] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
        Graphics g = imgs[0].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, chunkWidth, chunkHeight);
        g.dispose();
        // Output 5x5 grids
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "jpg", new File("img/" + (i/5) + "" + (i%5) + ".jpg"));
            System.out.print((i/5) + "" + (i%5) + "\t");
        }
    }
}
