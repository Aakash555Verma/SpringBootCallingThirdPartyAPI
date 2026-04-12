package org.financeData.dtos;

import lombok.Data;
import org.financeData.models.News;

import java.util.List;

@Data
public class RealTimeNewsData {
    private List<News> news;
}
