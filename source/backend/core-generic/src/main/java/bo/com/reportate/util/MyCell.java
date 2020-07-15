package bo.com.reportate.util;

import lombok.*;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :02-06-19
 * Project    :reportate
 * Package    :bo.com.reportate.core.util
 * Copyright  : Reportate
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
