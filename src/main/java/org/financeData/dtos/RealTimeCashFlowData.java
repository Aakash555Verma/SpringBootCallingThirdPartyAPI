package org.financeData.dtos;

import lombok.Data;
import org.financeData.models.CashFlow;

import java.util.List;

@Data
public class RealTimeCashFlowData {
    private List<CashFlow> cash_flow;
}
