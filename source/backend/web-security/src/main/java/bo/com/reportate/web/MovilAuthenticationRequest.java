package bo.com.reportate.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovilAuthenticationRequest implements Serializable {
    private String remoteId;
    private String name;
}
