package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tarun
 */
public class ImageCompression {

        /**
         * ***********************METHODS*****************************
         */
        /**
         * The below method is use for compress the image according to which you
         * are sending quality
         *
         * @param:BufferImage image
         * @param:quality in float
         * @return:BufferImage  
                *
         */
        public static BufferedImage compressImage(BufferedImage bi, float quality) throws IOException {
                ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName("jpeg").next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(quality);

                byte[] imageBytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
                writer.setOutput(ios);

                IIOImage iio = new IIOImage(bi, null, null);

                writer.write(null, iio, iwp);

                imageBytes = baos.toByteArray();

                return ImageIO.read(new ByteArrayInputStream(imageBytes));
        }
}
