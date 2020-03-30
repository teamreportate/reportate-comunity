package bo.com.reportate.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionDto {
    private Long id;
    private Long asignacionId;
    private Boolean nuevo;
}
