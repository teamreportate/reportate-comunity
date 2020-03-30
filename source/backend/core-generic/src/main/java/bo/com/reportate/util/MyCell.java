package bo.com.reportate.util;

import lombok.*;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :02-06-19
 * Project    :reportate
 * Package    :bo.com.reportate.core.util
 * Copyright  : MC4
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyCell {
    private String content;
    private String textColor;
    private String bgColor;
    private String textSize;
    private String textWeight;
    private CellType cellType;

    public MyCell(String content) {
        this.content = content;
    }
}
