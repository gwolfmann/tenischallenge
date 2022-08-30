package com.tenistournement.model.tournamentModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenisSet {
    private Integer gamesA;
    private Integer gamesB;
}
