// 代码生成时间: 2025-08-10 16:43:58
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ImageResizer {
    // 配置MyBatis的SqlSessionFactory
    private static SqlSessionFactory sqlSessionFactory;
    private static void initMyBatis() {
        // 这里假设有一个mybatis-config.xml配置文件
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    // 调整图片尺寸的方法
    public static void resizeImages(String folderPath, int newWidth, int newHeight) {
        try {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                        try {
                            BufferedImage originalImage = ImageIO.read(file);
                            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
                            // 这里可以添加一个方法来设置插值方法，例如使用Bicubic
                            resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                            ImageIO.write(resizedImage, "png", new File(file.getAbsolutePath().replaceFirst(file.getName(), "resized_" + file.getName())));
                        } catch (Exception e) {
                            System.err.println("Error resizing image: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing folder: " + folderPath);
            e.printStackTrace();
        }
    }

    // 程序的主入口
    public static void main(String[] args) {
        initMyBatis();
        if (args.length < 3) {
            System.out.println("Usage: ImageResizer <folderPath> <newWidth> <newHeight>");
            return;
        }
        String folderPath = args[0];
        int newWidth = Integer.parseInt(args[1]);
        int newHeight = Integer.parseInt(args[2]);
        resizeImages(folderPath, newWidth, newHeight);
    }
}
