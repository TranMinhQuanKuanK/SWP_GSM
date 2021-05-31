package utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tran Minh Quan
 */
public class StringNormalizer implements Serializable {

    public StringNormalizer() {
    }

    public String normalize(String original) {
       String VN = "ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýđĂÂĐÊÔƠƯÀẢÃẠÁẰẲẴẶẮẦẨẪẬẤÈẺẼẸÉỀỂỄỆẾÌỈĨỊÍÒỎÕỌÓỒỔỖỘỐỜỞỠỢỚÙỦŨỤÚỪỬỮỰỨỲỶỸỴÝĐ";
       String EN = "AADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYDAADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYD";
       // String EN = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        StringBuilder original_builder = new StringBuilder(original);
        int length = original_builder.length();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < VN.length(); j++) {
                if (Character.compare(original_builder.charAt(i), VN.charAt(j)) == 0) {
                    original_builder.setCharAt(i, EN.charAt(j));
                }
            }
        }
        
        length = original_builder.length();
        ArrayList<Integer> removeChar = new ArrayList<>();
        for (int i = 0; i < length-1; i++) {
            if (original_builder.charAt(i) == ' ' && original_builder.charAt(i + 1) == ' ') {
                removeChar.add(i);
            }
        }
        StringBuilder new_builder = new StringBuilder("");
        for (int i = 0; i < length; i++ ) {
            if (!removeChar.contains(i)) {
              new_builder.append(original_builder.charAt(i));
            }
        }

        return new_builder.toString().toUpperCase();
    }
}
