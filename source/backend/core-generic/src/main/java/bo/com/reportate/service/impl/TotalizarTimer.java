package bo.com.reportate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Component
@Slf4j
public class TotalizarTimer {
    @Scheduled(fixedRate = 100000)
    public void updateTime() {
        try {
            log.debug("Actualizando hora en nodo:{} ", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.error("Error Actualizando hora en nodo:", e);
        }
    }
}
