package bo.com.reportate.model.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :19/12/2019
 * Project       :reportate
 * Package       :bo.com.Reportate.reportate.model.dto
 **/
@Getter
@Setter
@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDto  implements Serializable {
    private String proceso;
}
