package dtu.ws.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Marcus August Christiansen - s175185
 */

@Getter
@Setter
@NoArgsConstructor
public class Payment implements Serializable {
    String fromAccountNumber;
    String toAccountNumber;
    BigDecimal amount;
    String description;
}
