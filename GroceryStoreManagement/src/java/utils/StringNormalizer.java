package utils;

import java.io.Serializable;

/**
 *
 * @author Tran Minh Quan
 */
public class StringNormalizer implements Serializable {

    public static String normalize(String original) {
        String VN = "ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýđĂÂĐÊÔƠƯÀẢÃẠÁẰẲẴẶẮẦẨẪẬẤÈẺẼẸÉỀỂỄỆẾÌỈĨỊÍÒỎÕỌÓỒỔỖỘỐỜỞỠỢỚÙỦŨỤÚỪỬỮỰỨỲỶỸỴÝĐ";
        String EN = "AADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYDAADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYD";
        StringBuilder original_builder = new StringBuilder(original);
        int length = original_builder.length();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < VN.length(); j++) {
                if (Character.compare(original_builder.charAt(i), VN.charAt(j)) == 0) {
                    original_builder.setCharAt(i, EN.charAt(j));
                }
            }
        }

        return original_builder.toString().toUpperCase().trim().replaceAll(" +", " ");
    }
    
    public static String dateNormalize(String original) {
        String normalizedDate = "";
        
        normalizedDate += original.substring(8, 10) + '/';
        normalizedDate += original.substring(5, 7) + '/';
        normalizedDate += original.substring(0, 4) + ' ';
        normalizedDate += original.substring(11, 16) + ' ';
        
        System.out.println(normalizedDate);
        
        return normalizedDate;
    }
    
    public static String moneyNormalize(String original) {
        StringBuilder normalizedMoney = new StringBuilder(original);
        
        for (int i = normalizedMoney.length() - 3; i > 0; i -= 3) {
            normalizedMoney.insert(i, ' ');
        }
        
        return normalizedMoney.toString();
    }
}