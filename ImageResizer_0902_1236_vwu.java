// 代码生成时间: 2025-09-02 12:36:08
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.List;

// 图片尺寸批量调整器
public class ImageResizer {

    private static final String MYBATIS_CONFIG = "mybatis-config.xml";
    private SqlSessionFactory sqlSessionFactory;

    // 构造函数，用于初始化SqlSessionFactory
    public ImageResizer() {
        try {
            String resource = MYBATIS_CONFIG;
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据数据库中的图片信息调整图片尺寸
    public void resizeImages(@Param("width") int width, @Param("height") int height) {
        // 获取SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 获取图片信息的Mapper
            ImageMapper imageMapper = session.getMapper(ImageMapper.class);
            List<ImageInfo> images = imageMapper.getImages();

            // 调整每张图片的尺寸
            for (ImageInfo imageInfo : images) {
                try {
                    resizeImage(imageInfo, width, height);
                } catch (IOException e) {
                    System.err.println("Error resizing image: " + imageInfo.getPath());
                    e.printStackTrace();
                }
            }
            session.commit();
        }
    }

    // 调整单个图片的尺寸
    private void resizeImage(ImageInfo imageInfo, int width, int height) throws IOException {
        File imageFile = new File(imageInfo.getPath());
        BufferedImage originalImage = ImageIO.read(imageFile);

        // 创建一个新的BufferedImage对象
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        // 设置图片背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 调整图片到新的尺寸
        AffineTransform transform = new AffineTransform();
        transform.scale((double) width / originalImage.getWidth(), (double) height / originalImage.getHeight());
        g2d.drawRenderedImage(originalImage, transform);

        // 释放资源
        g2d.dispose();

        // 保存调整后的图片
        ImageIO.write(resizedImage, imageInfo.getFormat(), imageFile);
    }
}

// 图片信息类
class ImageInfo {
    private String path;
    private String format;

    public ImageInfo(String path, String format) {
        this.path = path;
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

// 图片信息Mapper接口
interface ImageMapper {
    @Select("SELECT path, format FROM images")
    List<ImageInfo> getImages();
}
